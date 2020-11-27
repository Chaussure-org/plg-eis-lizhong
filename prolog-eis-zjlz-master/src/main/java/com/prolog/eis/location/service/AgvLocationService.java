package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDTO;
import com.prolog.eis.dto.page.AgvStoreInfoDto;
import com.prolog.eis.dto.page.AgvStoreQueryDto;
import com.prolog.eis.dto.page.StoreInfoDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.framework.core.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgvLocationService {

	/**
	 * 根据区域查找可用货位
	 * @param area	区域号
	 * @param locationNo	目标点位
	 * @param reserveCount	预留货位数
	 * @throws Exception
	 * @return
	 */
	AgvStoragelocationDTO findLoacationByArea(String area, String locationNo, int reserveCount) throws Exception;

	/**
	 * agv出库
	 * @param goodsId
	 * @param stationNo
	 * @throws Exception
	 */
	ContainerPathTaskDTO agvOutboundTask(int goodsId, String stationNo) throws Exception;


	/**
	 * 根据map查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<AgvStoragelocation> findByMap(Map map) throws Exception;

	/**
	 * 查询站台集合的可用任务拖区域
	 * @param list
	 * @param storeArea
	 * @return
	 */
    public List<StationTrayDTO> findTrayTaskStation(String storeArea,List<Integer> list);


	/**
	 * 查找区域一个可用位置
	 * @param storeArea
	 * @param stationId
	 * @return
	 */
	List<String> getUsableStore(String storeArea,int stationId);


	/**
	 * 查料箱是否到位
	 * @param containerNo
	 * @param stationId
	 * @return
	 */
	int findContainerArrive(String containerNo,int stationId);

	/**
	 *
	 * @param agvQueryDto
	 * @return
	 */
    Page<AgvStoreInfoDto> getAgvStorePage(AgvStoreQueryDto agvQueryDto);

	/**
	 * 根据货位id修改锁
	 * @param agvStoreId
	 * @param storagelock
	 */
	void updateStoreLock(int agvStoreId, int storagelock) throws Exception;


	/**
	 * 查铁笼区的可用铁笼
	 */
	List<String> getIronTray(String areaNo);
}
