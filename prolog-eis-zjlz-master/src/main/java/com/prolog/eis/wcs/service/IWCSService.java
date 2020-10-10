package com.prolog.eis.wcs.service;

import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.framework.common.message.RestMessage;

import java.util.List;

public interface IWCSService {


    /**
     * 输送线行走
     *
     * @param taskId
     * @param address
     * @param target
     * @param containerNo
     * @param type
     * @return
     */
    RestMessage<String> lineMove(String taskId, String address, String target, String containerNo, int type);


    /**
     * 请求订单箱
     *
     * @param taskId
     * @param address
     * @return
     */
    RestMessage<String> requestOrderBox(String taskId, String address);

    /**
     * 亮灯
     *
     * @param pickStationNo 站台id
     * @param lights        灯
     * @return
     */
    RestMessage<String> light(String pickStationNo, String[] lights);


    /**
     * 安全门控制
     *
     * @param doorNo
     * @param open
     * @throws Exception
     */
    void openDoor(String doorNo, boolean open) throws Exception;

}
