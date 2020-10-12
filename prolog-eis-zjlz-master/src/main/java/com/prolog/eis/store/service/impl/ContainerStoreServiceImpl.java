package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.store.service.ContainerStoreService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerStoreServiceImpl implements ContainerStoreService {
	
	@Autowired
	private ContainerStoreMapper containerStoreMapper;
	//@Autowired
	//private GoodsInfoMapper goodsInfoMapper;
	
	/**
	 * 根据托盘获取商品库存信息
	 * @param containerNo
	 * @return
	 */
	@Override
	public GoodsInfo findContainerStockInfo(String containerNo) {
		
//		ContainerStore containerStore = containerStoreMapper.findFirstByMap(MapUtils.put("containerNo", containerNo).getMap(), ContainerStore.class);
//		if(null == containerStore) {
//			return getEmptyGoods();
//		}else {
//			if(containerStore.getContainerType() == -1) {
//				//空托盘
//				return getEmptyGoods();
//			}else {
//				//任务托
//				return goodsInfoMapper.findById(containerStore.getGoodsId(), GoodsInfo.class);
//			}
//		}
		return null;
	}
	
	@Override
	public String buildTaskProperty1(GoodsInfo goodsInfo) {

		return goodsInfo.getOwnerId() + "And" + goodsInfo.getGoodsCode();
	}

	@Override
	public String buildTaskProperty2(GoodsInfo goodsInfo) {

		return goodsInfo.getLotId() + "And" + goodsInfo.getGoodsOrderNo();
	}

	@Override
	public boolean setContainerStoreEmpty(String containerNo) {
		return false;
	}

	private GoodsInfo getEmptyGoods() {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setOwnerId("");
		goodsInfo.setGoodsCode("");
		goodsInfo.setGoodsBarcode("");
		goodsInfo.setLotId("");
		
		return goodsInfo;
	}
}
