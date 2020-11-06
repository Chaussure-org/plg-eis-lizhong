package com.prolog.eis.service;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:20
 */
public interface McsService {

    /**
     * 回告
     * @param mcsMoveTaskDto
     */
    void doCallBack(McsMoveTaskDto mcsMoveTaskDto) throws Exception;
}
