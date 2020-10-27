package com.prolog.eis.rcs.service.impl;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.rcs.service.IRCSCallbackService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class RcsCallbackServiceImpl implements IRCSCallbackService {

    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;

    @Override
    @LogInfo(desci = "rcs任务回告",direction = "rcs->eis",type = LogDto.RCS_TYPE_CALLBACK,systemType = LogDto.RCS)
    @Transactional(rollbackFor = Exception.class)
    public void rcsCallback(String taskCode, String method) throws Exception {
        if (StringUtils.isEmpty(taskCode) || StringUtils.isEmpty(method)) {
            throw new Exception("参数不能为空");
        }
        List<ContainerPathTaskDetail> containerPathTaskDetailList
                = containerPathTaskDetailMapper.findByMap(MapUtils.put("taskId", taskCode).getMap(), ContainerPathTaskDetail.class);
        if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
            throw new Exception("任务不存在");
        }

        switch (method) {
            case LocationConstants.RCS_TASK_METHOD_START :
            case LocationConstants.RCS_TASK_METHOD_OUTBIN :
                this.callbackStart(containerPathTaskDetailList);
                break;
            case LocationConstants.RCS_TASK_METHOD_END :
                this.callbackEnd(containerPathTaskDetailList);
                break;
            default:
                throw new Exception("任务类型有误");
        }
    }

    /**
     * rcs回告任务开始
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
    }

    /**
     * rcs回告任务结束
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
        } else {//不是最后一条，则修改路径任务汇总当前区域，修改当前任务明细状态，并修改下一条任务明细为到位
            // TODO: 2020/10/27  agv 最终到达agv区域
            containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask, nowTime);
        }
        //历史表
        containerPathTaskService.saveContainerPathTaskHistory(containerPathTaskDetail, nowTime);
    }

}
