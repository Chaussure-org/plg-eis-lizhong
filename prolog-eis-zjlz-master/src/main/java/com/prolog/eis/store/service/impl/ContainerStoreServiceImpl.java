package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 18:30
 */
@Service
public class ContainerStoreServiceImpl implements IContainerStoreService {
    private final Logger logger = LoggerFactory.getLogger(ContainerStoreServiceImpl.class);

    @Autowired
    private ContainerStoreMapper containerStoreMapper;
    @Override
    public void updateContainerStore(ContainerStore containerStore) {
        if (containerStore != null){
            containerStoreMapper.update(containerStore);
        }

    }

    @Override
    public void updateContainerStoreNum(int num,String containerNo) {
        List<ContainerStore> containerStoreList = containerStoreMapper.findByMap(MapUtils.put("containerNo", containerNo).getMap(), ContainerStore.class);
        if (containerStoreList.size() == 0 ){
            throw new RuntimeException("容器【"+containerNo+"】未被管理");
        }
        ContainerStore containerStore = containerStoreList.get(0);
        if (containerStore.getQty() < num){
            logger.info("容器【{}】库存不足",containerNo);
        }
        containerStore.setQty(containerStore.getQty() - num);
        this.updateContainerStore(containerStore);
    }


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

    @Override
    public List<ContainerStore> findContainerListByGoodsId(Integer goodsId) {
        return containerStoreMapper.findBestContainerSeq(goodsId);
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
