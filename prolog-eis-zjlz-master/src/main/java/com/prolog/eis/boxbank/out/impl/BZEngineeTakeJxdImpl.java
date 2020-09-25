/*
package com.prolog.eis.boxbank.out.impl;

import com.prolog.eis.boxbank.out.BZEngineeTakeJxd;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dao.order.OrderHzMapper;
import com.prolog.eis.dao.pickorder.PickOrderMapper;
import com.prolog.eis.dto.enginee.*;
import com.prolog.eis.math.JXDTaker;
import com.prolog.eis.model.order.OrderHz;
import com.prolog.eis.orderpool.service.OrderPoolService;
import com.prolog.eis.pickstation.model.PickOrder;
import com.prolog.eis.util.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BZEngineeTakeJxdImpl implements BZEngineeTakeJxd {
    @Autowired
    private EisProperties eisProperties;
    @Autowired
    private OrderPoolService orderPoolService;
    @Autowired
    private PickOrderMapper pickOrderMapper;
    @Autowired
    private OrderHzMapper orderHzMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void checkZhanTaiJXD(XiangKuDto xiangKu, List<DingDanDto> dingDanPoolList) throws Exception {
        List<ZhanTaiDto> ztList = new ArrayList<ZhanTaiDto>();
        ztList.addAll(xiangKu.getZtList());// 复制集合

        // 优先为料箱数量少的站台索取拣选单
        ztList.sort((zt1, zt2) -> {
            return zt1.ComputeLiaoXiangCount() - zt2.ComputeLiaoXiangCount();
        });

        for (ZhanTaiDto zt : ztList) {
            // 如果站台的拣选单所需料箱已经全部到达线体，则为该站台索取新的拣选单
            if (zt.getIsLock() == 2 && zt.getIsClaim() == 1 && zt.CheckIsAllLXArrive()) {
                this.takeJXD(zt, xiangKu, dingDanPoolList);
            }
        }
    }

    */
/**
     * 为一个站台索取一个拣选单
     *
     * @param zt
     * @param xiangKu
     * @param dingDanPoolList
     * @throws Exception
     *//*

    private void takeJXD(ZhanTaiDto zt, XiangKuDto xiangKu, List<DingDanDto> dingDanPoolList) throws Exception {
        if (dingDanPoolList.size() == 0)//没有可以下发的订单
            return;

        List<DingDanDto> dingDanList = new ArrayList<DingDanDto>();
        dingDanList.addAll(dingDanPoolList);

        Set<Integer> otherZtSpIdSet = this.getOtherZtSpIdSet(zt, xiangKu);

        JXDTaker jxdTaker = new JXDTaker(zt.getZhanTaiId(), dingDanList, otherZtSpIdSet, eisProperties.getMaxJxdOrderCount(), eisProperties.getMaxSPCount());
        JianXuanDanDto jxd = jxdTaker.Compute();
        if (jxd == null)
            return;
        if (jxd.getDdList() == null || jxd.getDdList().size() == 0)
            throw new Exception("拣选单没有生成订单!");

        // 拣选单下所有订单都被拦截
        if (jxd.getDdList() == null || jxd.getDdList().size() == 0)
            return;

        jxd.setZhanTaiId(zt.getZhanTaiId());
        zt.getJxdList().add(jxd);

        //为站台生成拣选单,并生成出库任务
        this.updateBatchPicking(jxd);
        this.removeSelectedDingDan(jxd, dingDanPoolList);

        zt.setNeedChuKuJXD(jxd);
    }

    private void removeSelectedDingDan(JianXuanDanDto jxd, List<DingDanDto> dingDanPoolList) throws Exception {
        // 从订单池移除掉已经下发的订单
        List<Integer> orderIdList = new ArrayList<Integer>();
        for (DingDanDto dd : jxd.getDdList()) {
            orderIdList.add(dd.getId());
        }
        orderPoolService.delOrderList(orderIdList);// 从订单池移除订单

        Iterator<DingDanDto> iterator = dingDanPoolList.iterator();
        while (iterator.hasNext()) {
            DingDanDto next = iterator.next();
            if (orderIdList.contains(next.getId())) {
                iterator.remove();
            }
        }
    }

    private void updateBatchPicking(JianXuanDanDto jxd) throws Exception {
        List<DingDanDto> ddList = jxd.getDdList();
        if (ddList.size() == 0) {
            throw new Exception("没有订单能够绑定拣选单！！");
        }
        PickOrder pickOrder = new PickOrder();
        pickOrder.setStationId(jxd.getZhanTaiId());
        pickOrder.setIsPicking(2);
        pickOrder.setGmtCreateTime(new Date());
        pickOrder.setIsCkComplete(2);
        pickOrder.setIsAllArrive(0);
        pickOrderMapper.save(pickOrder);

        int pickingId = pickOrder.getId();
        List<String> ids = ListHelper.select(ddList, p -> p.getId() + "");
        String idStr = String.join(",", ids);
        List<OrderHz> orderHzs = orderHzMapper.findOrderHzByIds(idStr);
        if (orderHzs.size() > 0) {
            throw new Exception("该订单已经有拣选单！" + idStr);
        }

        orderHzMapper.updateBatchPicking(idStr, jxd.getZhanTaiId(), pickingId);
        jxd.setJxdId(pickingId);
    }

    */
/**
     * 获取一个站台的其他站台的没有完成的商品品种字典,站台间品种最大差异
     *
     * @param zt
     * @param xiangKu
     * @return
     *//*

    private Set<Integer> getOtherZtSpIdSet(ZhanTaiDto zt, XiangKuDto xiangKu) {
        Set<Integer> otherZtSpIdSet = new HashSet<Integer>();

        for (ZhanTaiDto ztTemp : xiangKu.getZtList()) {
            if (ztTemp.getZhanTaiId() != zt.getZhanTaiId()) {
                for (JianXuanDanDto jxd : ztTemp.getJxdList()) {
                    for (DingDanDto dd : jxd.getDdList()) {
                        for (DingDanMxDto ddmx : dd.getDingDanMxList()) {
                            // 没有播种完成
                            if (ddmx.getBoZhongCount() < ddmx.getSpCount()) {
                                otherZtSpIdSet.add(ddmx.getSpId());
                            }
                        }
                    }
                }
            }
        }

        return otherZtSpIdSet;
    }
}
*/
