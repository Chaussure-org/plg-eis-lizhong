package com.prolog.eis.location.service.impl;

import com.google.common.collect.Lists;
import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.util.location.CoordinateUtils;
import com.prolog.eis.util.mapper.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AgvLocationServiceImpl implements AgvLocationService{

	@Autowired
	private AgvStoragelocationMapper agvStoragelocationMapper;
	@Autowired
	private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;

	@Override
	public AgvStoragelocationDTO findLoacationByArea(String area, String locationNo, int reserveCount) throws Exception {
		if (StringUtils.isEmpty(area)) {
			throw new Exception("区域不可为空！");
		}
		Query query = new Query(AgvStoragelocation.class);
		query.addEq("areaNo", area);
		query.addEq("taskLock", 0);
		query.addEq("storageLock", 0);
		List<AgvStoragelocation> agvStoragelocationList = agvStoragelocationMapper.findByEisQuery(query);
		if (CollectionUtils.isEmpty(agvStoragelocationList)) {
			throw new Exception("请初始化agv区域货位表");
		}

		//预留货位数大于实际空余空位数，返回null
		if (agvStoragelocationList.size() <= reserveCount) {
			return null;
		} else {//计算最短的坐标
			List<AgvStoragelocationDTO> agvStoragelocationDTOList = Lists.newArrayList();
			for (AgvStoragelocation agvStoragelocation : agvStoragelocationList) {
				AgvStoragelocationDTO agvStoragelocationDTO = new AgvStoragelocationDTO();
				BeanUtils.copyProperties(agvStoragelocation, agvStoragelocationDTO);
				//点位解析
				int[] arrayXy = CoordinateUtils.locationToXyForRcs(locationNo);
				//根据两点差的平方和，返回一个正平方根
				double sqrt = Math.sqrt(Math.pow((arrayXy[0] - agvStoragelocation.getX()), 2) + Math.pow((arrayXy[1] - agvStoragelocation.getY()), 2));
				agvStoragelocationDTO.setOriginX(arrayXy[0]);
				agvStoragelocationDTO.setOriginY(arrayXy[1]);
				agvStoragelocationDTO.setStep(sqrt);
				agvStoragelocationDTOList.add(agvStoragelocationDTO);
			}
			//根据长度排序后默认取第一条最短的坐标点
			Collections.sort(agvStoragelocationDTOList, Comparator.comparing(AgvStoragelocationDTO::getStep));
			return agvStoragelocationDTOList.get(0);
		}
	}

	@Override
	public ContainerPathTaskDetailDTO requestPallet(String locationNo) throws Exception {
		Query query = new Query(ContainerPathTaskDetail.class);
		query.addEq("containerNo", null);
		query.addEq("taskState", 0);
		List<ContainerPathTaskDetail> containerPathTaskDetailList = containerPathTaskDetailMapper.findByEisQuery(query);
		if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
			return null;
		}
		List<ContainerPathTaskDetailDTO> containerPathTaskDetailDTOList= Lists.newArrayList();
		for (ContainerPathTaskDetail containerPathTaskDetail : containerPathTaskDetailList) {
			ContainerPathTaskDetailDTO dto = new ContainerPathTaskDetailDTO();
			BeanUtils.copyProperties(containerPathTaskDetail, dto);
			//点位转xy原点...
			int[] arrayPalletXy = CoordinateUtils.locationToXyForRcs(dto.getSourceLocation());
			int[] arrayNextXy = CoordinateUtils.locationToXyForRcs(locationNo);
			//根据两点差的平方和，返回一个正平方根
			double sqrt = Math.sqrt(Math.pow((arrayNextXy[0] - arrayPalletXy[0]), 2) + Math.pow((arrayNextXy[1] - arrayPalletXy[1]), 2));
			dto.setOriginX(arrayNextXy[0]);
			dto.setOriginY(arrayNextXy[1]);
			dto.setStep(sqrt);
			containerPathTaskDetailDTOList.add(dto);
		}
		//根据长度排序后默认取第一条最短的坐标点
		Collections.sort(containerPathTaskDetailDTOList, Comparator.comparing(ContainerPathTaskDetailDTO::getStep));
		return containerPathTaskDetailDTOList.get(0);
	}

}
