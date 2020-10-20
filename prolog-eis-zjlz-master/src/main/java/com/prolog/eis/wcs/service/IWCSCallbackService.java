package com.prolog.eis.wcs.service;

import com.prolog.eis.dto.wcs.*;
import com.prolog.framework.common.message.RestMessage;

public interface IWCSCallbackService {
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
     * 料箱、订单框到位回告
     * @param boxCallbackDTO
     * @return
     */
    RestMessage<String> executeBoxArriveCallback(BoxCallbackDTO boxCallbackDTO);

    /**
     * 料箱弹出完成回告
     * @param boxCompletedDTO
     * @return
     */
    RestMessage<String> executeCompleteBoxCallback(BoxCompletedDTO boxCompletedDTO);


    /**
     * 拍灯回告
     * @param lightDTO
     * @return
     */
    RestMessage<String> executeLightCallback(LightDTO lightDTO);



}
