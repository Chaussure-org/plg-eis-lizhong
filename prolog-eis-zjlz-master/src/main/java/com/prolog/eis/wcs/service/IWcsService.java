package com.prolog.eis.wcs.service;

import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.framework.common.message.RestMessage;

import java.util.List;

public interface IWcsService {


    /**
     * 输送线行走
     *
     * @param wcsLineMoveDto 输送线实体
     * @return
     */
    RestMessage<String> lineMove(WcsLineMoveDto wcsLineMoveDto) throws Exception;


//    /**
//     * 请求订单箱
//     *
//     * @param taskId
//     * @param address
//     * @return
//     */
//    RestMessage<String> requestOrderBox(String taskId, String address);
//
//    /**
//     * 亮灯
//     *
//     * @param pickStationNo 站台id
//     * @param lights        灯
//     * @return
//     */
//    RestMessage<String> light(String pickStationNo, String[] lights);
//
//
//    /**
//     * 安全门控制
//     *
//     * @param doorNo
//     * @param open
//     * @throws Exception
//     */
//    void openDoor(String doorNo, boolean open) throws Exception;

}
