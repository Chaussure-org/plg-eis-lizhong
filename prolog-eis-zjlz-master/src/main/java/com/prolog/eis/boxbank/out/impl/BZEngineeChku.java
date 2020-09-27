package com.prolog.eis.boxbank.out.impl;

import com.prolog.eis.boxbank.out.IOutService;
import com.prolog.eis.boxbank.rule.LayerLockRule;
import com.prolog.eis.boxbank.rule.StoreLocationDTO;
import com.prolog.eis.dao.enginee.EngineGetInitMapper;
import com.prolog.eis.dao.enginee.EngineOutboundMapper;
import com.prolog.eis.dto.enginee.*;
import com.prolog.eis.util.FileLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangkang
 */
@Service
public class BZEngineeChku {
    private final Logger logger = LoggerFactory.getLogger(BZEngineeChku.class);

    @Autowired
    private EngineGetInitMapper engineGetInitMapper;
    @Autowired
    private EngineOutboundMapper engineOutboundMapper;

    @Autowired
    private LayerLockRule layerLockRule;



    /**
     * 找到层之后 层的出库方法
     *
     * @param targetLayerId
     * @param spId
     * @param stationDto
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public boolean outboundByLayer(int targetLayerId, Integer spId, StationDto stationDto) throws Exception {
        StoreLocationDTO location = new StoreLocationDTO();
        location.setLayer(targetLayerId);

        boolean result = layerLockRule.execute(location, x -> {
            try {
                // 找出离目标点(x、y)最近的非锁定的该商品料箱（用内存计算）
                List<OutStoreHuoWeiDto> outStoreHuoWeiDtos = engineOutboundMapper.findAllOutboundCargoBox(spId, x.getLayer());
                if (outStoreHuoWeiDtos.size() == 0) {
                    return false;
                }

                int sourceX = 0;
                int sourceY = 0;
                for (OutStoreHuoWeiDto outStoreHuoWeiDto : outStoreHuoWeiDtos) {
                    int targetX = outStoreHuoWeiDto.getX();
                    int targetY = outStoreHuoWeiDto.getY();
                    double v = (sourceX - targetX) ^ 2 + (sourceY - targetY) ^ 2;
                    int distance = (int) Math.abs(Math.sqrt(v));
                    outStoreHuoWeiDto.setDistance(distance);
                }

                // 找出待出库的料箱
                OutStoreHuoWeiDto outStoreHuoWeiDto = bubblingSort(outStoreHuoWeiDtos);
                logger.info("+++++++++++++++++++++++" + outStoreHuoWeiDto.getContainerNo() + "++++++++++++++++++++++++");
                //生成料箱绑定汇总
                this.bindStation(outStoreHuoWeiDto.getStoreId(), outStoreHuoWeiDto.getContainerNo(), stationDto);
                //生成料箱绑定汇总 之后 往path 表里添加数据，发出下游指令方法
//                boolean checkMoveStore = outService.checkOut(outStoreHuoWeiDto.getContainerNo(),SxStore.TASKTYPE_BZ,zhanTaiDto.getZhanTaiId());
//                return checkMoveStore;
                return true;
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
                return false;
            }
        });

        if (!result) {
            //出库失败，让事务回滚
            throw new RuntimeException("出库失败");
        }
        return result;

    }


    /**
     * 根据离目标点距离，从小到大排序货位
     *
     * @param huoWeiDtos
     * @return
     */
    private OutStoreHuoWeiDto bubblingSort(List<OutStoreHuoWeiDto> huoWeiDtos) {
        OutStoreHuoWeiDto huoWeiDto1 = huoWeiDtos.get(0);
        if (huoWeiDtos.size() > 1) {
            for (int i = 1; i < huoWeiDtos.size(); i++) {
                OutStoreHuoWeiDto outStoreHuoWeiDto2 = huoWeiDtos.get(0);
                if (outStoreHuoWeiDto2.getDistance() < huoWeiDto1.getDistance()) {
                    huoWeiDto1 = outStoreHuoWeiDto2;
                }
            }
        }
        return huoWeiDto1;
    }


