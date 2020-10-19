package com.prolog.eis.mcs.service;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.mcs.McsResultDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:55
 */
public interface IMCSService {

    /**
     * 发送Mcs任务
     * @param mcsMoveTaskDto mcs任务实体
     * @return
     * @throws Exception
     */
    McsResultDto mcsContainerMove(McsMoveTaskDto mcsMoveTaskDto) throws Exception;
}
