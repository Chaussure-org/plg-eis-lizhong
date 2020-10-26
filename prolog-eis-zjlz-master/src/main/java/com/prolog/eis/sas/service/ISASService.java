package com.prolog.eis.sas.service;

import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.eis.dto.wcs.SasMoveCarDto;
import com.prolog.framework.common.message.RestMessage;

import java.io.IOException;
import java.util.List;

public interface ISASService {
    /**
     * 获取每层小车信息
     * @return
     */
    List<CarInfoDTO> getCarInfo() throws IOException;

    /**
     * 获取三个提升机的信息
     * @return
     */
    List<HoisterInfoDto> getHoisterInfoDto() throws Exception;

    /**
     * 小车换层
     * @param sasMoveCarDto 小车换层实体
     * @return
     */
    RestMessage<String> moveCar(SasMoveCarDto sasMoveCarDto) throws Exception;

    /**
     * 出入库指令
     * @param sasMoveTaskDto 出入库实体
     * @return
     */
    RestMessage<String> sendContainerTask(SasMoveTaskDto sasMoveTaskDto) throws Exception;


    /**
     * 删除容器信息
     * @param taskId
     * @param containerNo
     * @return
     * @throws Exception
     */
    RestMessage<String> deleteAbnormalContainerNo(String taskId, String containerNo) throws Exception;
}
