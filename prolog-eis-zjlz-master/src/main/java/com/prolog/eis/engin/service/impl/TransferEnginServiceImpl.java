package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.engin.service.TransferEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.framework.utils.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:移库作业调度实现
 * @return
 * @date:2020/10/27 11:08
 */
@Service
public class TransferEnginServiceImpl implements TransferEnginService {

    private static final Logger log = LoggerFactory.getLogger(TransferEnginServiceImpl.class);
    @Autowired
    private BoxOutEnginService boxOutEnginService;
    @Autowired
    private IOrderBillService iOrderBillService;
    @Autowired
    private IOrderDetailService iOrderDetailService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private TrayOutEnginService trayOutEnginService;
    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Override
    public void init() throws Exception {
        List<OutDetailDto> transfers = orderDetailMapper.findTransfer();
        Optional<OutDetailDto> transferFirst = transfers.stream().filter(x -> StringUtils.isBlank(x.getBranchType())).findFirst();
        Optional<OutDetailDto> first = transfers.stream().filter(x -> !StringUtils.isBlank(x.getBranchType())).findFirst();
        //如果所有的订单中既有播种任务又有移库任务
        if (transferFirst.isPresent() && first.isPresent()) {
            log.info("出库任务中 存在播种任务 和  移库任务，无法兼容两种作业模式");
            return;
        }

        List<OutDetailDto> LTKs = transfers.stream().filter(x -> x.getBranchType().equals(OrderBill.BRANCH_TYPE_LTK)).collect(Collectors.toList());
        List<OutDetailDto> XSKs = transfers.stream().filter(x -> x.getBranchType().equals(OrderBill.BRANCH_TYPE_XSK)).collect(Collectors.toList());
        if (XSKs.size() > 0) {
            List<OutContainerDto> outContainerDtoList = boxOutEnginService.outByGoodsId(XSKs.get(0).getGoodsId(), XSKs.get(0).getPlanQty());
            if (outContainerDtoList.size() > 0) {
                for (OutContainerDto outContainerDto : outContainerDtoList) {
                    pathSchedulingService.containerMoveTask(outContainerDto.getContainerNo(), "WCS081", "LXJZ01");
                }
            }
            //将订单明细转历史

        }
        if (LTKs.size()>0){
            List<OutContainerDto> outContainerDtoList = trayOutEnginService.outByGoodsId(LTKs.get(0).getGoodsId(), LTKs.get(0).getPlanQty());
            if (outContainerDtoList.size() > 0) {
                for (OutContainerDto outContainerDto : outContainerDtoList) {
                    pathSchedulingService.containerMoveTask(outContainerDto.getContainerNo(), "RCS01", null);
                }
            }
        }

    }
}
