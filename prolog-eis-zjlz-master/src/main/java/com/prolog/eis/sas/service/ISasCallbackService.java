package com.prolog.eis.sas.service;

import com.prolog.eis.dto.wcs.*;
import com.prolog.framework.common.message.RestMessage;

/**
* @Author  wangkang
* @Description  sas服务
* @CreateTime  2020-11-02 9:16
*/
public interface ISasCallbackService {
    /**
     * 任务回告
     * @param taskCallbackDTO
     * @return
     * @throws Exception
     */
    RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO) throws Exception;

}
