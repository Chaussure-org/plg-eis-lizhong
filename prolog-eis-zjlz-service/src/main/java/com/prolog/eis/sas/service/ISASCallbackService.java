package com.prolog.eis.sas.service;

import com.prolog.eis.dto.wcs.*;
import com.prolog.framework.common.message.RestMessage;

public interface ISASCallbackService {
    /**
     * 任务回告
     * @param taskCallbackDTO
     * @return
     */
    RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO);

}
