package com.prolog.eis.dispatch;

import com.prolog.eis.dto.enginee.OrderDto;
import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.orderpool.service.OrderPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 半成品托盘库出库调度
 * sunPP
 */
@Service
public class TrayOutDispatch {
    /**
     * 1.agv区域 有空闲库位，判断订单所需库存 在立库中是否满足，并选择满足的订单优先出库到 agv区域
     * 2.立库的出库 根据订单 首先将AGV区域出满，并且保存 并 保存 agv区域所出库的订单
     * 3.agv出库，站台绑定agv区域 的订单 并出库agv区域的订单
     * 4.不满足的最后出库，并根据站台所需的订单 再从箱库进行出库
     */
    @Autowired
    private OrderPoolService orderPoolService;

    public void trayOut() {
        /**
         *
         * 1.查找agv空位，有空位就出
         * 2.订单池获取订单,优先出库 立库 里库存满足的订单 computerHandleOrder()
         * 3.出库调度 goodsId,找托盘，这个根据商品数量
         */
    }

    /**
     * 基于内存计算，立库库存满足的订单
     *
     * @return
     * @throws Exception
     */
    private List<OrderDto> computerHandleOrder() throws Exception {
        // 获得订单池的订单
        List<OpOrderHz> opOrderList = orderPoolService.getOrderPool();
        List<OrderDto> orderList = new ArrayList<OrderDto>();
        if (opOrderList != null) {
            for (OpOrderHz opOrder : opOrderList) {
                //计算 订单里的 goodsId 在库存里是否满足
                // 满足库存的订单加入 orderPoolList

            }
        }
        return orderList;
    }

    /**
     * 基于订单开始出库
     * @param orderList
     * @throws Exception
     */
    private void outFromOrderBill(List<OrderDto> orderList)throws Exception{

    }

}
