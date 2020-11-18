package com.prolog.eis.mcs.service;

import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.mcs.McsResultDto;
import com.prolog.eis.dto.wcs.HoisterInfoDto;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:55
 */
public interface IMcsService {

    /**
     * 发送Mcs任务
     * @param mcsMoveTaskDto mcs任务实体
     * @return
     * @throws Exception
     */
    McsResultDto mcsContainerMove(McsMoveTaskDto mcsMoveTaskDto) throws Exception;

    /**
     * 获取堆垛机的信息
     * @return
     */
    List<McsCarInfoDto> getMcsCarInfo() throws Exception;
}
