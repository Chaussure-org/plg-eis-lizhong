package com.prolog.eis.order.service;

import com.prolog.eis.model.order.ContainerBindingDetail;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:05
 */
public interface IContainerBindingDetailService {

    /**
     * 根据map查询
     */
    List<ContainerBindingDetail> findMap(Map map) throws Exception;
}
