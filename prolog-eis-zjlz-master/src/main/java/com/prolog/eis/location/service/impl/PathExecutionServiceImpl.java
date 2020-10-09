package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.mcs.McsResultDto;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.PathExecutionService;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.mcs.service.IMCSService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.rcs.service.IRCSService;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConst;
import com.prolog.eis.util.mapper.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PathExecutionServiceImpl implements PathExecutionService {
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private IRCSService rcsRequestService;
    @Autowired
    private IMCSService mcsRequestService;
    @Autowired
    private SxMoveStoreService sxMoveStoreService;


    @Override
    public void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //找货位
        AgvStoragelocationDTO agvStoragelocationDTO = agvLocationService.findLoacationByArea(containerPathTaskDetailDTO.getNextArea()
                , containerPathTaskDetailDTO.getNextLocation(), 0);
        if(null == agvStoragelocationDTO){
            throw new Exception("货位已满");
        }
        String taskId = UUID.randomUUID().toString();
        containerPathTaskDetailDTO.setTaskId(taskId);
        //确定路径任务号，汇总表状态为10待发送任务，改明细表状态0到位
        this.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConst.PATH_TASK_STATE_TOBESENT, LocationConst.PATH_TASK_DETAIL_STATE_INPLACE);
        //给rcs发送移动指令
        RcsRequestResultDto rcsRequestResultDto = rcsRequestService.sendTask(taskId, containerPathTaskDetailDTO.getContainerNo()
                , containerPathTaskDetailDTO.getSourceLocation(), agvStoragelocationDTO.getLocationNo(), null, "1");

        //rcs回传成功后，汇总表状态为20已发送指令,改明细表状态50给设备发送移动指令
        if ("0".equals(rcsRequestResultDto.getCode())) {
            this.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                    , LocationConst.PATH_TASK_STATE_SEND, LocationConst.PATH_TASK_DETAIL_STATE_SEND);
        } else {
            //...重发等相关
        }
        //rcs回告到位后改汇总和明细，并判断是否最终任务
    }

    @Override
    public void doMcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //申请载具
        ContainerPathTaskDetailDTO palletContainerPathTaskDetailDTO = agvLocationService.requestPallet(containerPathTaskDetailDTO.getNextLocation());
        if(null == palletContainerPathTaskDetailDTO){
            throw new Exception("没有空余的载具");
        }
        String taskId = UUID.randomUUID().toString();
        containerPathTaskDetailDTO.setTaskId(taskId);
        //确定路径任务号，汇总表状态为10待发送任务，改明细表状态0到位
        this.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConst.PATH_TASK_STATE_TOBESENT, LocationConst.PATH_TASK_DETAIL_STATE_INPLACE);
        //给rcs发送移动指令
        RcsRequestResultDto rcsRequestResultDto = rcsRequestService.sendTask(taskId, containerPathTaskDetailDTO.getContainerNo()
                , palletContainerPathTaskDetailDTO.getSourceLocation(), containerPathTaskDetailDTO.getNextLocation(), null, "1");

        //rcs载具移动回传成功后，汇总表状态为20已发送指令,改明细表状态10申请载具
        if ("0".equals(rcsRequestResultDto.getCode())) {
            this.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, palletContainerPathTaskDetailDTO
                    , LocationConst.PATH_TASK_STATE_SEND, LocationConst.PATH_TASK_DETAIL_STATE_APPLYPALLET);
        } else {
            //...重发等相关
        }
        //rcs回告完成后，开始绑定载具，发送mcs移载指令，删除msc容器任务和明细
    }

    @Override
    public void doRcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        String taskId = UUID.randomUUID().toString();
        containerPathTaskDetailDTO.setTaskId(taskId);
        //确定路径任务号，汇总表状态为10待发送任务，改明细表状态0到位
        this.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConst.PATH_TASK_STATE_TOBESENT, LocationConst.PATH_TASK_DETAIL_STATE_INPLACE);
        //给mcs发送移载任务，先用移库任务，后面移载接口定了再改
        McsResultDto mcsResultDto = null;
