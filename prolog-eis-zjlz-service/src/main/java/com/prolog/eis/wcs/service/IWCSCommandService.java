package com.prolog.eis.wcs.service;

import com.prolog.eis.wcs.model.WCSCommand;

import java.util.List;

public interface IWCSCommandService {
    void add(WCSCommand command) throws Exception;
    void delete(int id);
    List<WCSCommand> getAll();

    List<WCSCommand> getByContainerNo(String containerNo);
    /**
     * 通过求余拆分记录集
     * @param denominator 分母
     * @param remainder 余数
     * @return
     * @throws Exception
     */
    List<WCSCommand> getByMod(int denominator, int remainder) throws Exception;

    /**
     * 发送指令
     * @param command
     * @throws Exception
     */
    boolean sendCommand(WCSCommand command) throws Exception;

    /**
     * 根据类型查询任务
     * @param type
     * @return
     */
    List<WCSCommand> getByType(int type);

    /**
     * 根据目标点获取命令
     * @param target
     * @return
     */
    List<WCSCommand> getByTarget(String target);

    long getCountByTarget(String target);

    /**
     * 获取命令排除目标点
     * @param targets
     * @return
     */
    List<WCSCommand> getExceptTargets(String[] targets);




    /**
     * 发送亮灯指令
     * @param taskId
     * @param stationNo
     * @param lights
     * @return
     */
    boolean sendLightCommand(String taskId, String stationNo, String lights) throws Exception;

    /**
     * 发送行走指令
     * @param taskId
     * @param address
     * @param target
     * @param containerNo
     * @return
     */
    boolean sendXZCommand(String taskId, String address, String target, String containerNo) throws Exception;

    /**
     * 发送出库指令
     * @param taskId
     * @param containerNo
     * @param address
     * @param target
     * @param weight
     * @param priority
     * @return
     */
    boolean sendCKCommand(String taskId, String containerNo, String address, String target, String weight, String priority) throws Exception;

    /**
     * 发送入库指令
     * @param taskId
     * @param containerNo
     * @param address
     * @param target
     * @param weight
     * @param priority
     * @return
     */
    boolean sendRKCommand(String taskId, String containerNo, String address, String target, String weight, String priority) throws Exception;

    /**
     * 发送订单框请求指令
     * @param taskId
     * @return
     */
    boolean sendOrderBoxReqCommand(String taskId, String target)  throws Exception;

    WCSCommand getById(int id);

    //发送换层指令
    boolean sendHCCommand(String taskId, int sourceLayer, int targetLayer) throws Exception;

}
