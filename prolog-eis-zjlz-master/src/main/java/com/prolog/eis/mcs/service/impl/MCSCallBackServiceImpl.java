package com.prolog.eis.mcs.service.impl;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.mcs.service.IMCSCallBackService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.util.mapper.Query;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:56
 */
@Service
public class MCSCallBackServiceImpl implements IMCSCallBackService {
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private SxMoveStoreService sxMoveStoreService;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private IContainerStoreService iContainerStoreService;

    /**
     * mcs回告
     *
     * @param mcsCallBackDto 回告内容
     * @throws Exception
     */
    @Override
    @LogInfo(desci = "堆垛机mcs任务回告", direction = "mcs->eis", type = LogDto.MCS_TYPE_CALLBACK, systemType = LogDto.MCS)
    @Transactional(rollbackFor = Exception.class)
    public void mcsCallback(McsCallBackDto mcsCallBackDto) throws Exception {
        String taskCode = mcsCallBackDto.getTaskId();
        int state = mcsCallBackDto.getStatus();
        String containerNo = mcsCallBackDto.getContainerNo();
        if (StringUtils.isEmpty(taskCode)) {
            throw new Exception("参数不能为空");
        }
        Query query = new Query(ContainerPathTaskDetail.class);
        query.addEq("taskId", taskCode);
        query.addEq("containerNo", containerNo);
        List<ContainerPathTaskDetail> containerPathTaskDetailList = containerPathTaskDetailMapper.findByEisQuery(query);
        if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
            throw new Exception("任务不存在");
        }
        Timestamp nowTime = PrologDateUtils.parseObject(new Date());

