package com.prolog.eis.order.service;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;
import com.prolog.eis.dto.bz.PickWmsDto;
import com.prolog.eis.dto.page.OrderInfoDto;
import com.prolog.eis.dto.page.OrderQueryDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.framework.core.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:52
 */
public interface IOrderBillService {

    /**
     * 保存订单
     * @param orderBill 订单实体
     */
    void saveOrderBill(OrderBill orderBill);

    /**
     * 通过billNo更新订单优先级
     * @param billNo 订单号
     */
    void upOrderProiorityByBillNo(String billNo) throws Exception;

    /**
     * 根据id查询查询订单
     * @param orderBillId 订单号
     * @return
     */
     OrderBill findBillById(int orderBillId);

    /**
     * 汇总转历史
     * @param orderBillId 订单号
     */
     void orderBillToHistory(int orderBillId);

    /**
     * 根据map删除
     * @param map map
     */
    void deleteOrderBillByMap(Map map);

    /**
     * 初始化成品库订单
     * @return
     */
    List<OrderBillDto> initFinishProdOrder(Map<Integer,Integer> map);

    /**
     * 修改订单及明细状态
     * @param orderDetail 订单详情
     */
    void updateOrderBillStatus(OrderDetail orderDetail) throws Exception;

    /**
     * 订单明细级别回告wms
     * @param orderBillId
     * @return
     */
    List<PickWmsDto> findWmsOrderBill(int orderBillId);

    /**
     * 获取成品库未完成订单总量及明细情况
     * @return
     */
    FinishNotSeedDTO getNoSeedCount();

    /**
     * 校验容器是否在当前站台有播种任务
     * @param stationId
     * @param containerNo
     * @return
     */
    int checkContainer(int stationId,String containerNo);


    /**
     * 成品库播种详情
     * @param containerNo
     * @return
     */
    List<FinishTrayDTO> getFinishSeedInfo(String containerNo,int pickingOrderId);

    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<OrderBill> findByMap(Map map);

    /**
     * 订单详情分页
     * @param orderQueryDto
     * @return
     */
    Page<OrderInfoDto> getOrderPage(OrderQueryDto orderQueryDto);
}
