package com.prolog.eis.pick.service;

import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/18 20:26
 * 成品库播种
 */
public interface IStationFinishSeedService {

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
     * 成品拖离开
     * @param containerNo
     * @throws Exception
     */
    void finishTrayLeave(String containerNo) throws Exception;

    /**
     * 得到播种界面
     * @param containerNo
     * @return
     * @throws Exception
     */
    FinishTrayDTO getFinishSeed(String containerNo) throws Exception;
    
    
}
