package com.prolog.eis.store.service.impl;

import com.prolog.eis.base.dao.GoodsMapper;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private GoodsMapper goodsMapper;
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
	public List<ContainerStore> findByMap(Map map) {
		return containerStoreMapper.findByMap(map,ContainerStore.class);
	}


	@Override
	public GoodsInfo findContainerStockInfo(String containerNo) {
		
		ContainerStore containerStore = containerStoreMapper.findFirstByMap(MapUtils.put("containerNo", containerNo).getMap(), ContainerStore.class);
		if(null == containerStore) {
			return getEmptyGoods();
		}else {
			if(containerStore.getGoodsId()==ContainerStore.EMPTY_TRAY) {
				//空托盘
				return getEmptyGoods();
			}else {
				//任务托
                GoodsInfo goodsInfo = new GoodsInfo();
                goodsInfo.setId(containerStore.getGoodsId());
                goodsInfo.setLotId(containerStore.getLotId());
                goodsInfo.setOwnerId(containerStore.getOwnerId());
                return goodsInfo;
            }
		}
	}
	
	@Override
	public String buildTaskProperty1(GoodsInfo goodsInfo) {
        if (goodsInfo == null){
            return null;
        }
		return goodsInfo.getId() + "And" + goodsInfo.getLotId();
	}

	@Override
	public String buildTaskProperty2(GoodsInfo goodsInfo) {
        if (goodsInfo == null){
            return null;
        }
		return goodsInfo.getOwnerId();
	}

	@Override
	public boolean setContainerStoreEmpty(String containerNo) {
		return false;
	}

    @Override
    public List<ContainerStore> findContainerListByGoodsId(Integer goodsId) {
        return containerStoreMapper.findBestContainerSeq(goodsId);
    }

    @Override
    public void updateContainerTaskType(ContainerStore containerStore) {
        containerStore.setTaskType(ContainerStore.TASK_TYPE_OUTBOUND);
        containerStore.setUpdateTime(new Date());
        containerStoreMapper.update(containerStore);
    }

    @Override
    public void updateTaskStausByContainer(String containerNo, int type) {
        Criteria ctr=Criteria.forClass(ContainerStore.class);
        ctr.setRestriction(Restrictions.eq("containerNo",containerNo));
        containerStoreMapper.updateMapByCriteria(MapUtils.put("taskStatus",type).getMap(),ctr);
    }

    @Override
    public void updateTaskTypeByContainer(String containerNo, int type) {
        Criteria ctr=Criteria.forClass(ContainerStore.class);
        ctr.setRestriction(Restrictions.eq("containerNo",containerNo));
        containerStoreMapper.updateMapByCriteria(MapUtils.put("taskType",type).getMap(),ctr);
    }

    /**
     * 新建库存
     * @param containerStore
     */
    @Override
    public void saveContainerStore(ContainerStore containerStore) {
        containerStoreMapper.save(containerStore);
    }

    @Override
    public void updateContainerStore(String containerNo, int taskType,int taskState) throws Exception {
        Criteria criteria = Criteria.forClass(ContainerStore.class);
        criteria.setRestriction(Restrictions.eq("containerNo",containerNo));
        containerStoreMapper.updateMapByCriteria(MapUtils.put("taskType",taskType).
                put("taskState",taskState).put("updateTime",new Date()).getMap(),criteria);
    }

    @Override
    public void updateEmptyContainer(String containerNo) {
        Criteria criteria = Criteria.forClass(ContainerStore.class);
        criteria.setRestriction(Restrictions.eq("containerNo",containerNo));
        containerStoreMapper.updateMapByCriteria(MapUtils.put("goodsId",-1).put("qty",1).
                put("updateTime",new Date()).getMap(),criteria);
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
