package com.prolog.eis.service;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:20
 */
public interface RcsService {

    /**
     * 回告
     * @param rcsTaskDto
     */
    void doCallBack(RcsTaskDto rcsTaskDto);
}
