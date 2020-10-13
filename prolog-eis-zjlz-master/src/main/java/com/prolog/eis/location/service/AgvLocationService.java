package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDTO;

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
}
