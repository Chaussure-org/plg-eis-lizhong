package com.prolog.eis.bz.service.impl;

import com.prolog.eis.bz.service.IStationBZService;
import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.order.service.ISeedInfoService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/9/28 12:17
 */

@Service
public class StationBZServiceImpl implements IStationBZService {
    @Autowired
    private IContainerBindingDetailService containerBindingDetailService;
    @Autowired
    private IStationService stationService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private ISeedInfoService seedInfoService;

    /**
     * 2、校验托盘或料箱是否在拣选站
     * 3、校验托盘或料箱是否是该站台的拣选任务(否剔除)
     * 4、开始播种作业
     * 5、订单托满换托盘
     * 6、订单结束站台置空
     *
     * @param stationId   站台id
     * @param containerNo 容器编号
     * @param orderBoxNo  订单框编号
     */
    @Override
    public BCPPcikingDTO startBZPicking(int stationId, String containerNo, String orderBoxNo) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }
        if (StringUtils.isBlank(orderBoxNo)) {
            throw new RuntimeException("订单拖编号不能为空");
        }

        Station station = stationService.findById(stationId);
        if (station == null) {
            throw new RuntimeException(stationId + "站台不存在");
        }
        //todo:校验托盘或料箱是否在拣选站
        List<ContainerBindingDetail> containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo).getMap());
        if (containerBinDings.size() == 0) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }
        ContainerBindingDetail bindingDetail = containerBinDings.get(0);
        BCPPcikingDTO picking = new BCPPcikingDTO();
        picking.setOrderId(bindingDetail.getOrderBillId());
        picking.setPickNum(bindingDetail.getSeedNum());
        BCPGoodsInfoDTO bcpGoodsDTO = orderDetailService.findPickingGoods(bindingDetail.getOrderDetailId()).get(0);
        picking.setGoodsname(bcpGoodsDTO.getGoodsname());
        picking.setGraphNo(bcpGoodsDTO.getGraphNo());
        picking.setGoodsNo(bcpGoodsDTO.getGoodsNo());
        picking.setOrderNo(bcpGoodsDTO.getOrderNo());
        //未拣选订单明细条目
        int count = orderDetailService.notPickingCount(bindingDetail.getOrderBillId());
        picking.setSurplusOrderDetailCount(count);
        return picking;

    }

    /**
     * 播种确认
     * 1、库存扣减 容器放行  单挑明细回告wms
     * 2、订单完成 明细转历史、订单拖放行
     *
     * @param stationId
     * @param containerNo
     * @param orderBoxNo
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pickingConfirm(int stationId, String containerNo, String orderBoxNo) throws Exception {
        if (StringUtils.isBlank(orderBoxNo)) {
            throw new RuntimeException("订单拖编号不能为空");
        }
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }
        Station station = stationService.findById(stationId);
        if (station == null) {
            throw new RuntimeException(stationId + "站台不存在");
        }
        ContainerBindingDetail containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo).getMap()).get(0);
        if (containerBinDings == null) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }
        OrderDetail orderDetail = orderDetailService.findOrderDetailById(containerBinDings.getOrderDetailId());
        if (orderDetail == null){
            throw new RuntimeException("绑定料箱【"+ containerNo+"】无订单明细");
        }
        Integer orderBillId = containerBinDings.getOrderBillId();
        orderDetail.setHasPickQty(orderDetail.getHasPickQty()+containerBinDings.getSeedNum());
        orderDetail.setCompleteQty(orderDetail.getCompleteQty()+containerBinDings.getSeedNum());
        orderDetail.setUpdateTime(new Date());
        orderDetailService.updateOrderDetail(orderDetail);
        //扣减库存
        containerStoreService.updateContainerStoreNum(containerBinDings.getSeedNum(),containerNo);
        //todo:   回告wms
        //订单播种完成后续操作  明细转历史、订单拖放行、回告wms
        //播种记录保存
        seedInfoService.saveSeedInfo(containerNo,orderBoxNo,orderBillId,containerBinDings.getOrderDetailId(),stationId,containerBinDings.getSeedNum());
        boolean flag = orderDetailService.orderPickingFinish(orderBillId);
        if (flag){
            //明细转历史
            orderBillService.orderBillToHistory(orderBillId);
            //todo：订单拖放行 贴标区或非贴标区

        }
        //todo：任务拖放行

    }

    @Override
    public boolean checkContainerToStation(String containerNo, int stationId) {
        return false;
    }

    @Override
    public boolean checkContainerExist(String containerNo, int stationId) {
        return false;
    }

    @Override
    public void orderToHistory(int orderBillId) throws Exception {

    }

    @Override
    public void containerNoLeave(String containerNo) throws Exception {
        /**
         * 1、上层输送线放行或下层订单箱放行
         */
    }

    @Override
    public void orderTrayLeave(String orderTrayNo,int stationId,int orderBillId) throws Exception {
        //订单拖是否有贴标商品，有贴标区
        List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByMap(MapUtils.put("orderBillId", orderBillId).getMap());
    }


}
