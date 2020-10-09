package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.boxoutdto.OrderSortDto;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.util.CompareStrSimUtil;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.swing.UIManager.getInt;

/**
 * ClassName:BoxOutEnginServiceImpl
 * Package:com.prolog.eis.engin.service.impl
 * Description:
 *
 * @date:2020/9/30 11:47
 * @author:SunPP
 */
public class BoxOutEnginServiceImpl implements BoxOutEnginService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderBillMapper orderBillMapper;

    @Override
    public void BoxOutByOrder() throws Exception {
        //1.要去往循环线区域的订单明细
        List<OutDetailDto> lineDetailList = orderDetailMapper.findAgvDetail("B");
        //找到所有要去循环线的订单
        Criteria ctr = Criteria.forClass(OrderBill.class);
        Set<Integer> ids = lineDetailList.stream().map(OutDetailDto::getOrderBillId).collect(Collectors.toSet());
        ctr.setRestriction(Restrictions.in("orderBillId", ids.toArray()));
        List<OrderBill> orderBillList = orderBillMapper.findByCriteria(ctr);
        //判断订单的状态 10 储备出库的订单 如果有 return 如果没有
        List<OrderBill> matchOrders = orderBillList.stream().filter(x -> x.getOrderTaskState().equals(10)).collect(Collectors.toList());
        //判断循环线的最大料箱数量，判断是否要出库
        //todo
        if (matchOrders.size() > 0) {
            //找到一个箱子，判断是否满足其他的明细
            //满足条件的订单明细
            List<Integer> orderIds = matchOrders.stream().map(OrderBill::getId).collect(Collectors.toList());
            List<OutDetailDto> detailList = lineDetailList.stream().filter(x -> orderIds.contains(x.getOrderBillId())).collect(Collectors.toList());
            List<OutContainerDto> outContainerDtoList = this.outByGoodsId(detailList);
            if (outContainerDtoList.size() > 0) {
                this.saveLineBindingDetail(outContainerDtoList, detailList.get(0));
            }
        } else {
            //计算 10 单重复度最高的 生成状态
        }


    }

    @Override
    public List<OutContainerDto> outByGoodsId(List<OutDetailDto> outDetailDtos) throws Exception {
        //找符合商品id的箱子
        //找层的任务数出库任务数和入库任务数
        //小车所在的层
        //1.优先出小车所在的层的库存 2.出库任务数从低到高排序 3.按照入库任务数从低到高 4.离出库位置最近的位置
        //2.找到箱子判断是否符合其他订单的明细
        List<OutContainerDto> outContainerDtoList = new ArrayList<>();

        return outContainerDtoList;
    }

    private void saveLineBindingDetail(List<OutContainerDto> outList, OutDetailDto detailDto) {
        for (OutContainerDto containerDto : outList) {
            LineBindingDetail lineBindingDetail = new LineBindingDetail();
            lineBindingDetail.setOrderMxId(detailDto.getDetailId());
            lineBindingDetail.setGoodsId(containerDto.getGoodsId());
            lineBindingDetail.setContainerNo(containerDto.getContainerNo());
            lineBindingDetail.setUpdateTime(new Date());
        }
    }


    /**
     * 计算10单 重复度最高的
     * 返回订单id 集合
     */
    private List<Integer> computeRepeat(List<OutDetailDto> lineDetailList) {
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
        List<OrderSortDto> list=new ArrayList<>();

        int orderBillId = lineDetailList.get(0).getOrderBillId();
        StringBuffer stringFirst = orderMap.get(orderBillId);
        orderMap.remove(orderBillId);
        for (Integer orderId : orderMap.keySet()) {
            float ratio = CompareStrSimUtil.getSimilarityRatio(stringFirst, orderMap.get(orderId), true);
            list.add(new OrderSortDto(orderId,ratio));
        }
        //订单重复度最高的排再最前面
        List<OrderSortDto> sortList = list.stream().sorted(Comparator.comparing(OrderSortDto::getRate).reversed()).collect(Collectors.toList());
        if (sortList.size()==0){
            return null;
        }
        if (sortList.size()>10){
            return sortList.subList(0,9).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        }else {
            return sortList.subList(0,sortList.size()).stream().map(OrderSortDto::getOrderBillId).collect(Collectors.toList());
        }
    }
}
