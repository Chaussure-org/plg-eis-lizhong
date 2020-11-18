package com.prolog.eis.location.service.impl;

import com.google.common.collect.Lists;
import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDTO;
import com.prolog.eis.dto.page.AgvStoreInfoDto;
import com.prolog.eis.dto.page.AgvStoreQueryDto;
import com.prolog.eis.dto.page.StoreInfoDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.dao.StoreAreaMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AgvLocationServiceImpl implements AgvLocationService {

	@Autowired
	private AgvStoragelocationMapper agvStoragelocationMapper;
	@Autowired
	private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
	@Autowired
	private ContainerPathTaskMapper containerPathTaskMapper;
	@Autowired
	private StoreAreaMapper storeAreaMapper;

	@Override
	public AgvStoragelocationDTO findLoacationByArea(String area, String locationNo, int reserveCount) throws Exception {
		if (StringUtils.isEmpty(area)) {
			throw new Exception("区域不可为空！");
		}
		//如果当前区域是接驳点，则直接取坐标即可
		StoreArea storeArea = storeAreaMapper.findByMap(
				MapUtils.put("areaNo", area).getMap()
				, StoreArea.class)
				.stream()
				.filter(s -> LocationConstants.AREA_TYPE_POINT == s.getAreaType())
				.findAny()
				.orElse(null);
		if (null != storeArea) {
			AgvStoragelocationDTO agvStoragelocationDTO = new AgvStoragelocationDTO();
			agvStoragelocationDTO.setLocationNo(storeArea.getLocationNo());
			return agvStoragelocationDTO;
		}

		List<String> locationList = containerPathTaskDetailMapper.findByMap(
				MapUtils.put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE).getMap()
				, ContainerPathTaskDetail.class)
				.stream()
				.map(ContainerPathTaskDetail::getNextLocation)
				.collect(Collectors.toList());

		//获取不在容器任务明细中的点位
		List<AgvStoragelocation> agvStoragelocationList = agvStoragelocationMapper.findByMap(
				MapUtils.put("areaNo", area)
						.put("taskLock", LocationConstants.AGV_TASKLOCK_UNLOCK)
						.put("storageLock", LocationConstants.AGV_STORAGELOCK_UNLOCK).getMap()
				, AgvStoragelocation.class)
				.stream()
				.filter(agvStoragelocation -> !locationList.contains(agvStoragelocation.getLocationNo()))
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(agvStoragelocationList)) {
			throw new Exception("请初始化agv区域货位表");
		}
		List<AgvStoragelocation> locationNoList = agvStoragelocationMapper.findByMap(
				MapUtils.put("locationNo", locationNo).getMap(), AgvStoragelocation.class);
		if (CollectionUtils.isEmpty(locationNoList)) {
			throw new Exception("点位不存在");
		}

		//预留货位数大于实际空余空位数，返回null
		if (agvStoragelocationList.size() <= reserveCount) {
			return null;
		} else {//计算最短的坐标
			List<AgvStoragelocationDTO> agvStoragelocationDTOList = Lists.newArrayList();
			for (AgvStoragelocation agvStoragelocation : agvStoragelocationList) {
				AgvStoragelocationDTO agvStoragelocationDTO = new AgvStoragelocationDTO();
				BeanUtils.copyProperties(agvStoragelocation, agvStoragelocationDTO);
				//根据两点差的平方和，返回一个正平方根
				double sqrt = Math.sqrt(Math.pow((locationNoList.get(0).getX() - agvStoragelocation.getX()), 2)
						+ Math.pow((locationNoList.get(0).getY() - agvStoragelocation.getY()), 2));
				agvStoragelocationDTO.setOriginX(locationNoList.get(0).getX());
				agvStoragelocationDTO.setOriginY(locationNoList.get(0).getY());
				agvStoragelocationDTO.setStep(sqrt);
				agvStoragelocationDTOList.add(agvStoragelocationDTO);
			}
			//根据长度排序后默认取第一条最短的坐标点
			Collections.sort(agvStoragelocationDTOList, Comparator.comparing(AgvStoragelocationDTO::getStep));
			return agvStoragelocationDTOList.get(0);
		}
	}

	@Override
	public ContainerPathTaskDTO agvOutboundTask(int goodsId, String stationId) throws Exception {
		List<AgvStoragelocationDTO> agvStoragelocationDTOList = this.agvStoragelocationMapper.listAgvStoragelocationXyByStationNo(stationId);
		if(CollectionUtils.isEmpty(agvStoragelocationDTOList)){
			throw new Exception("请初始化托盘作业位");
		}
		//计算站台中2个托盘位的坐标平均值，用于找最近的托盘
		int x = Math.toIntExact(Math.round(agvStoragelocationDTOList.stream().mapToDouble(AgvStoragelocationDTO::getOriginX).average().getAsDouble()));
		int y = Math.toIntExact(Math.round(agvStoragelocationDTOList.stream().mapToDouble(AgvStoragelocationDTO::getOriginY).average().getAsDouble()));

		List<ContainerPathTaskDTO> containerPathTaskDTOList = containerPathTaskMapper.listContainerPathTaskByGoodsId(goodsId);
		if(CollectionUtils.isEmpty(containerPathTaskDTOList)){
			throw new Exception("agv没有可出库的托盘");
		}
		containerPathTaskDTOList.forEach(containerPathTaskDTO -> {
			//根据两点差的平方和，返回一个正平方根
			double sqrt = Math.sqrt(Math.pow((x - containerPathTaskDTO.getX()), 2)
					+ Math.pow((y - containerPathTaskDTO.getY()), 2));
			containerPathTaskDTO.setStep(sqrt);
		});
		//根据长度排序后默认取第一条最短的托盘
		Collections.sort(containerPathTaskDTOList, Comparator.comparing(ContainerPathTaskDTO::getStep));
		ContainerPathTaskDTO containerPathTaskDTO = containerPathTaskDTOList.get(0);
		containerPathTaskDTO.setTargetArea(agvStoragelocationDTOList.get(0).getAreaNo());
		return containerPathTaskDTO;
	}

	@Override
	public List<AgvStoragelocation> findByMap(Map map) throws Exception {
		return agvStoragelocationMapper.findByMap(map,AgvStoragelocation.class);
	}

	@Override
	public List<StationTrayDTO> findTrayTaskStation(String storeArea,List<Integer> list) {
		return agvStoragelocationMapper.findTrayTaskStation(storeArea,list);
	}

	@Override
	public List<String> getUsableStore(String storeArea, int stationId) {
		return agvStoragelocationMapper.getUsableStore(storeArea,stationId);
	}

	@Override
	public int findContainerArrive(String containerNo, int stationId) {
		return agvStoragelocationMapper.findContainerArrive(containerNo,stationId);
	}

	@Override
	public Page<AgvStoreInfoDto> getAgvStorePage(AgvStoreQueryDto agvQueryDto) {
		PageUtils.startPage(agvQueryDto.getPageNum(),agvQueryDto.getPageSize());

		List<AgvStoreInfoDto> list = agvStoragelocationMapper.getAgvStoreInfo(agvQueryDto);
		Page<AgvStoreInfoDto> page = PageUtils.getPage(list);
		return page;
	}

	@Override
	public void updateStoreLock(int agvStoreId, int storagelock) throws Exception {
		if (storagelock != AgvStoragelocation.TASK_LOCK || storagelock == AgvStoragelocation.TASK_EMPTY){
			throw new Exception("锁参数异常，请检查参数");
		}
		AgvStoragelocation agvStoragelocation = agvStoragelocationMapper.findById(agvStoreId, AgvStoragelocation.class);
		if (agvStoragelocation == null){
			throw new Exception("【"+agvStoreId+"】货位不存在，无法修改");
		}
		agvStoragelocation.setStorageLock(storagelock);
		agvStoragelocationMapper.update(agvStoragelocation);
	}


}
