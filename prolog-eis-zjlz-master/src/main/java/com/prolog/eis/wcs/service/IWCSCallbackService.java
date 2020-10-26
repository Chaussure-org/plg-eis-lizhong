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



}
