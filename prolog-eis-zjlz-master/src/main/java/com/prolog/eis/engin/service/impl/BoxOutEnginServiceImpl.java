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
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.sas.service.ISasService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.util.CompareStrSimUtil;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @date:2020/9/30 11:47
 * @author:SunPP
 */
@Service
public class BoxOutEnginServiceImpl implements BoxOutEnginService {
    private final Logger logger = LoggerFactory.getLogger(TrayOutEnginServiceImpl.class);
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private BoxOutMapper boxOutMapper;
    @Autowired
    private ISasService sasService;
    @Autowired
    private LineBindingDetailMapper lineBindingDetailMapper;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private ContainerStoreMapper containerStoreMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void BoxOutByOrder() throws Exception {

        List<LineBindingDetail> detailStatus = lineBindingDetailMapper.findLineContainerTopath();
        if (!detailStatus.isEmpty()) {
            pathSchedulingService.containerMoveTask(detailStatus.get(0).getContainerNo(), "WCS081", "LXJZ01");
            lineBindingDetailMapper.updateLineStatus(detailStatus.get(0).getContainerNo(),OrderBill.ORDER_STATUS_OUTING);
            logger.info(detailStatus.get(0).getContainerNo()+"生成去往输送线的路径======================");
            return;
        }
        //1.要去往循环线区域的订单明细
        List<OutDetailDto> lineDetailList = orderDetailMapper.findLineDetail(StoreArea.WCS081);
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
                if (outContainerDtoList.size() > 0) {
                    this.saveLineBindingDetail(outContainerDtoList);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OutContainerDto> outByDetails(List<OutDetailDto> detailDtos) throws Exception {
        List<OutContainerDto> outContainerList = new ArrayList<OutContainerDto>();
        // 商品分组
        Map<Integer, List<OutDetailDto>> goodsIdMap = detailDtos.stream().collect(Collectors.groupingBy(x -> x.getGoodsId()));
        for (Map.Entry<Integer, List<OutDetailDto>> map : goodsIdMap.entrySet()) {
            int sum = map.getValue().stream().mapToInt(x -> x.getPlanQty()).sum();
            //商品id，总数，算出所需要出的总箱子
            List<OutContainerDto> outContainersByGoods = this.outByGoodsId(map.getKey(), sum);
            if (outContainersByGoods.size() == 0) {
                return outContainerList;
            }
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

    /**
     * @param goodsId
     * @param count
     * @return
     * @throws Exception
     */
    @Override
    public synchronized List<OutContainerDto> outByGoodsId(int goodsId, int count) throws Exception {

        //1.优先出小车所在的层的库存 移位数量由低到高 2.出库任务数从低到高排序 3.按照入库任务数从低到高 4.离出库位置最近的位置
        List<OutContainerDto> outContainerDtoList = new ArrayList<>();
        //箱库的库存
        List<LayerGoodsCountDto> layerGoodsCounts = boxOutMapper.findLayerGoodsCount(goodsId);
        //找层的任务数出库任务数和入库任务数
        List<LayerTaskDto> layerTaskCounts = boxOutMapper.findLayerTaskCount();
        //输送线上绑定了订单 的  剩余库存
        List<LayerGoodsCountDto> lineGoodsCounts = boxOutMapper.findLineGoodsCount(goodsId);

        //小车所在的层
        List<CarInfoDTO> conformCars = this.getConformCars();
        if (conformCars.size() == 0) {
            return outContainerDtoList;
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
        boolean isContinue = true;
        int sumCount = 0;
        //优先从 line 库存找
        for (LayerGoodsCountDto goodsCountDto : lineGoodsCounts) {
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
            Collections.sort(layerGoodsCounts, new Comparator<LayerGoodsCountDto>() {
                @Override
                public int compare(LayerGoodsCountDto o1, LayerGoodsCountDto o2) {
                    if (carLayers.contains(o1.getLayer()) && !carLayers.contains(o2.getLayer())) {
                        return -1;
                    }
                    if (!carLayers.contains(o1.getLayer()) && carLayers.contains(o2.getLayer())) {
                        return 1;
                    }
                    //1.小车所在的层
                    if (carLayers.contains(o1.getLayer()) && carLayers.contains(o2.getLayer())) {
                        //2.移位数量由低到高
                        if (o1.getDeptNum() == o2.getDeptNum()) {
                            //.3.出库任务数从低到高排序
                            if (o1.getOutCount() == o2.getOutCount()) {
                                //4.入库库任务数从低到高排序
                                if (o1.getInCount() == o2.getInCount()) {
                                    //可以加距离
                                    return 0;
                                } else {
                                    return o1.getInCount() < o2.getInCount() ? -1 : 1;
                                }
                            } else {
                                return o1.getOutCount() < o2.getOutCount() ? -1 : 1;
                            }
                        } else {
                            return o1.getDeptNum() < o2.getDeptNum() ? -1 : 1;
                        }
                    }
                    //两者都不是小车所在的层
                    return 0;
                }
            });
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
    @Transactional(rollbackFor = Exception.class)
    public void saveLineBindingDetail(List<OutContainerDto> outList) {
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
        List<String> containers = outList.stream().map(OutContainerDto::getContainerNo).collect(Collectors.toList());
        String strs = String.join(",", containers);
        lineBindingDetailMapper.saveBatch(list);
        //update 库存业务属性 和 库存状态
        containerStoreMapper.updateContainerStatus(strs, ContainerStore.TASK_TYPE_OUTBOUND,ContainerStore.TASK_TYPE_OUTBOUND);
        //更新订单状态
        List<Integer> ids = list.stream().distinct().map(x -> x.getOrderBillId()).collect(Collectors.toList());
        String idsStr = StringUtils.join(ids, ',');
        orderBillMapper.updateOrderStatus(OrderBill.ORDER_STATUS_START_OUT,idsStr);
    }

    private List<CarInfoDTO> getConformCars() throws Exception {
        List<CarInfoDTO> carInfos = sasService.getCarInfo();
        List<CarInfoDTO> cars = carInfos.stream().filter(x -> Arrays.asList(1, 2).contains(x.getStatus())).collect(Collectors.toList());
        return cars;
    }

    /**
     * 计算10单 重复度最高的
     * 返回订单id 集合
     */
    @Override
    public synchronized List<Integer> computeRepeat(List<OutDetailDto> lineDetailList) throws Exception {
        Map<Integer, List<OutDetailDto>> mapTemp = lineDetailList.stream().collect(Collectors.groupingBy(OutDetailDto::getOrderBillId));
        List<OrderSortDto> sortList = new ArrayList<>();

        for (Integer orderId : mapTemp.keySet()) {
            List<OutDetailDto> orderDetails = mapTemp.get(orderId).stream().sorted(Comparator.comparing(OutDetailDto::getGoodsId)).collect(Collectors.toList());
            OrderSortDto SortDto = new OrderSortDto();
            SortDto.setOrderBillId(orderId);
            List<Integer> ids = orderDetails.stream().map(OutDetailDto::getDetailId).collect(Collectors.toList());
            SortDto.setIds(ids);
            sortList.add(SortDto);
        }

        //取 商品品种数最大的订单
        Collections.sort(sortList, new Comparator<OrderSortDto>() {
            @Override
            public int compare(OrderSortDto o1, OrderSortDto o2) {
                if (o2.getIds().size() - o1.getIds().size() == 0) {
                    return 0;
                } else {
                    return o2.getIds().size() - o1.getIds().size() > 0 ? 1 : -1;
                }
            }
        });
        int[] s1 = sortList.get(0).getIds().stream().mapToInt(Integer::valueOf).toArray();

        for (OrderSortDto dto : sortList) {
            int[] s2 = dto.getIds().stream().mapToInt(Integer::valueOf).toArray();
            int sameCount = 0;
            for (int i = 0; i < s1.length; i++) {
                for (int j = 0; j < s2.length; j++) {
                    if (s1[i] == s2[j]) {
                        sameCount++;
                    }
                }
            }
            float ratio = CompareStrSimUtil.getSimilarityRatio(s1, s2, true);
            dto.setLevenCount(ratio);
            dto.setSameCount(sameCount);
        }
        //商品品种相同数最多，然后是不同数最少
        List<OrderSortDto> list = sortList.stream().sorted(Comparator.comparing(OrderSortDto::getSameCount, Comparator.reverseOrder()).
                thenComparing(OrderSortDto::getLevenCount)).collect(Collectors.toList());
        if (list.size() > 10) {
            return sortList.subList(0, 10).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        } else {
            return sortList.subList(0, sortList.size()).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        }
    }
}