    /**
     * 绑定站台
     *
     * @param storeId
     * @param containerNo
     * @param station
     * @throws Exception
     */
    private void bindStation(int storeId, String containerNo, StationDto station) throws Exception {
        if (station == null) {
            FileLogHelper.writeLog("LiaoXiangChuKuLogError",
                    "LiaoXiangChuKu站台为空，xkKuCunId:" + storeId + ",containerNo:" + containerNo);

            throw new Exception("LiaoXiangChuKu站台为空!");
        }

        if (station.getNeedOutboundPickOrder() == null) {
            FileLogHelper.writeLog("LiaoXiangChuKuLogError", "站台" + station.getStationId() + "的出库拣选单为空，xkKuCunId:"
                    + storeId + ",containerNo:" + containerNo);

            throw new Exception("站台" + station.getStationId() + "的出库拣选单为空!");
        }

        FileLogHelper.writeLog("LiaoXiangChuKuLog", "料箱出库后生成数据:xkKuCunId:" + storeId + ",containerNo:"
                + containerNo + ",zhanTaiId:" + station.getStationId());

        try {

            ContainerDto container = new ContainerDto();
            container.setBoxLibStockId(storeId);
            container.setStationId(station.getStationId());
            container.setContainerNo(containerNo);

            //一个站台只有一个订单，此料箱只绑定一次（利中项目）
            // 从当前站台的所有订单中查找此料箱中的商品，依次进行绑定扣除
            boolean isLxBinding = false;
            PickOrderDto pickOrder = station.getNeedOutboundPickOrder();
            for (int i = 0; i < pickOrder.getOrderList().size(); i++) {
                OrderDto order = pickOrder.getOrderList().get(i);
                boolean isDingDanBinding = this.bindOrder(pickOrder, container, order, station);
                if (isDingDanBinding) {
                    isLxBinding = true;
                }
            }

            if (!isLxBinding) {
                throw new Exception("料箱没有进行订单绑定");
            }

        } catch (Exception ex) {
            FileLogHelper.writeLog("LiaoXiangChuKuLogError", "料箱出库后生成数据失败:xkKuCunId" + storeId + ",containerNo:"
                    + containerNo + ",zhanTaiId:" + station.getStationId() + ",异常:" + ex.toString());
            throw new Exception(ex.toString());
        }
    }


    /**
     * 绑定料箱和订单
     *
     * @param pickOrder
     * @param container
     * @param order
     * @param station
     * @return
     * @throws Exception
     */
    private boolean bindOrder(PickOrderDto pickOrder, ContainerDto container, OrderDto order, StationDto station) throws Exception {
        boolean isBinding = false;
        while (true) {
            OrderMxDto orderMx = null;
            CargoBoxDto cargoBox = null;
            for (OrderMxDto orderMxTemp : order.getOrderMxList()) {
                if (orderMxTemp.getSpId() == container.getSpId()) {
                    if (!orderMxTemp.checkBindingFinish()) {
                        orderMx = orderMxTemp;
                        break;
                    }
                }
            }
            // 订单内没有需要当前料箱绑定的明细
            if (orderMx == null)
            {
                break;
            }


            // 未绑数=应播数-已播数-已绑数
            int leaveBindingCount = orderMx.getLeaveBindingCount();

            if (leaveBindingCount <= 0) {
                throw new Exception("明细已完全绑定");
            }

            boolean isBindingDingDan = this.bindingOrderMxContainer(pickOrder, orderMx, cargoBox, station);
            if (isBindingDingDan) {
                isBinding = true;
            }
        }

        return isBinding;
    }

