package com.prolog.eis.engin.service;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;

/**
 *
 */
public interface TrayOutEnginService {
    /**
     * 1.出库时效
     * 2.出库 订单在立库里 库存满足的订单 在订单明细里记状态 出库至 agv 区域
     * 3.不满足时 判断
     * 该订单明细 在立库和箱库 所需要出库的 容器的数量
     * 数量少的 标记 目的的出库位置
     *
     * @throws Exception
     */
    void initOrder() throws Exception;

    /**
     * 根据 合适的订单
     * 1.根据agv 的 库位数量进行出库
     * 2.找到 属于agv 订单区域的订单
     * @throws Exception
     */
    void trayOutByOrder() throws Exception;

    /**
     * 1.根据goodsId count 找到具体的托盘进行出库
     * 规则：
     * 其实 也就是 生成 料箱出库明细  料箱出库汇总
     * 生成路径 path 给点位
     * @throws Exception
     */
    List<OutContainerDto> outByGoodsId(int goodsId, int count) throws Exception;

    List<OutContainerDto> outByDetails(List<OutDetailDto> detailDtos) throws Exception;
}
