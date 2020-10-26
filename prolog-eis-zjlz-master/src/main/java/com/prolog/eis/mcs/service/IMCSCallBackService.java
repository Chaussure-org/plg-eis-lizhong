package com.prolog.eis.mcs.service;

import com.prolog.eis.dto.mcs.McsCallBackDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:55
 */
public interface IMCSCallBackService {


    /**
     * 根据回告内容执行操作
     * @param mcsCallBackDto 回告内容
     * @throws Exception
     */
    void mcsCallback(McsCallBackDto mcsCallBackDto) throws Exception;
}
