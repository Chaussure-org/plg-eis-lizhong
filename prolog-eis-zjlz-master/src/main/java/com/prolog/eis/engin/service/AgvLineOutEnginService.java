package com.prolog.eis.engin.service;

import com.prolog.eis.dto.lzenginee.PickingAreaDto;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;

/**
 * agv区域和输送线的 出库
 * @author sunpp
 */
public interface AgvLineOutEnginService {

    void computerPickOrder() throws Exception;
}