        switch (state) {
            case LocationConstants.MCS_TASK_METHOD_START:
                this.callbackStart(containerPathTaskDetailList, nowTime);
                break;
            case LocationConstants.MCS_TASK_METHOD_END:
                this.callbackEnd(containerPathTaskDetailList, nowTime);
                break;
            default:
                throw new Exception("任务类型有误");
        }
    }

    /**
     * mcs回告开始
     *
     * @param containerPathTaskDetailList
     * @param nowTime
     * @throws Exception
     */
    private void callbackStart(List<ContainerPathTaskDetail> containerPathTaskDetailList, Timestamp nowTime) throws Exception {
        ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
        Integer taskState = containerPathTaskDetail.getTaskState();

        switch (taskState) {
            case LocationConstants.PATH_TASK_DETAIL_STATE_SEND:
                sxMoveStoreService.mcsCallBackStart(containerPathTaskDetail, nowTime);
                break;
            case LocationConstants.PATH_TASK_DETAIL_STATE_PALLETINPLACE:
                this.mcsTransferCallbackStart(containerPathTaskDetailList, nowTime);
                break;
            default:
        }
    }

    /**
     * mcs移载回告开始
     *
     * @param containerPathTaskDetailList
     * @param nowTime
     * @throws Exception
     */
    private void mcsTransferCallbackStart(List<ContainerPathTaskDetail> containerPathTaskDetailList, Timestamp nowTime) throws Exception {
        //有2条数据，则是mcs-rcs移载任务
        if (containerPathTaskDetailList.size() > 1) {
            for (ContainerPathTaskDetail containerPathTaskDetail : containerPathTaskDetailList) {
                ContainerPathTask containerPathTask = this.getContainerPathTask(containerPathTaskDetail);
                containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_START);
                containerPathTask.setUpdateTime(nowTime);
                containerPathTaskMapper.update(containerPathTask);

                containerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_BINDPALLET);
                containerPathTaskDetail.setUpdateTime(nowTime);
                containerPathTaskDetailMapper.update(containerPathTaskDetail);
            }
        } else {//否则是rcs-mcs移载任务
            ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
            ContainerPathTask containerPathTask = this.getContainerPathTask(containerPathTaskDetail);
            containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_START);
            containerPathTask.setUpdateTime(nowTime);
            containerPathTaskMapper.update(containerPathTask);

            containerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_BINDPALLET);
            containerPathTaskDetail.setUpdateTime(nowTime);
            containerPathTaskDetailMapper.update(containerPathTaskDetail);
        }
    }

    /**
     * mcs回告结束
     *
     * @param containerPathTaskDetailList
     * @param nowTime
     * @throws Exception
     */
    private void callbackEnd(List<ContainerPathTaskDetail> containerPathTaskDetailList, Timestamp nowTime) throws Exception {
        ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
        Integer taskState = containerPathTaskDetail.getTaskState();
        //当从堆垛机库出库完成时 更改路径里taskType状态
        containerPathTaskService.updatePathTaskTypeByContainer(containerPathTaskDetail.getContainerNo(),0);
        switch (taskState) {
            //先回告了开始，才能改成完成状态
            case LocationConstants.PATH_TASK_DETAIL_STATE_START:
                sxMoveStoreService.mcsCallBackComplete(containerPathTaskDetail, nowTime);
                break;
            case LocationConstants.PATH_TASK_DETAIL_STATE_BINDPALLET:
                this.mcsTransferCallbackEnd(containerPathTaskDetailList, nowTime);
                break;
            default:
        }
    }

    /**
     * mcs移载回告结束
     *
     * @param containerPathTaskDetailList
     * @param nowTime
     * @throws Exception
     */
    private void mcsTransferCallbackEnd(List<ContainerPathTaskDetail> containerPathTaskDetailList, Timestamp nowTime) throws Exception {
        //有2条数据，则是mcs-rcs移载任务
        if (containerPathTaskDetailList.size() > 1) {
            //载具解绑
            for (ContainerPathTaskDetail containerPathTaskDetail : containerPathTaskDetailList) {
                ContainerPathTask containerPathTask = this.getContainerPathTask(containerPathTaskDetail);
                //如果托盘号为空，则是rcs任务
                if (null == containerPathTaskDetail.getContainerNo()) {
                    containerPathTaskDetailMapper.deleteById(containerPathTaskDetail.getId(), ContainerPathTaskDetail.class);
                    containerPathTaskMapper.deleteById(containerPathTask.getId(), ContainerPathTask.class);
                } else {
                    containerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_NOTSTARTED);
                    containerPathTaskDetail.setUpdateTime(nowTime);
                    containerPathTaskDetailMapper.update(containerPathTaskDetail);

                    containerPathTask.setSourceArea(containerPathTaskDetail.getNextArea());
                    containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_TOBESENT);
                    containerPathTask.setUpdateTime(nowTime);
                    containerPathTaskMapper.update(containerPathTask);

                    containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask, nowTime);
                }
            }
        } else {//否则是rcs-mcs移载任务
            ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
            ContainerPathTask containerPathTask = this.getContainerPathTask(containerPathTaskDetail);

            //写一个rcs载具回暂存区任务
            ContainerPathTask newContainerPathTask = new ContainerPathTask();
            newContainerPathTask.setPalletNo(containerPathTask.getPalletNo());
            newContainerPathTask.setContainerNo(containerPathTask.getPalletNo());
            newContainerPathTask.setSourceArea(containerPathTaskDetail.getSourceArea());
            newContainerPathTask.setSourceLocation(containerPathTaskDetail.getSourceLocation());
            newContainerPathTask.setTargetArea("ZC");
            newContainerPathTask.setTargetLocation("ZC");
            newContainerPathTask.setActualHeight(containerPathTask.getActualHeight());
            newContainerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_TOBESENT);
            newContainerPathTask.setCreateTime(nowTime);
            containerPathTaskMapper.save(containerPathTask);

            ContainerPathTaskDetail newContainerPathTaskDetail = new ContainerPathTaskDetail();
            newContainerPathTaskDetail.setPalletNo(containerPathTaskDetail.getPalletNo());
            newContainerPathTaskDetail.setContainerNo(containerPathTaskDetail.getPalletNo());
            newContainerPathTaskDetail.setSourceArea(containerPathTaskDetail.getSourceArea());
            newContainerPathTaskDetail.setSourceLocation(containerPathTaskDetail.getSourceLocation());
            newContainerPathTaskDetail.setNextArea("ZC");
            newContainerPathTaskDetail.setNextLocation("ZC");
            newContainerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE);
            newContainerPathTaskDetail.setSortIndex(1);
            newContainerPathTaskDetail.setCreateTime(nowTime);
            containerPathTaskDetailMapper.save(containerPathTaskDetail);

            //正常路径状态修改
            containerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_NOTSTARTED);
            containerPathTaskDetail.setUpdateTime(nowTime);
            containerPathTaskDetailMapper.update(containerPathTaskDetail);

            containerPathTask.setPalletNo(containerPathTask.getContainerNo());
            containerPathTask.setSourceArea(containerPathTaskDetail.getNextArea());
            containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_TOBESENT);
            containerPathTask.setUpdateTime(nowTime);
            containerPathTaskMapper.update(containerPathTask);

            containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask, nowTime);
        }
    }

    /**
     * 修改任务汇总状态
     *
     * @param containerPathTaskDetail
     * @return
     * @throws Exception
     */
    private ContainerPathTask getContainerPathTask(ContainerPathTaskDetail containerPathTaskDetail) throws Exception {
        Query query = new Query(ContainerPathTask.class);
        query.addEq("palletNo", containerPathTaskDetail.getPalletNo());
        query.addEq("containerNo", containerPathTaskDetail.getContainerNo());
        List<ContainerPathTask> containerPathTaskList = containerPathTaskMapper.findByEisQuery(query);
        if (CollectionUtils.isEmpty(containerPathTaskList)) {
            throw new Exception("任务不存在");
        }
        return containerPathTaskList.get(0);
    }
}
