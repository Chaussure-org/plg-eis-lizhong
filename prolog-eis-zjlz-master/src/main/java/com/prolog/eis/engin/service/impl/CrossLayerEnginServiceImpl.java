package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.engin.service.CrossLayerEnginService;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author SunPP
 * Description:三十功名尘与土，八千里路云和月
 * @return
 * @date:2020/10/26 15:47
 */
@Service
public class CrossLayerEnginServiceImpl implements CrossLayerEnginService {

    @Autowired
    private ContainerStoreMapper containerStoreMapper;
    @Autowired
    private ISASService isasService;

    @Override
    public void findCrossLayerTask() throws Exception {
        List<LayerGoodsCountDto> outContainers = containerStoreMapper.findOutContainers();
        isasService.getCarInfo();
    }

    @Override
    public void sendCrossLayerTask() throws Exception {

    }

    @Override
    public void saveCrossLayerTask() throws Exception {

    }
}
