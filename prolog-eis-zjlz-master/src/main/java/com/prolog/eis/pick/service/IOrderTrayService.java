package com.prolog.eis.pick.service;

/**
 * @author dengj
 * @version 1.0 订单拖
 * @date 2020/10/20 19:06
 */
public interface IOrderTrayService {


    /**
     * 请求订单拖
     */
    void requestOrderTray() throws Exception;



    /**
     * 订单拖出库
     */
    void orderTrayOut() throws Exception;


}
