package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.RoadWayContainerTaskDto;
import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
import com.prolog.eis.engin.dao.TrayOutMapper;
import com.prolog.eis.engin.service.AgvInBoundEnginService;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.util.PrologDateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020/10/30 11:30
 */
public class AgvInBoundEnginServiceImpl implements AgvInBoundEnginService {

    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;
    @Autowired
    private TrayOutMapper trayOutMapper;
    @Override
    public void AgvInBound() throws Exception {
        List<ContainerPathTask> emptyAgvContainers = agvStoragelocationMapper.findEmptyAgvContainer();
        //agv 区域的空托盘
        Date currentTime=new Date();
        for (ContainerPathTask containerPathTask:emptyAgvContainers){
            int mins = PrologDateUtils.dateBetweenMin(currentTime, containerPathTask.getUpdateTime());
            if (mins>2){
                //回库
            }
        }
    }

    @Override
    public String computeInBoundArea() throws Exception {
        //1.任务数最少的 2.本巷道商品品种数最少的,商品均分
        //巷道的出库任务数 和入库任务数
        List<RoadWayContainerTaskDto> RoadWayContainerTasks = trayOutMapper.findRoadWayContainerTask();
        //巷道库存的 goodsId 和 goodsCount
        List<RoadWayGoodsCountDto> roadWayGoodsCounts = trayOutMapper.findRoadWayGoodsCount(1);


        List<OutContainerDto> outContainerDtoList = new ArrayList<>();
        //给任务数赋值
        for (RoadWayContainerTaskDto taskDto : RoadWayContainerTasks) {
            for (RoadWayGoodsCountDto GoodsCountDto : roadWayGoodsCounts) {
                if (taskDto.getRoadWay() == GoodsCountDto.getRoadWay()) {
                    GoodsCountDto.setTaskCount(taskDto.getInCount() + taskDto.getOutCount());
                }
            }
        }
       // List<RoadWayContainerTaskDto> RoadWayContainerTasks = trayOutMapper.findRoadWayContainerTask();
        return null;
    }


}
