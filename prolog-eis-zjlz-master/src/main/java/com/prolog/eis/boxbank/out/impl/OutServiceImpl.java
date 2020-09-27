/*
package com.prolog.eis.boxbank.out.impl;

import com.prolog.eis.boxbank.out.IOutService;
import com.prolog.eis.boxbank.rule.CarTaskCountRule;
import com.prolog.eis.boxbank.rule.StoreLocationDTO;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.enums.CkHoisterEnum;
import com.prolog.eis.model.point.PointLocation;
import com.prolog.eis.model.store.SxStore;
import com.prolog.eis.model.location.sxk.SxStoreLocation;
import com.prolog.eis.service.pointlocation.IPointLocationService;
import com.prolog.eis.service.repair.RepairPlanService;
import com.prolog.eis.service.store.IStoreLocationService;
import com.prolog.eis.service.store.IStoreService;
import com.prolog.eis.service.store.IStoreTaskService;
import com.prolog.eis.util.TaskUtils;
import com.prolog.eis.wcs.common.Constant;
import com.prolog.eis.wcs.dto.HoisterInfoDto;
import com.prolog.eis.wcs.model.WCSTask;
import com.prolog.eis.wcs.service.IWCSCommandService;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.eis.wcs.service.IWCSTaskService;
import com.prolog.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutServiceImpl implements IOutService {
   */
/* @Autowired
    private IStoreLocationService locationService;
    @Autowired
    private IStoreService storeService;
    @Autowired
    private CarTaskCountRule carTaskCountRule;
    @Autowired
    private IPointLocationService pointService;

//    @Autowired
//    private LayerLockRule layerLockRule;
    @Autowired
    private IWCSService wcsService;
    @Autowired
    private IStoreTaskService storeTaskService;

    @Autowired
    private IWCSTaskService taskService;
    @Autowired
    private IWCSCommandService commandService;
    @Autowired
    private EisProperties properties;
    @Autowired
    private RepairPlanService repairPlanService;

    @Autowired
    private IWCSService iwcsService;

    private final Logger logger = LoggerFactory.getLogger(OutServiceImpl.class);

    *//*
*/
/**
     *
     * @param lxNo
     * @param taskType
     * @param stationId
     * @return
     * @throws Exception
     *//*
*/
/*
    @Override
    public boolean checkOut(String lxNo,int taskType,int stationId) throws Exception {
        if(StringUtils.isBlank(lxNo)){
            throw new RuntimeException("料箱编号不能为空");
        }
        SxStore store = storeService.getByContinerNo(lxNo);
        if(store==null) {
            String message = String.format("料箱(%s)库存数据不存在",lxNo);
            throw new RuntimeException(message);
        }
        String taskId= TaskUtils.gerenateTaskId();

        SxStoreLocation sxStoreLocation = locationService.getById(store.getStoreLocationId());
        if(sxStoreLocation==null){
            throw new RuntimeException("未找到料箱("+lxNo+")的货位信息");
        }

        StoreLocationDTO location = new StoreLocationDTO();
        location.setContainerNo(lxNo);
        location.setLocationId(sxStoreLocation.getId());
        location.setLayer(sxStoreLocation.getLayer());
        location.setLocationNo(sxStoreLocation.getStoreNo());

        //小车任务数据规则
        if(!carTaskCountRule.execute(location,null)){
            throw new RuntimeException("小车任务数到达上限，稍后重试");
        }

        List<WCSTask> wts = taskService.getOutboundTask(stationId);
        if(wts.size()>properties.getMaxOutTaskCountPerStation()){
            throw new RuntimeException("站台出库任务数已达上限，稍后重试");
        }
        //处理出库业务
        storeTaskService.startOutboundTask(store,taskType,stationId,taskId);

        //调用wcs出一个料箱
        String containerNo = location.getContainerNo();
        String address = location.getLocationNo();
//        String target = "02000020065002"; //不指定提升机
        String target = "";
        //根据stationId寻找出库点
        if(taskType==SxStore.TASKTYPE_BH || taskType==SxStore.TASKTYPE_TK){
            //寻找任务最少的出库点
            if (taskType==SxStore.TASKTYPE_BH){
                repairPlanService.changeStatus(containerNo);
            }
            PointLocation pt = taskService.getBestOutboundLocation();
            target = pt.getPointId(); //1号
            stationId = pt.getStationId();
        }else{
            List<PointLocation> points = pointService.getPointByStation(stationId,PointLocation.TYPE_OUT_STORE);
            //安全门
            points = points.stream().filter(x->!x.isDisable()).collect(Collectors.toList());
            target = points.size()>0?points.get(0).getPointId():"";
        }
        //安全门
        String weight="0";
        String priority="99";
        //紧急制动
        WCSTask task = taskService.getByContainerNo(containerNo);
        if (task !=null){
            System.out.println("++++++++++++++++++"+containerNo+"已经存在出库任务++++++++++++++++++");
            return false;
        }
        String values = CkHoisterEnum.getValues(target);
        List<HoisterInfoDto> hoisterInfoDto = iwcsService.getHoisterInfoDto();
        //status=1 表示可用
        List<String> goodsHoister = hoisterInfoDto.stream().filter(x -> x.getStatus() == 1 && x.getCode() == 0).map(HoisterInfoDto::getHoist).collect(Collectors.toList());
        if (!goodsHoister.contains(values)) {
            String Tsj = goodsHoister.get(0);
            target = CkHoisterEnum.getAddress(Tsj);

        }
        //生成任务
        this.createTask(taskId,stationId,containerNo,address,target,taskType,Constant.TASK_TYPE_CK);
        //向wcs发送出库指令
       boolean result = commandService.sendCKCommand(taskId,containerNo,address,target,weight,priority);

        if(!result){
            throw new RuntimeException("发送任务失败");
        }
        return true;

    }


    *//*
*/
/**
     * 生成任务
     * @param taskId
     * @param stationId
     * @param containerNo
     * @param address
     * @param target
     * @throws Exception
     *//*
*/
/*
    private void createTask(String taskId,int stationId,String containerNo,String address,String target,int taskType,int wcsTaskType) throws Exception{
        WCSTask task = new WCSTask();
        task.setId(taskId);
        task.setStationId(stationId);
        task.setEisType(taskType);
        task.setWcsType(wcsTaskType);
        task.setStatus(WCSTask.STATUS_WAITTING);
        task.setContainerNo(containerNo);
        task.setAddress(address);
        task.setTarget(target);
        task.setGmtCreateTime(new Date());
        taskService.add(task);
    }
*//*


}
*/
