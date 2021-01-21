package com.prolog.eis.engin.service.impl;

import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.RoadWayContainerTaskDto;
import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
import com.prolog.eis.engin.dao.TrayOutMapper;
import com.prolog.eis.engin.service.AgvInBoundEnginService;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020/10/30 11:30
 */
@Service
public class AgvInBoundEnginServiceImpl implements AgvInBoundEnginService {

    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;
    @Autowired
    private TrayOutMapper trayOutMapper;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private EisProperties eisProperties;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IContainerStoreService containerStoreService;

    @Override
    public void AgvInBound() throws Exception {
        List<ContainerPathTask> emptyAgvContainers = agvStoragelocationMapper.findEmptyAgvContainer();
        //agv 区域的空托盘
        Date currentTime = new Date();
        for (ContainerPathTask containerPathTask : emptyAgvContainers) {
            int mins = PrologDateUtils.dateBetweenMin(currentTime, containerPathTask.getUpdateTime());
            if (mins > eisProperties.getAgvInboundTime()) {
                //回库
                List<RoadWayContainerTaskDto> roadWayContainerTasks = trayOutMapper.findRoadWayContainerTask();
                roadWayContainerTasks.stream().sorted(Comparator.comparing(RoadWayContainerTaskDto::getInCount).thenComparing(RoadWayContainerTaskDto::getOutCount));
                List<ContainerStore> containerStoreList = containerStoreService.findByMap(MapUtils.put("containerNo", containerPathTask.getContainerNo()).getMap());
                if (containerStoreList!=null && containerStoreList.size()>0) {
                    Goods goods = goodsService.findGoodsById(containerStoreList.get(0).getGoodsId());
                    String target = goods.getGoodsOneType().equals("包材")?""+(roadWayContainerTasks.get(0).getRoadWay()-1):"MCS0" + (roadWayContainerTasks.get(0).getRoadWay()-1);
                    pathSchedulingService.containerMoveTask(containerPathTask.getContainerNo(),
                            StoreArea.RCS01,
                            target);
                }

            }
        }
    }

    @Override
    public String computeInBoundArea() throws Exception {
        return null;
    }


}
