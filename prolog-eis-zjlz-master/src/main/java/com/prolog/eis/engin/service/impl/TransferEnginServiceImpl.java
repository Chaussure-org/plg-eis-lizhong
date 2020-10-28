package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.engin.service.TransferEnginService;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private BoxOutEnginService boxOutEnginService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public void init() throws Exception {
        //1.订单查询 移库类型 2.每次出库一个订单明细
        List<OutDetailDto> transfers = orderDetailMapper.findTransfer();
        //立体库移库 集合
        List<OutDetailDto> LTKs = transfers.stream().filter(x -> x.getBranchType().equals(OrderBill.BRANCH_TYPE_LTK)).collect(Collectors.toList());
        List<OutDetailDto> XSKs = transfers.stream().filter(x -> x.getBranchType().equals(OrderBill.BRANCH_TYPE_XSK)).collect(Collectors.toList());

    }
}
