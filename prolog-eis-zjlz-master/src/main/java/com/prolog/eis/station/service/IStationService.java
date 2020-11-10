package com.prolog.eis.station.service;

import com.prolog.eis.dto.inventory.StationTaskDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.vo.station.StationInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:29
 */
public interface IStationService {

    /**
     * 根据id查找
     *
     * @param stationId
     * @return
     * @throws Exception
     */
    Station findById(int stationId) throws Exception;

    /**
     * 修改对象
     *
     * @param station
     * @throws Exception
     */
    void updateStation(Station station) throws Exception;

    /**
     * 根据map查询
     *
     * @param map
     * @return
     */
    List<Station> findStationByMap(Map map);

    /**  获取当前站台的订单汇总id
     * @param stationId
     * @return
     */
    List<Integer> findPickingOrderBillId(int stationId);

    /**
     * 检查站台状态
     * @return
     */
    boolean checkStationStatus() throws Exception;

    /**
     * 清空站台拣选单id
     */
    void clearStationPickingOrder(int stationId);

    /**
     * 通过containerNo在站台找到相应的绑定任务
     * @param containerNo
     * @return
     */
    List<ContainerTaskDto> getTaskByContainerNo(String containerNo);

    /**
     * 成品库站台索取订单
     * @throws Exception
     * @param isLock
     */
    void changeFinishStationStatus(int isLock) throws Exception;

    /**
     * 原料拣选站播种切换
     * @param stationId
     * @param isLock
     * @throws Exception
     */
    void changeStationIsLock(int stationId,int isLock) throws Exception;


    /**
     * 获取站台id
     * @param stationIp
     * @return
     */
    Station getStationId(String stationIp) throws Exception;


    /**
     * 获取站台盘点任务数
     */
    List<StationTaskDto> getStationTask();

    /**
     * 修改站台作业类型
     * @param stationInfoDto
     * @throws Exception
     */
    void updateStationTaskType(StationInfoDto stationInfoDto) throws Exception;

    /**
     * 查所有站台信息
     * @return
     */
    List<StationInfoVo> queryAll();

    /**
     * 根据站台id查站台信息
     * @param stationId
     */
    StationInfoVo queryById(int stationId);
}
