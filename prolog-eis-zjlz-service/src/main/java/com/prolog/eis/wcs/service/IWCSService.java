package com.prolog.eis.wcs.service;

import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.framework.common.message.RestMessage;

import java.util.List;

public interface IWCSService {
    /**
     * 获取每层小车信息
     * @return
     */
    List<CarInfoDTO> getCarInfo();

    /**
     * 获取三个提升机的信息
     *
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
     * 输送线行走
     * @param taskId
     * @param address
     * @param target
     * @param containerNo
     * @param type
     * @return
     */
    RestMessage<String> lineMove(String taskId, String address, String target, String containerNo, int type, int i);


    /**
     * 请求订单箱
     * @param taskId
     * @param address
     * @return
     */
    RestMessage<String> requestOrderBox(String taskId, String address);

    /**
     * 亮灯
     * @param pickStationNo 站台id
     * @param lights 灯
     * @return
     */
    RestMessage<String> light(String pickStationNo, String[] lights);

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
     * 安全门控制
     * @param doorNo
     * @param open
     * @throws Exception
     */
    void openDoor(String doorNo, boolean open) throws Exception;


    /**
     * 料箱入库异常删除
     */
    RestMessage<String> deleteAbnormalContainerNo(String taskId, String containerNo) throws Exception;
}
