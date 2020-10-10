package com.prolog.eis.store.service;


import com.prolog.eis.model.GoodsInfo;

public interface ContainerStoreService {

	/**
	 * 根据托盘获取商品库存信息
	 * @param containerNo
	 * @return
	 */
	GoodsInfo findContainerStockInfo(String containerNo);
	
	/**
	 * 获取商品的分配货位属性1
	 * @param goodsInfo
	 * @return
	 */
	String buildTaskProperty1(GoodsInfo goodsInfo);
	
	/**
	 * 获取商品的分配货位属性2
	 * @param goodsInfo
	 * @return
	 */
	String buildTaskProperty2(GoodsInfo goodsInfo);

	/**
	 * 将托盘库存设置位空托盘
	 * @param containerNo 托盘号
	 * @return
	 */
	boolean setContainerStoreEmpty(String containerNo);
}
