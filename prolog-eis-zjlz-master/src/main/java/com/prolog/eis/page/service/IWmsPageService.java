package com.prolog.eis.page.service;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/19 18:10
 */
public interface IWmsPageService {

    /**
     * 空托上架入库
     * @param containerNo
     * @param feederNo
     * @throws Exception
     */
    void emptyTrayPutaway(String containerNo,String feederNo) throws Exception;


    /**
     * 接驳口下架释放货位
     * @param containerNo
     * @param feederNo
     */
    void storeRelease(String containerNo,String feederNo) throws Exception;
}
