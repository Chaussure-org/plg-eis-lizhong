package com.prolog.eis.engin.service;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
import com.prolog.eis.dto.lzenginee.boxoutdto.LayerTaskDto;

import java.util.List;

/**
 * ClassName:BoxOutEnginService
 * Package:com.prolog.eis.engin.service
 * Description:
 *
 * @date:2020/9/30 11:46
 * @author:SunPP
 */
public interface BoxOutEnginService {
    /**
     * 料箱根据订单出库
     */
    void BoxOutByOrder() throws Exception;

    List<OutContainerDto> outByGoodsId(int goodsId,int count) throws Exception;

    List<Integer> computeRepeat(List<OutDetailDto> lineDetailList) throws Exception;

    /**
     *计算箱库层任务数
     * @return
     */
    List<LayerTaskDto> computeBoxLayerTask();

}
