package com.prolog.eis.pick.service;

/**
 * @author dengj
 * @version 1.0 订单拖
 * @date 2020/10/20 19:06
 */
public interface IOrderTrayService {


    /**
     * 请求订单拖(拆盘机)
     * @throws Exception
     */
    void requestOrderTray() throws Exception;



    /**
     * 订单拖出库
     */
    void orderTrayOut() throws Exception;


    /**
     * 请求铁笼
     * @throws Exception
     */
    void requestIronTray() throws Exception;
}
