package com.prolog.eis.location.service;

/**
 * @author: wuxl
 * @create: 2020-08-25 14:00
 * @Version: V1.0
 */
public interface LocationService {

    /**
     * 生成容器路径任务
     * @param palletNo
     * @param containerNo
     * @throws Exception
     */
    void doContainerPathTaskByContainer(String palletNo, String containerNo) throws Exception;

    /**
     * 执行容器任务
     * @param palletNo
     * @param containerNo
     * @throws Exception
     */
    void doPathExecutionByContainer(String palletNo, String containerNo) throws Exception;

    /**
     * 生成并执行容器任务
     * @param palletNo
     * @param containerNo
     * @throws Exception
     */
    void doContainerPathTaskAndExecutionByContainer(String palletNo, String containerNo) throws Exception;
}
