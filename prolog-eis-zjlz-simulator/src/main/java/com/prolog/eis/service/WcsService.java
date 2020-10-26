package com.prolog.eis.service;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:20
 */
public interface WcsService {

    /**
     * 回告
     * @param wcsLineMoveDto
     */
    void doCallBack(WcsLineMoveDto wcsLineMoveDto);

    /**
     * bcr请求
     * @param wcsLineMoveDto
     * @param type 类型
     */
    void doBcrRequest(WcsLineMoveDto wcsLineMoveDto, int type);
}
