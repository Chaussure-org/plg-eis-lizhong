package com.prolog.eis.engin.service;

/**
 * @Author wangkang
 * @Description 成品库出库
 * @CreateTime 2020-10-14 16:01
 */
public interface FinishedProdOutEnginService {

    /**
     * 成品出库调度
     */
    void finishProdOutByOrder() throws Exception;
}
