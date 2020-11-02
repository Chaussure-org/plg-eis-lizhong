package com.prolog.eis.sas.service.impl;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wcs.BCRDataDTO;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IContainerPathTaskDetailService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.sas.service.ISasCallbackService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.reflections.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SasCallbackServiceImpl implements ISasCallbackService {

    private final Logger logger = LoggerFactory.getLogger(SasCallbackServiceImpl.class);
    private final RestMessage<String> success = RestMessage.newInstance(true, "200", "操作成功", null);
    private final RestMessage<String> faliure = RestMessage.newInstance(false, "500", "操作失败", null);
    private final RestMessage<String> out = RestMessage.newInstance(false, "300", "订单箱异常", null);

    @Autowired
    private IContainerPathTaskDetailService containerPathTaskDetailService;

    @Autowired
    private ContainerPathTaskService containerPathTaskService;

    @Autowired
    private IWareHousingService iWareHousingService;
    @Autowired
    private IContainerStoreService iContainerStoreService;

    /**
     * 任务回告
     *
     * @param taskCallbackDTO
     * @return
     */
    @Override

    @Transactional(rollbackFor = Exception.class,timeout = 600)
    public RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO) throws Exception {
        if (taskCallbackDTO == null) {
            return success;
        }
        switch (taskCallbackDTO.getType()){
            case 1:
                doInboundTask(taskCallbackDTO);
                break;
            case 2:
                doOutboundTask(taskCallbackDTO);
                break;
            case 3:
                doHcTask(taskCallbackDTO);
                break;
            default:
                throw new Exception("回告类型未找到");
        }
        return success;
    }


    /**
     * 出库任务回告
     *
     * @param taskCallbackDTO
     */
    @LogInfo(desci = "sas出库任务回告",direction = "sas->eis",type = LogDto.SAS_TYPE_SEND_OUTBOUND_TASK_CALLBACK,systemType = LogDto.SAS)
    private void doOutboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {
        callBack(taskCallbackDTO);
    }

    /**
     * 更新任务相关
     * @param containerPathTaskDetail 任务详情
     * @param containerPathTask 任务
     */
    private void updateTaskInfo(ContainerPathTaskDetail containerPathTaskDetail, ContainerPathTask containerPathTask) {
        containerPathTask.setSourceArea(containerPathTaskDetail.getNextArea());
        containerPathTask.setSourceLocation(containerPathTaskDetail.getNextLocation());
        containerPathTask.setTargetLocation(containerPathTaskDetail.getNextLocation());
        containerPathTask.setTaskType(LocationConstants.PATH_TASK_TYPE_NONE);
        containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_NOTSTARTED);
        containerPathTask.setUpdateTime(new Date());
        containerPathTaskService.updateTask(containerPathTask);
        containerPathTaskDetail.setSourceArea(containerPathTaskDetail.getNextArea());
        containerPathTaskDetail.setSourceLocation(containerPathTaskDetail.getNextLocation());
        containerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE);
        containerPathTaskDetail.setTaskId(null);
        containerPathTaskDetail.setSortIndex(1);
        containerPathTaskDetail.setArriveTime(new Date());
        containerPathTaskDetail.setUpdateTime(new Date());
        containerPathTaskDetailService.updateTaskDetail(containerPathTaskDetail);
    }

    /**
     * 入库任务回告
     *
     * @param taskCallbackDTO
     */
    @LogInfo(desci = "sas入库任务回告",direction = "sas->eis",type = LogDto.SAS_TYPE_SEND_INBOUND_TASK_CALLBACK,systemType =
            LogDto.SAS)
    private void doInboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {
        callBack(taskCallbackDTO);
    }

    private void callBack(TaskCallbackDTO taskCallbackDTO) throws Exception {
        if (taskCallbackDTO.getStatus() == 2) {
            List<ContainerPathTaskDetail> containerPathTaskDetailList
                    = containerPathTaskDetailService.getTaskDetailByTaskId(taskCallbackDTO.getTaskId());
            if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
                throw new Exception("任务不存在");
            }
            ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailList.get(0);
            Timestamp nowTime = PrologDateUtils.parseObject(new Date());

            ContainerPathTask containerPathTask =
                    containerPathTaskService.getContainerPathTask(containerPathTaskDetail);
            //当前路径任务明细终点=任务汇总终点，则是最后一条任务
            //清除路径任务汇总，解绑载具
            if (containerPathTaskDetail.getNextArea().equals(containerPathTask.getTargetArea())) {
                updateTaskInfo(containerPathTaskDetail, containerPathTask);
            } else {//不是最后一条，则修改路径任务汇总当前区域，修改当前任务明细状态，并修改下一条任务明细为到位
                containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask
                        , nowTime);
            }
            //入库完成
            if (containerPathTaskDetail.getSourceArea().equals(StoreArea.SAS01)){
                iContainerStoreService.updateTaskTypeByContainer(taskCallbackDTO.getContainerNo(),0);
                iWareHousingService.deleteInboundTask(taskCallbackDTO.getContainerNo());
            }
            iContainerStoreService.updateTaskStausByContainer(taskCallbackDTO.getContainerNo(),0);
            //历史表
            //containerPathTaskService.saveContainerPathTaskHistory(containerPathTaskDetail, nowTime);
        }
    }

    /**
     * 换层任务回告
     * @param taskCallbackDTO
     */
    @LogInfo(desci = "sas出库任务回告",direction = "sas->eis",type = LogDto.SAS_TYPE_CHANGE_LAYER_CALLBACK,systemType = LogDto.SAS)
    private void doHcTask(TaskCallbackDTO taskCallbackDTO) throws Exception {
        callBack(taskCallbackDTO);
    }

}
