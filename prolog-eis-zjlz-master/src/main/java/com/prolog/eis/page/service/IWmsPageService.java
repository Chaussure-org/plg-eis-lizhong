package com.prolog.eis.page.service;

import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;
import com.prolog.eis.dto.inventory.InventoryShowDto;

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


    /**
     * 查看盘点详情 发送前端
     * @param containerNo
     * @return
     */
    InventoryShowDto findInventoryDetail(String containerNo) throws Exception;

    /**
     * 盘点执行
     * @param containerNo 容器号
     * @param qty  数量
     */
    void doInventoryTask(String containerNo,int qty) throws Exception;


    /**
     * 获取成品库未出库
     * @return
     */
    FinishNotSeedDTO getNotSeedCount() throws Exception;


    /**
     * 索取订单
     */
    void changeStationIsLock(int isLock) throws Exception;


    /**
     * 播种确认
     * @param containerNo
     * @throws Exception
     * @param num
     */
    void confirmSeed(String containerNo,int num) throws Exception;

    /**
     * 得到播种界面
     * @param containerNo
     * @return
     * @throws Exception
     */
    FinishTrayDTO getFinishSeed(String containerNo) throws Exception;
}
