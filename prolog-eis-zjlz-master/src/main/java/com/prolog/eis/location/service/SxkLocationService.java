package com.prolog.eis.location.service;

import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.model.store.SxStoreLocationGroup;

public interface SxkLocationService {

	/**
	 * 根据区域查找货位组
	 * @param area
	 * @param originX
	 * @param originY
	 * @param reserveCount
	 * @param weight
	 * @param taskProperty1
	 * @param taskProperty2
	 * @return
	 */
	SxStoreLocation findLoacationByArea(String area, int originX, int originY, int reserveCount, double weight,
										String taskProperty1, String taskProperty2) throws Exception;
	
	/**
	 * 重新计算货位组  深度， 可入库货位，货位组商品属性
	 * @param sxStoreLocationGroup
	 * @throws Exception
	 */
	void computeLocation(SxStoreLocationGroup sxStoreLocationGroup) throws Exception;
}
