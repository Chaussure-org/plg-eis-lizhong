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
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.location.StoreArea;
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
    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void BoxOutByOrder() throws Exception {

        List<LineBindingDetail> detailStatus = lineBindingDetailMapper.findByMap(MapUtils.put("detailStatus", OrderBill.ORDER_STATUS_START_OUT).getMap(), LineBindingDetail.class);
        if (!detailStatus.isEmpty()) {
            pathSchedulingService.containerMoveTask(detailStatus.get(0).getContainerNo(), "A100", null);
            return;
        }
        //1.要去往循环线区域的订单明细
        List<OutDetailDto> lineDetailList = orderDetailMapper.findLineDetail(StoreArea.L01);
        if (lineDetailList.isEmpty()) {
            return;
        }
        //优先出库 wms优先级高的  eis优先级为 高的
        List<OutDetailDto> wmsOutdetails = lineDetailList.stream().filter(x -> x.getWmsOrderPriority() == OrderBill.WMS_PRIORITY).collect(Collectors.toList());
        if (!wmsOutdetails.isEmpty()) {
            //有wms订单优先级,整体算出库的绑定明细
            List<OutContainerDto> outContainerDtoList = this.outByDetails(wmsOutdetails);
            this.saveLineBindingDetail(outContainerDtoList);
        } else {
            List<OutDetailDto> agvDetails = lineDetailList.stream().sorted(Comparator.comparing(OutDetailDto::getOrderPriority)).collect(Collectors.toList());
            List<Integer> orderIds = this.computeRepeat(agvDetails);
            List<OutDetailDto> details = agvDetails.stream().filter(x -> orderIds.contains(x.getOrderBillId())).collect(Collectors.toList());
            if (!details.isEmpty()) {
                List<OutContainerDto> outContainerDtoList = this.outByDetails(details);
                this.saveLineBindingDetail(outContainerDtoList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OutContainerDto> outByDetails(List<OutDetailDto> detailDtos) throws Exception {
        List<OutContainerDto> outContainerList = new ArrayList<OutContainerDto>();
        int wmsPriority = detailDtos.get(0).getWmsOrderPriority();
        // 商品分组
        Map<Integer, List<OutDetailDto>> goodsIdMap = detailDtos.stream().collect(Collectors.groupingBy(x -> x.getGoodsId()));

        for (Map.Entry<Integer, List<OutDetailDto>> map : goodsIdMap.entrySet()) {

            int sum = map.getValue().stream().mapToInt(x -> x.getPlanQty()).sum();

            //商品id，总数，算出所需要出的总箱子
            List<OutContainerDto> outContainersByGoods = this.outByGoodsId(map.getKey(), sum, wmsPriority);
            for (OutDetailDto outDetailDto : map.getValue()) {
                //循环每个商品下的明细集合 进行箱子分配 订单明细
                for (OutContainerDto outContainerDto : outContainersByGoods) {
                    //明细已经全部分配完成，则分配下个明细
                    if (outDetailDto.getPlanQty() == 0) {
                        break;
                    }
                    //如果箱子数量为0 则找下个箱子
                    if (outContainerDto.getQty() == 0) {
                        continue;
                    }

                    OutDetailDto temp = new OutDetailDto();
                    if (outContainerDto.getQty() >= outDetailDto.getPlanQty()) {
                        temp.setPlanQty(outDetailDto.getPlanQty());
                        outContainerDto.setQty(outContainerDto.getQty() - outDetailDto.getPlanQty());
                        outDetailDto.setPlanQty(0);
                    } else {
                        temp.setPlanQty(outContainerDto.getQty());
                        outDetailDto.setPlanQty(outDetailDto.getPlanQty() - outContainerDto.getQty());
                        outContainerDto.setQty(0);
                    }
                    //给此容器添加绑定的明细
                    temp.setDetailId(outDetailDto.getDetailId());
                    temp.setOrderBillId(outDetailDto.getOrderBillId());
                    temp.setOrderPriority(outDetailDto.getOrderPriority());
                    temp.setWmsOrderPriority(outDetailDto.getWmsOrderPriority());
                    outContainerDto.getDetailList().add(temp);
                }
            }

            outContainerList.addAll(outContainersByGoods);
        }
        return outContainerList;
    }

    @Override
    public synchronized List<OutContainerDto> outByGoodsId(int goodsId, int count, int wmsPriority) throws Exception {

        //1.优先出小车所在的层的库存 移位数量由低到高 2.出库任务数从低到高排序 3.按照入库任务数从低到高 4.离出库位置最近的位置
        List<OutContainerDto> outContainerDtoList = new ArrayList<>();
        //箱库的库存
        List<LayerGoodsCountDto> layerGoodsCounts = boxOutMapper.findLayerGoodsCount(goodsId);
        //找层的任务数出库任务数和入库任务数
        List<LayerTaskDto> layerTaskCounts = boxOutMapper.findLayerTaskCount();
        //输送线上绑定了订单 的  剩余库存
        List<LayerGoodsCountDto> agvGoodsCounts = boxOutMapper.findLineGoodsCount(goodsId);
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
        }
        boolean isContinue = true;
        int sumCount = 0;
//        //优先从agv库存找
        for (LayerGoodsCountDto goodsCountDto : agvGoodsCounts) {
            if (goodsCountDto.getQty() <= 0) {
                continue;
            }
            if (sumCount >= count) {
                isContinue = false;
                break;
            }
            OutContainerDto outContainer = this.getOutContainer(goodsCountDto, goodsId);
            outContainerDtoList.add(outContainer);
            sumCount += goodsCountDto.getQty();
        }
        if (isContinue) {
            //1.移位数量由低到高2.出库任务数从低到高排序 3.按照入库任务数从低到高
            layerGoodsCounts.stream().sorted(
                    Comparator.comparing(LayerGoodsCountDto::getInCount).reversed().
                            thenComparing(LayerGoodsCountDto::getOutCount).reversed().
                            thenComparing(LayerGoodsCountDto::getDeptNum));
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
        List<LineBindingDetail> list = new ArrayList<>();

        for (OutContainerDto containerDto : outList) {
            for (OutDetailDto detailDto : containerDto.getDetailList()) {
                LineBindingDetail lineBindingDetail = new LineBindingDetail();

                lineBindingDetail.setContainerNo(containerDto.getContainerNo());
                lineBindingDetail.setOrderBillId(detailDto.getOrderBillId());
                lineBindingDetail.setOrderMxId(detailDto.getDetailId());
                lineBindingDetail.setGoodsId(containerDto.getGoodsId());
                lineBindingDetail.setBindingNum(detailDto.getPlanQty());

                lineBindingDetail.setOrderPriority(detailDto.getOrderPriority());
                lineBindingDetail.setWmsOrderPriority(detailDto.getWmsOrderPriority());
                lineBindingDetail.setDetailStatus(OrderBill.ORDER_STATUS_START_OUT);
                lineBindingDetail.setUpdateTime(new Date());
                list.add(lineBindingDetail);
            }
        }
        lineBindingDetailMapper.saveBatch(list);
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
    public List<Integer> computeRepeat(List<OutDetailDto> lineDetailList) throws Exception {
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
        //orderMap.remove(orderBillId);
        for (Integer orderId : orderMap.keySet()) {
            float ratio = CompareStrSimUtil.getSimilarityRatio(stringFirst, orderMap.get(orderId), true);
            //list.add(new OrderSortDto(orderId, ratio,));
        }
        //订单重复度最高的排再最前面
        List<OrderSortDto> sortList = list.stream().sorted(Comparator.comparing(OrderSortDto::getRate).reversed()).collect(Collectors.toList());
        if (sortList.size() == 0) {
            return null;
        }
        if (sortList.size() > 10) {
            return sortList.subList(0, 10).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        } else {
            return sortList.subList(0, sortList.size()).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        }
    }
}
