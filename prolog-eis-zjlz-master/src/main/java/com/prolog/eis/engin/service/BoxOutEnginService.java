package com.prolog.eis.engin.service;

import com.prolog.eis.dto.lzenginee.OutContainerDto;

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

    List<OutContainerDto> outByGoodsId() throws Exception;
}