    /**
     * 绑定订单明细和料箱
     *
     * @param pickOrder
     * @param orderMx
     * @param cargoBox
     * @param station
     * @return
     * @throws Exception
     */
    private boolean bindingOrderMxContainer(PickOrderDto pickOrder, OrderMxDto orderMx, CargoBoxDto cargoBox, StationDto station) throws Exception {
        int ddspLeaveCount = orderMx.getLeaveBindingCount();
        int hgLeaveCount = cargoBox.getBindingLeaveCount();

        if (ddspLeaveCount < 0) {
            throw new Exception(String.format("ddspLeaveCount小于0；值：%n", ddspLeaveCount));
        }

        if (hgLeaveCount < 0) {
            throw new Exception(String.format("hgLeaveCount小于0；值：%n", hgLeaveCount));
        }

        if (hgLeaveCount == 0) {
            return false;
        }
        if (ddspLeaveCount == 0) {
            return false;
        }

        int jxCount = ddspLeaveCount;
        if (hgLeaveCount < ddspLeaveCount) {
            jxCount = hgLeaveCount;
        }

        orderMx.addBindingCount(jxCount);
        cargoBox.getContainerSubOrderMxBindingMap().put(orderMx.getId(), jxCount);
        if (!pickOrder.getContainerList().contains(cargoBox.getContainer())) {
            pickOrder.getContainerList().add(cargoBox.getContainer());
        }

        // 保存料箱绑定数据
        this.saveLxBingDing(cargoBox.getContainerNo(), cargoBox.getCargoBoxNo(), orderMx.getOrderHzId(), orderMx.getId(),
                jxCount, station.getStationId(), station.getNeedOutboundPickOrder().getPickOrderId(),
                cargoBox.getContainer().getBoxLibStockId());

        return true;
    }

    /**
     * 保存订单绑定
     *
     * @param liaoXiangNo 料箱编号
     * @param huoGeNo     料箱货格编号
     * @param orderHzId   订单汇总id
     * @param orderMxId   订单明细ID
     * @param planNum     计划数量
     * @param stationId   站台id
     * @param jxdId       拣选单id
     * @param xkStoreId
     * @throws Exception
     */
    private void saveLxBingDing(String liaoXiangNo, String huoGeNo, int orderHzId, int orderMxId, int planNum, int stationId, int jxdId, int xkStoreId) throws Exception {
       /* List<ContainerBindingHz> containerBindingHzs = containerBindingHzMapper.findByMap(MapUtils.put("containerNo", liaoXiangNo).getMap(), ContainerBindingHz.class);
        ContainerSubBindingMx containerSubBindingMx = new ContainerSubBindingMx();
        OrderMxBingding orderMxBingding = new OrderMxBingding();
        if (containerBindingHzs.size() > 0) {
            containerSubBindingMx.setContainerNo(liaoXiangNo);
            containerSubBindingMx.setContainerSubNo(huoGeNo);
            containerSubBindingMx.setBindingNum(planNum);
            containerSubBindingMx.setCreateTime(PrologDateUtils.parseObject(new Date()));
            containerSubBindingMx.setIsFinish(0);
            containerSubBindingMx.setOrderHzId(orderHzId);
            containerSubBindingMx.setOrderMxId(orderMxId);
            containerSubBindingMxMapper.save(containerSubBindingMx);
            orderMxBingding.setOrderMxId(orderMxId);
            orderMxBingding.setContainerNo(liaoXiangNo);
            orderMxBingding.setContainerSubNo(huoGeNo);
            orderMxBingdingMapper.save(orderMxBingding);
        } else {
            ContainerBindingHz containerBindingHz = new ContainerBindingHz();
            containerBindingHz.setContainerNo(liaoXiangNo);
            containerBindingHz.setPickingOrderId(jxdId);
            containerBindingHz.setStationId(stationId);
            containerBindingHz.setXkStoreId(xkStoreId);
            containerBindingHzMapper.save(containerBindingHz);

            containerSubBindingMx.setContainerNo(liaoXiangNo);
            containerSubBindingMx.setContainerSubNo(huoGeNo);
            containerSubBindingMx.setBindingNum(planNum);
            containerSubBindingMx.setCreateTime(PrologDateUtils.parseObject(new Date()));
            containerSubBindingMx.setIsFinish(0);
            containerSubBindingMx.setOrderHzId(orderHzId);
            containerSubBindingMx.setOrderMxId(orderMxId);
            containerSubBindingMxMapper.save(containerSubBindingMx);
            orderMxBingding.setOrderMxId(orderMxId);
            orderMxBingding.setContainerNo(liaoXiangNo);
            orderMxBingding.setContainerSubNo(huoGeNo);
            orderMxBingdingMapper.save(orderMxBingding);
        }*/
        FileLogHelper.writeLog("saveLxBingDingLog", "生成料箱绑定汇总xkKuCunId:" + xkStoreId + ",liaoXiangNo:" + liaoXiangNo
                + ",orderHzId:" + orderHzId + "orderMxId:" + orderMxId + ",planNum：" + planNum);
    }
}
