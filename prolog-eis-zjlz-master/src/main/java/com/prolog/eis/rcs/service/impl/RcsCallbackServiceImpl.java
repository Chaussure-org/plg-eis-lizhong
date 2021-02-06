package com.prolog.eis.rcs.service.impl;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.rcs.RcsCallbackDto;
import com.prolog.eis.location.dao.AgvBindingDetaileMapper;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.rcs.service.IRcsCallbackService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.wcs.service.IOpenDiskService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class RcsCallbackServiceImpl implements IRcsCallbackService {

    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private AgvBindingDetaileMapper agvBindingDetaileMapper;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;
    @Autowired
    private IOpenDiskService openDiskService;

    @Override
    @LogInfo(desci = "rcs任务回告", direction = "rcs->eis", type = LogDto.RCS_TYPE_CALLBACK, systemType = LogDto.RCS)
    @Transactional(rollbackFor = Exception.class)
    public void rcsCallback(RcsCallbackDto rcsCallbackDto) throws Exception {
        if (StringUtils.isBlank(rcsCallbackDto.getMethod()) || StringUtils.isBlank(rcsCallbackDto.getTaskCode())) {
            throw new Exception("参数不能为空");
        }
        List<ContainerPathTaskDetail> containerPathTaskDetailList
                = containerPathTaskDetailMapper.findByMap(MapUtils.put("taskId", rcsCallbackDto.getTaskCode()).getMap(), ContainerPathTaskDetail.class);
        if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
            throw new Exception("任务不存在");
        }

        switch (rcsCallbackDto.getMethod()) {
            case LocationConstants.RCS_TASK_METHOD_START:
                break;
            case LocationConstants.RCS_TASK_METHOD_OUTBIN:
                this.callbackStart(containerPathTaskDetailList);
                break;
            case LocationConstants.RCS_TASK_METHOD_END:
                this.callbackEnd(containerPathTaskDetailList);
                break;
            default:
                throw new Exception("任务类型有误");
        }
    }

    /**
     * rcs回告任务开始
     *
     * @param containerPathTaskDetailList
     * @throws Exception
     */
    private void callbackStart(List<ContainerPathTaskDetail> containerPathTaskDetailList) throws Exception {
        ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
        Timestamp nowTime = PrologDateUtils.parseObject(new Date());

        ContainerPathTask containerPathTask = containerPathTaskService.getContainerPathTask(containerPathTaskDetail);
        containerPathTaskMapper.updateMapById(containerPathTask.getId()
                , MapUtils.put("taskState", LocationConstants.PATH_TASK_STATE_START)
                        .put("updateTime", nowTime).getMap()
                , ContainerPathTask.class);

        containerPathTaskDetailMapper.updateMapById(containerPathTaskDetail.getId()
                , MapUtils.put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_START)
                        .put("moveTime", nowTime)
                        .put("updateTime", nowTime).getMap()
                , ContainerPathTaskDetail.class);
        agvStoragelocationMapper.updateLocationLock(containerPathTaskDetail.getSourceLocation(), AgvStoragelocation.TASK_EMPTY);
        if (containerPathTask.getSourceArea().equals(OpenDisk.OPEN_DISK_OUT)){
            //agv位取货后更新状态(拆盘机出口)
            List<OpenDisk> openDiskList = openDiskService.findOpenDiskByMap(MapUtils.put("openDiskId", OpenDisk.OPEN_DISK_OUT).getMap());
            OpenDisk openDisk = openDiskList.get(0);
            openDisk.setTaskStatus(0);
            openDiskService.updateOpenDisk(openDisk);
        }
        if (containerPathTask.getSourceArea().equals(OpenDisk.OPEN_DISK_IN)){
            //agv位放货后更新状态(拆盘机入口)
            List<OpenDisk> openDiskList = openDiskService.findOpenDiskByMap(MapUtils.put("openDiskId", OpenDisk.OPEN_DISK_IN).getMap());
            OpenDisk openDisk = openDiskList.get(0);
            openDisk.setTaskStatus(1);
            openDiskService.updateOpenDisk(openDisk);
        }
    }

    /**
     * rcs回告任务结束
     *
     * @param containerPathTaskDetailList
     * @throws Exception
     */
    private void callbackEnd(List<ContainerPathTaskDetail> containerPathTaskDetailList) throws Exception {
        ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
        Timestamp nowTime = PrologDateUtils.parseObject(new Date());

        ContainerPathTask containerPathTask = containerPathTaskService.getContainerPathTask(containerPathTaskDetail);
        //当前路径任务明细终点=任务汇总终点，则是最后一条任务
        //清除路径任务汇总，解绑载具
        if (containerPathTaskDetail.getNextArea().equals(containerPathTask.getTargetArea())) {
            containerPathTaskMapper.updateMapById(containerPathTask.getId()
                    , MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                            .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("targetLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskType", LocationConstants.PATH_TASK_TYPE_NONE)
                            .put("taskState", LocationConstants.PATH_TASK_STATE_NOTSTARTED)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTask.class);

            containerPathTaskDetailMapper.updateMapById(containerPathTaskDetail.getId()
                    , MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                            .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE)
                            .put("taskId", null)
                            .put("sortIndex", 1)
                            .put("arriveTime", nowTime)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTaskDetail.class);
            if (containerPathTaskDetail.getSourceArea().equals(StoreArea.RCS01)) {
                agvBindingDetaileMapper.updateAgvStatus(containerPathTask.getContainerNo(), OrderBill.ORDER_STATUS_FINISH);
            }
        } else {//不是最后一条，则修改路径任务汇总当前区域，修改当前任务明细状态，并修改下一条任务明细为到位
            containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask, nowTime);
        }
        //历史表
        //containerPathTaskService.saveContainerPathTaskHistory(containerPathTaskDetail, nowTime);
    }

}
