package com.prolog.eis.out.service;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.out.EmptyBoxBackDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.wcs.service.IWCSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.SaslServer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author SunPP
 * Description:三十功名尘与土，八千里路云和月
 * @return
 * @date:2020/10/26 10:49
 */
public class EmptyBoxBackServiceImpl implements EmptyBoxBackService {

    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Autowired
    private BoxOutEnginService boxOutEnginService;
    @Autowired
    private ContainerStoreMapper containerStoreMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void outEmptyBox(EmptyBoxBackDto emptyBoxBackDto) throws Exception {

        List<OutContainerDto> emptyBoxs = boxOutEnginService.outByGoodsId(-10, emptyBoxBackDto.getQTY());
        if (emptyBoxs.size() < emptyBoxBackDto.getQTY()) {
            throw new Exception("当前空料箱数量" + emptyBoxs.size() + "不满足出库数量" + emptyBoxBackDto.getQTY());
        }
        for (int i = 0; i < emptyBoxBackDto.getQTY(); i++) {
            pathSchedulingService.containerMoveTask(emptyBoxs.get(i).getContainerNo(),
                    StoreArea.WCS061, null);
        }
        List<String> containers = emptyBoxs.stream().map(OutContainerDto::getContainerNo).collect(Collectors.toList());
        String strs = String.join(",", containers);
        containerStoreMapper.updateContainerStatus(strs, ContainerStore.TASK_TYPE_INVENTORY_BACK);
    }
}
