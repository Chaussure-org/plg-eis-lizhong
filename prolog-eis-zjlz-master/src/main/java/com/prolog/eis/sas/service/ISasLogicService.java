package com.prolog.eis.sas.service;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.util.LogInfo;

/**
 * @Author wangkang
 * @Description 逻辑处理
 * @CreateTime 2020-12-11 14:32
 */
public interface ISasLogicService {
    /**
     * 移位任务回告
     * @param taskCallbackDTO
     * @throws Exception
     */
    void doMoveTask(TaskCallbackDTO taskCallbackDTO) throws Exception;

    /**
     * 出库任务回告
     * @param taskCallbackDTO
     * @throws Exception
     */
    void doOutboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception;

    /**
     * 入库任务回告
     * @param taskCallbackDTO
     * @throws Exception
     */
    void doInboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception;

    /**
     * 换层任务回告
     * @param taskCallbackDTO
     * @throws Exception
     */
    void doHcTask(TaskCallbackDTO taskCallbackDTO) throws Exception;
}
