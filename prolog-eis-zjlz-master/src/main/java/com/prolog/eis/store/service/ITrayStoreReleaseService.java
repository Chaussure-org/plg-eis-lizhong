package com.prolog.eis.store.service;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/3 10:57
 * 接驳口下架服务类
 */
public interface ITrayStoreReleaseService {

    /**
     * 货位释放
     * @param containerNo
     * @param feederNo
     */
    void storeRelease(String containerNo,String feederNo) throws Exception;


    /**
     * 空托上架入库
     * @param trayNo
     * @param transhipNo
     * @throws Exception
     */
    void emptyTrayPull(String trayNo,String transhipNo) throws Exception;
}
