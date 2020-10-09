package com.prolog.eis.sas.service;

import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.framework.common.message.RestMessage;

import java.util.List;

public interface ISASService {
    /**
     * 获取每层小车信息
     * @return
     */
    List<CarInfoDTO> getCarInfo();

    /**
     * 获取三个提升机的信息
     * @return
     */
    List<HoisterInfoDto> getHoisterInfoDto();

    /**
     * 小车换层
     * @param taskId
     * @param carId
     * @param sourceLayer
     * @param targetLayer
     * @return
     */
    RestMessage<String> moveCar(String taskId, int carId, int sourceLayer, int targetLayer);

    /**
     * 出入库指令
     * @param taskId
     * @param type
     * @param containerNo
     * @param address
     * @param target
     * @param weight
     * @param priority
     * @return
     */
    RestMessage<String> sendContainerTask(String taskId, int type, String containerNo, String address, String target,
                                          String weight, String priority, int status);


    /**
     * 删除容器信息
     * @param taskId
     * @param containerNo
     * @return
     * @throws Exception
     */
    RestMessage<String> deleteAbnormalContainerNo(String taskId, String containerNo) throws Exception;
}
