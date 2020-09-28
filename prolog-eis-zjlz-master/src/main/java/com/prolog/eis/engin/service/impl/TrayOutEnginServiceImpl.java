package com.prolog.eis.engin.service.impl;

import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.store.dao.OContainerStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrayOutEnginServiceImpl implements TrayOutEnginService {

    @Autowired
    private OContainerStoreMapper containerStoreMapper;
    /**
     * 1.出库时效
     * 2.出库 订单在立库里 库存满足的订单 在订单明细里记状态 出库至 agv 区域
     * 3.不满足时 判断
     * 该订单明细 在立库和箱库 所需要出库的 容器的数量
     * 数量少的 标记 目的的出库位置
     *
     * @throws Exception
     */
    @Override
    public List<OrderBill> computerOrder(List<OrderBill> orderBills) throws Exception {
        /**
         * 1.查所有的订单
         */
        List<ContainerStore> containerStoreList = containerStoreMapper.findByMap(null, ContainerStore.class);
        Map<Integer, Integer> collect = containerStoreList.stream().collect(Collectors.toMap(ContainerStore::getGoodsId, ContainerStore::getQty));
        return null;
    }

    @Override
    public void trayOutByOrder(List<OrderBill> orderBills) throws Exception {

    }
    /**
     * 1.根据goodsId count 找到具体的托盘 货位进行出库
     * 规则：
     * 其实 也就是 生成 料箱出库明细  料箱出库汇总
     * 生成路径 path 给点位
     * @throws Exception
     */
    @Override
    public void OutByGoodsId(int goodsId,int count) throws Exception {
        /** 1.计算每层的总任务数
         * 2.计算每层的出库作业数
         3.计算每层的入库作业数
         4.计算商品在每层非锁定的料箱数量
         5.得到 每一层总的任务数  出库任务数 入库 任务数 非锁定的料箱数量
         6.排序 保证出库任务的均衡
         // 如果巷道作业数排序相同，再按层出库任务数排序（升序）
         // 如果层出库任务数相同，再按层入库任务数排序（升序）
         // 如果层任务数相同，再按料箱数排序（降序）
         7.找到是哪一个层
         8.找到本层 该商品的货位和数量 以及移位数 最少的，离提升机 距离最近
         满足明细数量的，注：尾托的 概念 以及比例的选择
         */



    }
}
