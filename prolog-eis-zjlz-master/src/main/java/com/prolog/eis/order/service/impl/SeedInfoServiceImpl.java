package com.prolog.eis.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.prolog.eis.dto.page.PickingPrintDto;
import com.prolog.eis.dto.page.PickingPrintQueryDto;
import com.prolog.eis.model.order.SeedInfo;
import com.prolog.eis.order.dao.SeedInfoMapper;
import com.prolog.eis.order.service.ISeedInfoService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/13 17:24
 */
@Service
public class SeedInfoServiceImpl implements ISeedInfoService {
    @Autowired
    private SeedInfoMapper seedInfoMapper;
    @Override
    public void saveSeedInfo(String containerNo,String orderTrayNo,int orderBillId,int orderDetailId,int stationId,int num,int goodsId) {
        SeedInfo seedInfo = new SeedInfo();
        seedInfo.setContainerNo(containerNo);
        seedInfo.setNum(num);
        seedInfo.setOrderBillId(orderBillId);
        seedInfo.setOrderDetailId(orderDetailId);
        seedInfo.setOrderTrayNo(orderTrayNo);
        seedInfo.setStationId(stationId);
        seedInfo.setCreateTime(new Date());
        seedInfo.setGoodsId(goodsId);
        seedInfoMapper.save(seedInfo);
    }

    @Override
    public SeedInfo findSeedInfoByOrderDetail(int orderDetailId) {
        return seedInfoMapper.findSeedInfoByOrderDetail(orderDetailId);
    }

    @Override
    public List<SeedInfo> findSeedInfoByMap(Map map) {
        return seedInfoMapper.findByMap(map,SeedInfo.class);
    }

    @Override
    public Page<PickingPrintDto> getPrintPage(PickingPrintQueryDto printQueryDto) {
        PageHelper.startPage(printQueryDto.getPageNum(),printQueryDto.getPageSize());
        List<PickingPrintDto> seedInfo = seedInfoMapper.getSeedInfo(printQueryDto);
        Page<PickingPrintDto> page = PageUtils.getPage(seedInfo);

        return page;
    }
}
