package com.prolog.eis.boxbank.out.impl;

import com.prolog.eis.boxbank.out.BZEnginee;
import com.prolog.eis.dao.enginee.BZEngineInitMapper;
import com.prolog.eis.dao.enginee.EngineOutboundMapper;
import com.prolog.eis.dto.enginee.*;
import com.prolog.eis.store.dao.OContainerStoreMapper;
import com.prolog.eis.util.FileLogHelper;
import com.prolog.eis.util.ListHelper;
import com.prolog.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class BZEngineeImpl implements BZEnginee {
    private final Logger logger = LoggerFactory.getLogger(BZEngineeImpl.class);

    @Autowired
    private OContainerStoreMapper containerStoreMapper;
    @Autowired
    private BZEngineInitMapper bzEngineInitMapper;

    @Autowired
    private EngineOutboundMapper engineOutboundMapper;

    @Autowired
    private BZEngineeChku bzEngineeChku;

//    /**
//     * 获取可用商品库存
//     * @return
//     * @throws Exception
//     */
//    public Map<Integer, Integer> getSpStockMap() throws Exception {
//        // 查询已绑定拣选单的订单还需占用的商品库存
//        List<KuCunTotalDto> findAllUsedKuCun = engineGetInitMapper.findAllUsedKuCun();
//        // 查询已上架状态并且无锁定的箱库库存
//        List<KuCunTotalDto> findAllXkKuCun = engineGetInitMapper.findAllXkKuCun();
//        // 查询在途商品库存
//        List<KuCunTotalDto> findAllztKuCun = engineGetInitMapper.findAllztKuCun();
//        // 查询商品库存
//        Map<Integer, Integer> spStockMap = getMap(findAllUsedKuCun, findAllXkKuCun, findAllztKuCun);
//
//        return spStockMap;
//    }

    /**
     * 初始化箱库数据
     *
     * @return
     * @throws Exception
     */
    @Override
    public BoxLibDto init() throws Exception {
        BoxLibDto boxLibDto = new BoxLibDto();
        // 当前播种作业站台集合
        List<StationDto> stations = bzEngineInitMapper.findAllStation();
        //站台出库料箱数量
        Map<Integer, Integer> ztCountMap = bzEngineInitMapper.findChuKuLxCount().stream().
                collect(Collectors.toMap(StationContainerNo::getZhanTaiId, StationContainerNo::getCount));
        stations.stream().forEach(x -> {
            if (ztCountMap.containsKey(x)) {
                x.setChuKuLxCount(ztCountMap.get(x));
                x.setPickOrderList(findPickOrderList());
            }
        });
        boxLibDto.setStations(stations);
        boxLibDto.setSpStockMap(findSpStockMap());
        System.out.println(JsonUtils.toString(boxLibDto));
        return boxLibDto;

    }

    /**
     * sunpp
     * 查询商品库存数据 goodsId,goodsNum
     *
     * @return
     */
    private Map<Integer, Integer> findSpStockMap() {
      /*  List<ContainerStore> containerStoreList = containerStoreMapper.findByMap(new HashMap<>(), ContainerStore.class);
        Map<Integer, Integer> containerGoodsNumMap = containerStoreList.stream().collect(Collectors.toMap(ContainerStore::getGoodsId, ContainerStore::getQty, (v1, v2) -> {
            return v1 + v2;
        }));
        return containerGoodsNumMap;*/
        return null;
    }

    /**
     * sunpp
     * 查找 拣选单集合
     *
     * @return
     */
    private List<PickOrderDto> findPickOrderList() {
        // 查询拣选单  没有订单集合  和 料箱集合
        List<PickOrderDto> pickOrders = bzEngineInitMapper.findAllJianXuanDan();
        // 查询料箱未全部到位的拣选单下的所有订单
        Map<Integer, List<OrderDto>> orderMap = bzEngineInitMapper.findAllDingDan().stream().collect(Collectors.groupingBy(OrderDto::getJxdId));
        //查询所有的料箱
        List<ContainerDto> allLxList = bzEngineInitMapper.findAllLx();
        //查询料箱绑定明细数量
        List<LxOrderMxCountDto> lxOrderMxCountDtoList = bzEngineInitMapper.findLxOrderMxCount();

        for (ContainerDto containerDto : allLxList) {
            for (LxOrderMxCountDto lxOrderMxCountDto : lxOrderMxCountDtoList) {
                if (lxOrderMxCountDto.getContainerNo().equals(containerDto.getContainerNo())) {
                    containerDto.getContainerOrderBindingMap().put(lxOrderMxCountDto.getOrderDetailId(), lxOrderMxCountDto.getOrderDetailQty());
                }
            }
        }
        Map<Integer, List<ContainerDto>> lxMap = allLxList.stream().collect(Collectors.groupingBy(ContainerDto::getPickOrderId));
        for (PickOrderDto pickOrderDto : pickOrders) {
            if (orderMap.containsKey(pickOrderDto.getPickOrderId())) {
                pickOrderDto.setOrderList(orderMap.get(pickOrderDto.getPickOrderId()));
            }
            if (lxMap.containsKey(pickOrderDto.getPickOrderId())) {
                pickOrderDto.setContainerList(lxMap.get(pickOrderDto.getPickOrderId()));
            }
        }

        return pickOrders;
    }

    /**
     * 箱库的出库方法
     * @param spId
     * @param stationDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outbound(int spId, StationDto stationDto) throws Exception {
        /**
         * 2020.4.20确认 step1.计算每层的出库作业数(找任务数少的) step2.计算每层的入库作业数(找任务数少的)
         * step3.计算商品在每层非锁定的料箱数量(优先找数量多的) step4.排列优先级 step5.选择总任务数小于小车数量*2(找层)
         * step6.找出离提升机(x、y)最近的非锁定的该商品料箱（用内存计算） step7.发送出库任务，路径规划
         */
        // 计算每层的总任务数
        List<CengLxTaskDto> totalTasks = engineOutboundMapper.findTotalTask();
        // 计算每层的出库作业数
        List<CengLxTaskDto> ckCengLxTaskDtos = engineOutboundMapper.findCkCengLxTaskDtos();
        // 计算每层的入库作业数
        List<CengLxTaskDto> rkCengLxTaskDtos = engineOutboundMapper.findRkCengLxTaskDtos();

        // 计算商品在每层非锁定的料箱数量
        List<AllHuoWeiDto> allHuoWeiDtos = engineOutboundMapper.findAllHuoWeiDto(spId);
        // 拼装集合
        Map<Integer, List<CengLxTaskDto>> ckCengLxTaskDtosByCeng = ListHelper.buildGroupDictionary(ckCengLxTaskDtos,
                p -> p.getCeng());
        Map<Integer, List<CengLxTaskDto>> rkCengLxTaskDtosByCeng = ListHelper.buildGroupDictionary(rkCengLxTaskDtos,
                p -> p.getCeng());
        Map<Integer, List<CengLxTaskDto>> totalTasksByCeng = ListHelper.buildGroupDictionary(totalTasks,
                p -> p.getCeng());

        for (AllHuoWeiDto allHuoWeiDto : allHuoWeiDtos) {
            List<CengLxTaskDto> cengCkLxTaskDtos = ckCengLxTaskDtosByCeng.get(allHuoWeiDto.getCeng());
            List<CengLxTaskDto> cengRkLxTaskDtos = rkCengLxTaskDtosByCeng.get(allHuoWeiDto.getCeng());
            List<CengLxTaskDto> cengTotalLxTaskDtos = totalTasksByCeng.get(allHuoWeiDto.getCeng());

            if (cengCkLxTaskDtos != null && cengCkLxTaskDtos.size() > 0) {
                allHuoWeiDto.setCkTaskCount(cengCkLxTaskDtos.get(0).getTaskCount());
            }

            if (cengRkLxTaskDtos != null && cengRkLxTaskDtos.size() > 0) {
                allHuoWeiDto.setRkTaskCount(cengRkLxTaskDtos.get(0).getTaskCount());
            }

            if (cengTotalLxTaskDtos != null && cengTotalLxTaskDtos.size() > 0) {
                allHuoWeiDto.setRkTaskCount(cengTotalLxTaskDtos.get(0).getTaskCount());
            }

        }
        getSortList(allHuoWeiDtos);

        if (allHuoWeiDtos == null || allHuoWeiDtos.size() == 0) {
            logger.info("++++++++++++++++++没有找到" + spId + "商品对应的货格+++++++++++++++++++");
            return false;
        }

        for (int i = 0; i < allHuoWeiDtos.size(); i++) {
            AllHuoWeiDto allHuoWeiDto = allHuoWeiDtos.get(i);
            // 出库
            try {
                boolean isOutboundSuccess = bzEngineeChku.outboundByLayer(allHuoWeiDto.getCeng(), spId, stationDto);
                if (isOutboundSuccess) {
                    return true;
                }
            } catch (Exception e) {
                continue;
            }

        }

        FileLogHelper.writeLog("StoreCkError", "无法获得可用货位,当前商品为：" + spId);
        return false;


    }


    private Map<Integer, Integer> getMap(List<KuCunTotalDto> findAllUsedKuCun, List<KuCunTotalDto> findAllXkKuCun,
                                         List<KuCunTotalDto> findAllztKuCun) throws Exception {

        Map<Integer, Integer> spNumMap = new HashMap<Integer, Integer>();
        // 箱库库存+在途库存-待使用的库存
        List<KuCunTotalDto> xkAndztKuCun = new ArrayList<KuCunTotalDto>();
        if (findAllXkKuCun.size() == 0) {
            xkAndztKuCun.addAll(findAllztKuCun);
        } else if (findAllztKuCun.size() == 0) {
            xkAndztKuCun.addAll(findAllXkKuCun);
        } else {
            xkAndztKuCun.addAll(findAllztKuCun);
            xkAndztKuCun.addAll(findAllXkKuCun);
            // TODO
            Map<KuCunTotalDto, KuCunTotalDto> map = new HashMap<KuCunTotalDto, KuCunTotalDto>();
            for (KuCunTotalDto kuCunTotalDto : xkAndztKuCun) {
                if (map.containsKey(kuCunTotalDto)) {
                    map.put(kuCunTotalDto, KuCunTotalDto.merge(kuCunTotalDto, map.get(kuCunTotalDto)));
                } else {
                    map.put(kuCunTotalDto, kuCunTotalDto);
                }
            }
            Collection<KuCunTotalDto> values = map.values();
            if (values instanceof List) {
                xkAndztKuCun = (List) values;
            } else {
                xkAndztKuCun = new ArrayList<KuCunTotalDto>(values);
            }
        }

        if (findAllUsedKuCun.size() == 0) {
            for (KuCunTotalDto kuCunTotalDto : xkAndztKuCun) {
                spNumMap.put(kuCunTotalDto.getSpId(), kuCunTotalDto.getNum());
            }
        } else if (xkAndztKuCun.size() == 0) {
            for (KuCunTotalDto findAllUsedKuCunByHangdao1 : findAllUsedKuCun) {
                spNumMap.put(findAllUsedKuCunByHangdao1.getSpId(), 0 - findAllUsedKuCunByHangdao1.getNum());
            }
        } else {
            CopyOnWriteArrayList<KuCunTotalDto> findAllUsedKuCun2 = new CopyOnWriteArrayList<KuCunTotalDto>(
                    findAllUsedKuCun);
            CopyOnWriteArrayList<KuCunTotalDto> xkAndztKuCun2 = new CopyOnWriteArrayList<KuCunTotalDto>(xkAndztKuCun);
            // 用总库存减去目前订单数量为可用数量
            for (KuCunTotalDto kuCunTotalDto2 : xkAndztKuCun2) {
                for (KuCunTotalDto kuCunTotalDto1 : findAllUsedKuCun2) {
                    if (kuCunTotalDto1.getSpId() == kuCunTotalDto2.getSpId()) {
                        int num = kuCunTotalDto2.getNum() - kuCunTotalDto1.getNum();
                        spNumMap.put(kuCunTotalDto1.getSpId(), num);
                    } else {
                        int num = 0 - kuCunTotalDto1.getNum();
                        if (!spNumMap.containsKey((kuCunTotalDto1.getSpId()))) {
                            spNumMap.put(kuCunTotalDto1.getSpId(), num);
                        }
                        if (!spNumMap.containsKey(kuCunTotalDto2.getSpId())) {
                            spNumMap.put(kuCunTotalDto2.getSpId(), kuCunTotalDto2.getNum());
                        }
                    }
                }
            }
        }
        return spNumMap;
    }

    private List<HuoGeSpMxDto> getNewHuoGeMap(List<HuoGeDingDanDto> huoGeDingDanDtos) throws Exception {
        List<HuoGeSpMxDto> newHuoGeDingDans = new ArrayList<HuoGeSpMxDto>();
        Map<String, List<HuoGeDingDanDto>> buildGroupDictionary = ListHelper.buildGroupDictionary(huoGeDingDanDtos, p -> p.getHuoGeNo());
        for (String huogeNo : buildGroupDictionary.keySet()) {
            HuoGeSpMxDto huoGeSpMxDto = new HuoGeSpMxDto();
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            List<HuoGeDingDanDto> list = buildGroupDictionary.get(huogeNo);
            for (HuoGeDingDanDto huoGeDingDanDto2 : list) {
                map.put(huoGeDingDanDto2.getDingDanMxId(), huoGeDingDanDto2.getNum());
            }
            huoGeSpMxDto.setHuoGeNo(huogeNo);
            huoGeSpMxDto.setLxDingDanMxBindingMap(map);
        }
        return newHuoGeDingDans;
    }

    private List<AllHuoWeiDto> getSortList(List<AllHuoWeiDto> allHuoWeiDtos) {
        Collections.sort(allHuoWeiDtos, new Comparator<AllHuoWeiDto>() {

            @Override
            public int compare(AllHuoWeiDto o1, AllHuoWeiDto o2) {
                // 如果巷道作业数排序相同，再按层出库任务数排序（升序）
                int j = o1.getCkTaskCount() - o2.getCkTaskCount();
                if (j == 0) {
                    // 如果层出库任务数相同，再按层入库任务数排序（升序）
                    int k = o1.getRkTaskCount() - o2.getRkTaskCount();
                    if (k == 0) {
                        // 如果层任务数相同，再按料箱数排序（降序）
                        return o2.getLxCount() - o1.getLxCount();
                    }
                    return k;
                }
                return j;
            }
        });
        return allHuoWeiDtos;
    }
}
