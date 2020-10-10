package com.prolog.eis.mcs.service;

import com.prolog.eis.dto.mcs.McsResultDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:55
 */
public interface IMCSService {

    /**
     * 发送Mcs任务
     * @param containerNo 容器号
     * @param type 任务类型 1入库 2出库 3同层移库 4输送线前进
     * @param address 起点
     * @param target 终点
     * @param priority 优先级 0~99 越小越优先
     * @return
     * @throws Exception
     */
    McsResultDto mcsContainerMove(String taskId, String containerNo, int type, String address, String target, String priority) throws Exception;
}