//        mcsRequestService.mcsContainerMove(taskId,
//                containerPathTaskDetailDTO.getContainerNo(),
//                0,
//                containerPathTaskDetailDTO.getNextLocation(),
//                containerPathTaskDetailDTO.getSourceLocation(),
//                "99");

        //mcs回传成功后，汇总表状态为20已发送指令,改明细表状态50给设备发送移动指令
        if(mcsResultDto.isRet()) {
            //发送成功
            //修改路径状态
            this.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                    , LocationConst.PATH_TASK_STATE_SEND, LocationConst.PATH_TASK_DETAIL_STATE_PALLETINPLACE);
        } else {
            //...重发等相关
        }
        //mcs回告完成，解绑载具，生成mcs任务明细,rcs的支架回写一个区域
    }
    
    @Override
    public void doMcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
    	
    	sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO);
    }

    @Override
    public void doMcsToWcsTask(ContainerPathTask containerPathTask,
                               ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

    }

    @Override
    public void doWcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

    }

    @Override
    public void doWcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

    }

    @Override
    public void doRcsToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

    }

    @Override
    public void doSasToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

    }

    @Override
    public void doWcsToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

    }

    @Override
    public void updateNextContainerPathTaskDetail(ContainerPathTaskDetail containerPathTaskDetail, ContainerPathTask containerPathTask, Timestamp nowTime) throws Exception {
        Query query = new Query(containerPathTaskDetail.getClass());
        query.addEq("containerNo", containerPathTaskDetail.getContainerNo());
        query.addEq("palletNo", containerPathTaskDetail.getPalletNo());
        query.addEq("sortIndex", containerPathTaskDetail.getSortIndex() + 1);
        List<ContainerPathTaskDetail> containerPathTaskDetailList = containerPathTaskDetailMapper.findByEisQuery(query);
        //查询有没有下一条任务，有则改下一条明细状态和点位，没有则修改汇总状态为0未开始
        if(!CollectionUtils.isEmpty(containerPathTaskDetailList)){
            ContainerPathTaskDetail nextContainerPathTaskDetail = containerPathTaskDetailList.get(0);
            nextContainerPathTaskDetail.setSourceLocation(containerPathTaskDetail.getNextLocation());
            nextContainerPathTaskDetail.setTaskState(LocationConst.PATH_TASK_DETAIL_STATE_INPLACE);
            nextContainerPathTaskDetail.setUpdateTime(nowTime);
            containerPathTaskDetailMapper.update(nextContainerPathTaskDetail);
        } else {
            containerPathTask.setTaskState(LocationConst.PATH_TASK_STATE_NOTSTARTED);
            containerPathTaskMapper.update(containerPathTask);
        }
    }

    @Transactional
    public void updateContainerPathTask(ContainerPathTask containerPathTask,
                                 ContainerPathTaskDetailDTO containerPathTaskDetailDTO
            , ContainerPathTaskDetailDTO palletContainerPathTaskDetailDTO, Integer hzTaskState, Integer mxTaskState) throws Exception {
        Timestamp newTime = PrologDateUtils.parseObject(new Date());

        containerPathTask.setTaskState(hzTaskState);
        containerPathTask.setUpdateTime(newTime);
        containerPathTaskMapper.update(containerPathTask);

        ContainerPathTaskDetail containerPathTaskDetail = new ContainerPathTaskDetail();
        BeanUtils.copyProperties(containerPathTaskDetailDTO, containerPathTaskDetail);
        containerPathTaskDetail.setTaskState(mxTaskState);
        containerPathTaskDetail.setUpdateTime(newTime);
        containerPathTaskDetailMapper.update(containerPathTaskDetail);

        //如传入载具明细，则是申请载具流程，需要生成一条rcs移载任务
        if(null != palletContainerPathTaskDetailDTO){
            ContainerPathTask palletContainerPathTask = new ContainerPathTask();
            BeanUtils.copyProperties(containerPathTask, palletContainerPathTask);
            palletContainerPathTask.setPalletNo(palletContainerPathTaskDetailDTO.getPalletNo());
            palletContainerPathTask.setContainerNo(palletContainerPathTaskDetailDTO.getPalletNo());
            palletContainerPathTask.setSourceArea(palletContainerPathTaskDetailDTO.getSourceArea());
            palletContainerPathTask.setTargetArea(containerPathTaskDetailDTO.getNextArea());
            palletContainerPathTask.setTaskState(hzTaskState);
            palletContainerPathTask.setCreateTime(newTime);
            containerPathTaskMapper.save(containerPathTask);

            ContainerPathTaskDetail palletContainerPathTaskDetail = new ContainerPathTaskDetail();
            BeanUtils.copyProperties(palletContainerPathTaskDetailDTO, palletContainerPathTaskDetail);
            palletContainerPathTaskDetail.setTaskState(mxTaskState);
            palletContainerPathTaskDetail.setTaskId(containerPathTaskDetailDTO.getTaskId());
            palletContainerPathTaskDetail.setNextArea(containerPathTaskDetailDTO.getNextArea());
            palletContainerPathTaskDetail.setUpdateTime(newTime);
            containerPathTaskDetailMapper.save(palletContainerPathTaskDetail);
        }
    }
}
