package com.prolog.eis.wcs.service.impl;

import com.prolog.eis.wcs.common.Constant;
import com.prolog.eis.wcs.dao.WCSCommandMapper;
import com.prolog.eis.wcs.dao.WCSHistoryCommandMapper;
import com.prolog.eis.wcs.model.WCSCommand;
import com.prolog.eis.wcs.model.WCSHistoryCommand;
import com.prolog.eis.wcs.service.IWCSCommandService;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.eis.wcs.service.IWCSTaskService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.core.exception.ParameterException;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Order;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.JsonUtils;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WCSCommandServiceImpl implements IWCSCommandService {
    @Autowired
    private WCSCommandMapper mapper;
    @Autowired
    private WCSHistoryCommandMapper historyMapper;
    @Autowired
    private IWCSService wcsService;
    @Autowired
    private IWCSTaskService taskService;
    @Override
    public void add(WCSCommand command) throws Exception {
        if(command==null || StringUtils.isBlank(command.getTaskId()))
            throw new ParameterException("参数不能为空");

        if(command.getType() != Constant.COMMAND_TYPE_LIGHT && command.getType()!=Constant.COMMAND_TYPE_REQUEST_ORDER_BOX && StringUtils.isBlank(command.getContainerNo()) ){
            throw new ParameterException("命令容器号不能为空");
        }

        if(this.getByContainerNo(command.getContainerNo()).size()>0){
            throw new ParameterException("同时作业的容器不能重复");
        }

        mapper.save(command);
    }

    @Override
    public void delete(int id) {
        mapper.deleteById(id,WCSCommand.class);
    }

    @Override
    public List<WCSCommand> getAll() {
        return mapper.findByMap(null,WCSCommand.class);
    }

    /**
     * 通过求余拆分记录集
     * @param denominator 分母
     * @param remainder 余数
     * @return
     * @throws Exception
     */
    @Override
    public List<WCSCommand> getByMod(int denominator, int remainder) throws Exception{
        return mapper.getByMod(denominator,remainder);
    }

    /**
     * 发送指令
     *
     * @param command
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean sendCommand(WCSCommand command) throws Exception {
        if(command==null || command.getType()<1 || StringUtils.isBlank(command.getTaskId())){
            throw new ParameterException("参数无效");
        }

        RestMessage<String> result = null;
        switch(command.getType()){
            case Constant.COMMAND_TYPE_CK:
                //出库 同步
                result = wcsService.sendContainerTask(command.getTaskId(),Constant.TASK_TYPE_CK,command.getContainerNo(),command.getAddress(),command.getTarget(),command.getWeight(),command.getPriority(),command.getStatus());
                break;
            case Constant.COMMAND_TYPE_LIGHT:
                //亮光 同步
                if (command.getLights() == null) {
                    result = wcsService.light(command.getStationNo(), new String[]{});
                } else {
                    result = wcsService.light(command.getStationNo(), command.getLights().split(","));
                }
                break;
            case Constant.COMMAND_TYPE_REQUEST_ORDER_BOX:
                if(StringUtils.isBlank(command.getTarget()))
                    throw new ParameterException("目标地址为空");
                taskService.startTask(command.getTaskId());
                result = wcsService.requestOrderBox(command.getTaskId(),command.getTarget());
                //当命令接收成功时，开始wctask
                break;
            case Constant.COMMAND_TYPE_RK:
                if(StringUtils.isBlank(command.getTarget())||StringUtils.isBlank(command.getAddress()))
                    throw new ParameterException("目标地址或起始地址为空");
                result =wcsService.sendContainerTask(command.getTaskId(),Constant.TASK_TYPE_RK,command.getContainerNo(),command.getAddress(),command.getTarget(),"0","99",command.getStatus());
                break;
            case Constant.COMMAND_TYPE_XZ:
                if(StringUtils.isBlank(command.getTarget())||StringUtils.isBlank(command.getAddress()))
                    throw new ParameterException("目标地址或起始地址为空");
                result = wcsService.lineMove(command.getTaskId(),command.getAddress(),command.getTarget(),command.getContainerNo(),Constant.TASK_TYPE_XZ,0);
                break;
            case Constant.COMMAND_TYPE_HC:
                if (command.getTargetLayer()==0 ||command.getSourceLayer()==0){
                    throw new ParameterException("原层或目标层不能为空");
                }
                result = wcsService.moveCar(command.getTaskId(),0,command.getSourceLayer(),command.getTargetLayer());
                break;
            default:
                throw new RuntimeException("命令类型错误");
        }

        WCSHistoryCommand his = command.getHistoryCommand();
        his.setSendTime(new Date());
        his.setResult(JsonUtils.toString(result));
        historyMapper.save(his);

        if(result.isSuccess() && command.getId()>0){
            try {
                this.delete(command.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result.isSuccess();

    }

    public WCSCommand getById(int id){
        return mapper.findById(id,WCSCommand.class);
    }

    @Override
    public boolean sendHCCommand(String taskId, int sourceLayer, int targetLayer) throws Exception {
        WCSCommand cmd = new WCSCommand();
        cmd.setTaskId(taskId);
        cmd.setType(Constant.COMMAND_TYPE_HC);
        cmd.setSourceLayer(sourceLayer);
        cmd.setTargetLayer(targetLayer);
        return this.sendCommand(cmd);
    }

    @Override
    public List<WCSCommand> getByContainerNo(String containerNo){
        return mapper.findByMap(MapUtils.put("containerNo",containerNo).getMap(),WCSCommand.class);
    }
    /**
     * 根据类型查询任务
     *
     * @param type
     * @return
     */
    @Override
    public List<WCSCommand> getByType(int type) {
        return mapper.findByMap(MapUtils.put("type",type).getMap(),WCSCommand.class);
    }

    /**
     * 根据目标点获取命令
     *
     * @param target
     * @return
     */
    @Override
    public List<WCSCommand> getByTarget(String target) {
        if(StringUtils.isBlank(target)){
            return new ArrayList<>();
        }
        Criteria ctr = Criteria.forClass(WCSCommand.class);
        ctr.setOrder(Order.newInstance().asc("createTime"));
        ctr.setRestriction(Restrictions.eq("target",target));
        return mapper.findByCriteria(ctr);
    }

    @Override
    public long getCountByTarget(String target) {
        if(StringUtils.isBlank(target)){
            return 0;
        }
        Criteria ctr = Criteria.forClass(WCSCommand.class);
        ctr.setOrder(Order.newInstance().asc("createTime"));
        ctr.setRestriction(Restrictions.eq("target",target));
        return mapper.findCountByCriteria(ctr);
    }


    /**
     * 获取命令排除目标点
     *
     * @param targets
     * @return
     */
    @Override
    public List<WCSCommand> getExceptTargets(String[] targets) {
        if(targets==null || targets.length==0){
            return new ArrayList<>();
        }
        Criteria ctr = Criteria.forClass(WCSCommand.class);
        ctr.setOrder(Order.newInstance().asc("createTime"));
        ctr.setRestriction(Restrictions.notIn("target", String.valueOf(targets)));
        return mapper.findByCriteria(ctr);
    }

    /**
     * 发送亮灯指令
     *
     * @param taskId
     * @param stationNo
     * @param lights
     * @return
     */
    @Override
    public boolean sendLightCommand(String taskId, String stationNo, String lights)  throws Exception {
        WCSCommand cmd = new WCSCommand();
        cmd.setTaskId(taskId);
        cmd.setType(Constant.COMMAND_TYPE_LIGHT);
        cmd.setStationNo(stationNo);
        cmd.setLights(lights);
        return this.sendCommand(cmd);
    }

    /**
     * 发送行走指令
     *
     * @param taskId
     * @param address
     * @param target
     * @param containerNo
     * @return
     */
    @Override
    public boolean sendXZCommand(String taskId, String address, String target, String containerNo) throws Exception {
        WCSCommand cmd = new WCSCommand();
        cmd.setTaskId(taskId);
        cmd.setType(Constant.COMMAND_TYPE_XZ);
        cmd.setAddress(address);
        cmd.setTarget(target);
        cmd.setContainerNo(containerNo);
        return this.sendCommand(cmd);
    }

    /**
     * 发送出库指令
     *
     * @param taskId
     * @param containerNo
     * @param address
     * @param target
     * @param weight
     * @param priority
     * @return
     */
    @Override
    public boolean sendCKCommand(String taskId, String containerNo, String address, String target, String weight, String priority) throws Exception {
        WCSCommand cmd = new WCSCommand();
        cmd.setTaskId(taskId);
        cmd.setType(Constant.COMMAND_TYPE_CK);
        cmd.setAddress(address);
        cmd.setTarget(target);
        cmd.setContainerNo(containerNo);
        cmd.setWeight(weight);
        cmd.setPriority(priority);
        cmd.setStatus(0);
        return this.sendCommand(cmd);
    }

    /**
     * 发送入库指令
     *
     * @param taskId
     * @param containerNo
     * @param address
     * @param target
     * @param weight
     * @param priority
     * @return
     */
    @Override
    public boolean sendRKCommand(String taskId, String containerNo, String address, String target, String weight, String priority) throws Exception {
        WCSCommand cmd = new WCSCommand();
        cmd.setTaskId(taskId);
        cmd.setType(Constant.COMMAND_TYPE_RK);
        cmd.setAddress(address);
        cmd.setTarget(target);
        cmd.setContainerNo(containerNo);
        cmd.setWeight(weight);
        cmd.setPriority(priority);
        cmd.setStatus(0);
        return this.sendCommand(cmd);
    }

    /**
     * 发送订单框请求指令
     *
     * @param taskId
     * @return
     */
    @Override
    public boolean sendOrderBoxReqCommand(String taskId,String target) throws Exception{
        WCSCommand cmd = new WCSCommand();
        cmd.setTaskId(taskId);
        cmd.setTarget(target);
        cmd.setType(Constant.COMMAND_TYPE_REQUEST_ORDER_BOX);
        return this.sendCommand(cmd);
    }
}
