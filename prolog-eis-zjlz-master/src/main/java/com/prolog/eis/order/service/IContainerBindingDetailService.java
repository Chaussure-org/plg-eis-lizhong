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
     * @param map
     * @return
     * @throws Exception
     */
    List<ContainerBindingDetail> findMap(Map map) throws Exception;

    /**
     * 根据map删除
     * @param map
     */
    void deleteContainerDetail(Map map);

    /**
     *   获取容器绑定的站台
     * @param containerNo
     * @return
     */
    List<Integer> getContainerBindingToStation(String containerNo);

    /**
     * 保存容器绑定相亲
     * @param containerBindingDetail 详情实体
     */
    void saveInfo(ContainerBindingDetail containerBindingDetail);


    /**
     * 根据拣选单和容器编号获取绑定汇总
     * @param pickOrderId
     * @param containerNo
     * @return
     */
    List<ContainerBindingDetail> getBindingDetail(int pickOrderId,String containerNo);
}
