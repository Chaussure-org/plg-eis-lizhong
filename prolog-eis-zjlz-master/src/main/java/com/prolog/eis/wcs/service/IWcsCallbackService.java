package com.prolog.eis.wcs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prolog.eis.dto.wcs.*;
import com.prolog.framework.common.message.RestMessage;

public interface IWcsCallbackService {
    /**
     * 任务回告
     * @param taskCallbackDTO
     * @return
     */
    RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO);

    /**
     * BCR 回告
     * @param bcrDataDTO
     * @return
     */
    RestMessage<String> executeBcrCallback(BCRDataDTO bcrDataDTO) throws Exception;


    /**
     * 拆盘机入口回告
     * @param openDiskDto
     * @return
     */
    RestMessage<String> openDiskEntranceCallback(OpenDiskDto openDiskDto) throws JsonProcessingException;

    /**
     * 拆盘机出口回告
     * @param openDiskDto
     * @return
     */
    RestMessage<String> openDiskOuTCallback(OpenDiskFinishDto openDiskDto) throws JsonProcessingException;

    /**
     * 拣选站容器放行
     * @param containerLeaveDto
     * @return
     */
    RestMessage<String> containerLeave(ContainerLeaveDto containerLeaveDto) throws Exception;
}
