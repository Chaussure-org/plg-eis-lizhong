package com.prolog.eis.service;

import com.prolog.eis.dto.sas.SasMoveTaskDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:20
 */
public interface SasService {

    /**
     * 回告
     * @param sasMoveTaskDto
     */
    void doCallBack(SasMoveTaskDto sasMoveTaskDto);
}
