package com.prolog.eis.pick.service;

import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.dto.bz.OrderTrayWeighDTO;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.station.Station;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/9/28 12:07
 */
public interface IStationBZService {
    /**
     * 开始拣选
     * @param stationId 站台id
     * @param containerNo 容器编号
     * @param orderBoxNo 订单框编号
     */
    BCPPcikingDTO startBZPicking(int stationId, String containerNo, String orderBoxNo,String locationNo) throws Exception;

    /**
     * 拣选确认
     */
    void pickingConfirm(int stationId,String containerNo,String orderBoxNo,int completeNum) throws Exception;
    /**
     * 检查容器是否是当前拣选站所需
     */
    boolean checkContainerToStation(String containerNo,int stationId);
    /**
     * 校验托盘或料箱是否在拣选站
     */
    boolean checkContainerExist(String containerNo,int stationId) throws Exception;

    /**
     * 检查订单拖是否在当前拣选站
     * @param orderTrayNo
     * @param stationId
     * @param areaNo
     * @return
     */
    boolean checkOrderTrayNo(String orderTrayNo,int stationId,String areaNo) throws Exception;

    /**
     * 订单转历史
      * @param orderBillId   订单汇总id
     */
    void orderToHistory(int orderBillId) throws Exception;

    /**
     * 物料容器离开
     * @param containerNo
     * @throws Exception
     */
    void  containerNoLeave(String containerNo,int stationId) throws Exception;

    /**
     * 订单拖放行 或换拖
     * @param orderTrayNo
     *
     * @param orderBillId
     * @throws Exception
     */
    void orderTrayLeave(String orderTrayNo,int orderBillId) throws Exception;

    /**
     * 计算尾拖
     * @param containerNo
     * @return
     */
    boolean computeLastTray(String containerNo) throws Exception;

    /**
     * 计算绑定料箱去最近的一个站台
     * @param stationIds
     * @param sourceStation
     * @return
     */
    int computeContainerTargetStation(List<Integer> stationIds,int sourceStation);


    /**
     * 切换拣选单
     * @param station
     */
    void changePickingOrder(Station station) throws Exception;

    /**
     * 播种明细回告wms
     * @param containerBindingDetail
     */
    void seedToWms(ContainerBindingDetail containerBindingDetail) throws Exception;


    /**
     * 更换订单拖
     * @param orderTrayNo
     * @param stationId
     */
    void changeOrderTray(String orderTrayNo,int stationId) throws Exception;


    /**
     * 进行播种
     * @param stationId
     * @param containerNo
     * @param completeNum
     * @param orderBillId
     * @param orderBoxNo
     * @throws Exception
     */
    void doPicking(int stationId, String containerNo,int completeNum,int orderBillId,String orderBoxNo) throws Exception;

    /**
     * 找寻站台托盘到达的合适点位
     * @param stationIds
     * @param containerNo
     * @throws Exception
     */
    void  computeTrayStation(List<Integer> stationIds,String containerNo) throws Exception;

    /**
     * 称重校验
     * @param stationId
     * @param orderDetailId
     * @param passBoxNo
     * @return
     * @throws Exception
     */
    OrderTrayWeighDTO weighCheck(int stationId, int orderDetailId, String passBoxNo) throws Exception;

    /**
     * 保存订单拖重量
     * @param orderBillId
     * @param orderTrayNo
     */
    void saveTrayWeigh(int orderBillId, String orderTrayNo,String locationNo) throws Exception;


    /**
     * 拣选完成操作
     * @param stationId
     * @param containerNo
     * @param orderTrayNo
     * @param orderBillId
     */
    void pickingComplete(int stationId,String containerNo,String orderTrayNo,int orderBillId) throws Exception;
}
