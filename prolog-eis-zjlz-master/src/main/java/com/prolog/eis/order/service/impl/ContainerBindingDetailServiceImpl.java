package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.order.dao.ContainerBindingDetailMapper;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:05
 */
@Service
public class ContainerBindingDetailServiceImpl implements IContainerBindingDetailService {

    @Autowired
    private ContainerBindingDetailMapper containerBindingDetailMapper;
    @Override
    public List<ContainerBindingDetail> findMap(Map map) throws Exception {
        return containerBindingDetailMapper.findByMap(map,ContainerBindingDetail.class);
    }

    @Override
    public void deleteContainerDetail(Map map) {
        containerBindingDetailMapper.deleteByMap(map,ContainerBindingDetail.class);
    }

    @Override
    public List<Integer> getContainerBindingToStation(String containerNo) {
        return containerBindingDetailMapper.getContainerBindingToStation(containerNo);
    }

    @Override
    public void saveInfo(ContainerBindingDetail containerBindingDetail) {
        containerBindingDetailMapper.save(containerBindingDetail);
    }

    @Override
    public List<ContainerBindingDetail> getBindingDetail(int pickOrderId,String containerNo) {
        return containerBindingDetailMapper.getBindingDetail(pickOrderId,containerNo);
    }
}
