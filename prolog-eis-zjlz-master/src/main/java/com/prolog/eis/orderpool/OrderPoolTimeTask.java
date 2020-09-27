package com.prolog.eis.orderpool;

import com.prolog.eis.orderpool.service.OrderPoolService;
import com.prolog.eis.util.FileLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单池调度
 */
@Component
public class OrderPoolTimeTask {

    @Autowired
    private OrderPoolService orderPoolService;

//    public static OrderPoolManager orderPoolManager = new OrderPoolManager();

    /**
     * 订单池调度
     *
     * @throws Exception
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 5000)
    public void orderPool() throws Exception {
        try {
            //获取新订单
            orderPoolService.pullNewOrders();
        } catch (Exception e) {
            FileLogHelper.writeLog("updateOrderPool", e.getMessage());
        }
    }

}
