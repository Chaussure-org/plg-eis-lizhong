package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
import com.prolog.eis.dto.lzenginee.boxoutdto.LayerTaskDto;
import com.prolog.eis.dto.lzenginee.boxoutdto.OrderSortDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.engin.dao.BoxOutMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.util.CompareStrSimUtil;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName:BoxOutEnginServiceImpl
 * Package:com.prolog.eis.engin.service.impl
 * Description:
 *
 * @date:2020/9/30 11:47
 * @author:SunPP
 */
@Service
public class BoxOutEnginServiceImpl implements BoxOutEnginService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private BoxOutMapper boxOutMapper;
    @Autowired
    private ISASService sasService;
    @Autowired
    private LineBindingDetailMapper lineBindingDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void BoxOutByOrder() throws Exception {
        /**
         * 1.托盘的出库与箱库的出库不影响
         * 2.优先出 全部从箱库出库的 订单
         */
        //1.要去往循环线区域的订单明细
        List<OutDetailDto> lineDetailList = orderDetailMapper.findAgvDetail("B");
        //优先出库 wms优先级高的  eis优先级为 高的
        List<OutDetailDto> lineDetails = lineDetailList.stream().sorted(Comparator.comparing(OutDetailDto::getWmsOrderPriority).thenComparing(OutDetailDto::getOrderPriority)).collect(Collectors.toList());

        //找到所有要去循环线的订单
        Criteria ctr = Criteria.forClass(OrderBill.class);
        Set<Integer> ids = lineDetailList.stream().map(OutDetailDto::getOrderBillId).collect(Collectors.toSet());
        ctr.setRestriction(Restrictions.in("orderBillId", ids.toArray()));
        List<OrderBill> orderBillList = orderBillMapper.findByCriteria(ctr);
        //判断订单的状态 10 准备出库的订单 如果有 return 如果没有
        List<OrderBill> matchOrders = orderBillList.stream().filter(x -> x.getOrderTaskState().equals(10)).collect(Collectors.toList());
        //判断循环线的最大料箱数量，判断是否要出库
        //todo
        List<Integer> orderIds;
        if (matchOrders.size() > 0) {
            //找到一个箱子，判断是否满足其他的明细
            //满足条件的订单明细
            List<Integer> idsTemp = matchOrders.stream().map(OrderBill::getId).collect(Collectors.toList());
            orderIds = idsTemp;
        } else {
            //计算 10 单重复度最高的 更新订单状态
            List<Integer> idList = computeRepeat(lineDetailList);
            Criteria criteria = Criteria.forClass(OrderBill.class);
            criteria.setRestriction(Restrictions.in("ids", idList.toArray()));
            orderBillMapper.updateMapByCriteria(MapUtils.put("orderTaskState", OrderBill.ORDER_STATUS_START_OUT).getMap(), criteria);
            orderIds = idList;
        }
        List<OutDetailDto> detailList = lineDetailList.stream().filter(x -> orderIds.contains(x.getOrderBillId())).collect(Collectors.toList());
        //每次出一个goodsId，也就是一个订单明细
        List<OutContainerDto> outContainerDtoList = this.outByDetails(detailList);
        if (outContainerDtoList.size() > 0) {
            this.saveLineBindingDetail(outContainerDtoList);
            //生成路径
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OutContainerDto> outByDetails(List<OutDetailDto> detailDtos) throws Exception {
        List<OutContainerDto> outContainerList = new ArrayList<>();
        int wmsPriority = detailDtos.get(0).getWmsOrderPriority();
        //按 商品分组
        Map<Integer, List<OutDetailDto>> goodsIdMap = detailDtos.stream().collect(Collectors.groupingBy(x -> x.getGoodsId()));
        for (Map.Entry<Integer, List<OutDetailDto>> map : goodsIdMap.entrySet()) {
            for (OutDetailDto outDetailDto : map.getValue()) {
                int sum = map.getValue().stream().mapToInt(x -> x.getPlanQty()).sum();
                List<OutContainerDto> outContainerDtoList = this.outByGoodsId(map.getKey(), sum, wmsPriority);
                for (OutContainerDto outContainerDto : outContainerDtoList) {
                    if (outContainerDto.getQty() == 0) {
                        break;
                    }

                    OutDetailDto orderDetail = new OutDetailDto();
                    if (outContainerDto.getQty() >= outDetailDto.getPlanQty()) {
                        outContainerDto.setQty(outContainerDto.getQty() - outDetailDto.getPlanQty());
                        outDetailDto.setPlanQty(0);
                        orderDetail.setPlanQty(outDetailDto.getPlanQty());
                    } else {
                        outContainerDto.setQty(0);
                        outDetailDto.setPlanQty(outDetailDto.getPlanQty() - outContainerDto.getQty());
                        orderDetail.setPlanQty(outContainerDto.getQty());
                    }
                    //给此容器添加绑定的明细
                    orderDetail.setDetailId(outDetailDto.getDetailId());
                    outContainerDto.getDetailList().add(orderDetail);
                }
                outContainerList.addAll(outContainerDtoList);
            }
        }
        return outContainerList;
    }

    @Override
    public synchronized List<OutContainerDto> outByGoodsId(int goodsId,int count,int wmsPriority) throws Exception {

        //1.优先出小车所在的层的库存 移位数量由低到高 2.出库任务数从低到高排序 3.按照入库任务数从低到高 4.离出库位置最近的位置
        List<OutContainerDto> outContainerDtoList = new ArrayList<>();
        //选择重复度最高的订单 中的任意一个明细进行出库,找符合商品id的箱子
        List<LayerGoodsCountDto> layerGoodsCounts = boxOutMapper.findLayerGoodsCount(goodsId);
        //找层的任务数出库任务数和入库任务数
        List<LayerTaskDto> layerTaskCounts = boxOutMapper.findLayerTaskCount();
        //小车所在的层
        List<CarInfoDTO> conformCars = this.getConformCars();
        if (conformCars.isEmpty()) {
            return null;
        }
        for (LayerTaskDto layerTaskDto : layerTaskCounts) {
            for (LayerGoodsCountDto layerGoodsCountDto : layerGoodsCounts) {
                if (layerTaskDto.getLayer() == layerGoodsCountDto.getLayer()) {
                    layerGoodsCountDto.setInCount(layerTaskDto.getInCount());
                    layerGoodsCountDto.setOutCount(layerTaskDto.getOutCount());
                }
            }
        }
        List<Integer> carLayers = conformCars.stream().map(CarInfoDTO::getLayer).collect(Collectors.toList());
        //小车所在的层的库存
        List<LayerGoodsCountDto> carLayerGoodCounts = layerGoodsCounts.stream().filter(x -> carLayers.contains(x.getLayer())).collect(Collectors.toList());
        if (carLayerGoodCounts.isEmpty()) {

            // TODO: 2020/10/10 跨层
        } else {
            //1.移位数量由低到高2.出库任务数从低到高排序 3.按照入库任务数从低到高
            layerGoodsCounts.stream().sorted(
                    Comparator.comparing(LayerGoodsCountDto::getDeptNum).
                            thenComparing(LayerGoodsCountDto::getOutCount).reversed().thenComparing(LayerGoodsCountDto::getInCount).reversed());
            int sumCount = 0;
            for (LayerGoodsCountDto goodsCountDto : layerGoodsCounts) {
                if (sumCount >= count) {
                    break;
                }
                OutContainerDto outContainer = this.getOutContainer(goodsCountDto, goodsId);
                outContainerDtoList.add(outContainer);
                sumCount += goodsCountDto.getQty();
            }
        }
        return outContainerDtoList;
    }


    private OutContainerDto getOutContainer(LayerGoodsCountDto goodsCountDto, int goodsId) {
        OutContainerDto outContainerDto = new OutContainerDto();
        outContainerDto.setContainerNo(goodsCountDto.getContainerNo());
        outContainerDto.setGoodsId(goodsId);
        outContainerDto.setStoreLocation(goodsCountDto.getStoreLocation());
        outContainerDto.setQty(goodsCountDto.getQty());
        return outContainerDto;
    }

    //更新line_binding_detail
    private void saveLineBindingDetail(List<OutContainerDto> outList) {
        for (OutContainerDto containerDto : outList) {

        }
    }

    private List<CarInfoDTO> getConformCars() throws Exception {
        List<CarInfoDTO> carInfos = sasService.getCarInfo();
        List<CarInfoDTO> cars = carInfos.stream().filter(x -> Arrays.asList(1, 2).contains(x.getStatus())).collect(Collectors.toList());
        if (cars.size() == 0) {
            return null;
        }
        return cars;
    }

    /**
     * 计算10单 重复度最高的
     * 返回订单id 集合
     */
    @Override
    public List<Integer> computeRepeat(List<OutDetailDto> lineDetailList) throws Exception{
        Map<Integer, List<OutDetailDto>> mapTemp = lineDetailList.stream().collect(Collectors.groupingBy(OutDetailDto::getOrderBillId));
        Map<Integer, StringBuffer> orderMap = new HashMap<>();
        for (Integer orderId : mapTemp.keySet()) {
            StringBuffer str = new StringBuffer();
            List<OutDetailDto> orderDetails = mapTemp.get(orderId).stream().sorted(Comparator.comparing(OutDetailDto::getGoodsId)).collect(Collectors.toList());
            for (OutDetailDto detailDto : orderDetails) {
                str = str.append(detailDto.getGoodsId());
            }
            orderMap.put(orderId, str);
        }
        List<OrderSortDto> list = new ArrayList<>();

        int orderBillId = lineDetailList.get(0).getOrderBillId();
        StringBuffer stringFirst = orderMap.get(orderBillId);
        orderMap.remove(orderBillId);
        for (Integer orderId : orderMap.keySet()) {
            float ratio = CompareStrSimUtil.getSimilarityRatio(stringFirst, orderMap.get(orderId), true);
            list.add(new OrderSortDto(orderId, ratio));
        }
        //订单重复度最高的排再最前面
        List<OrderSortDto> sortList = list.stream().sorted(Comparator.comparing(OrderSortDto::getRate).reversed()).collect(Collectors.toList());
        if (sortList.size() == 0) {
            return null;
        }
        if (sortList.size() > 10) {
            return sortList.subList(0, 9).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        } else {
            return sortList.subList(0, sortList.size()).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        }
    }
}
