package com.prolog.eis.wcs.service.impl;


import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.enums.ConstantEnum;
import com.prolog.eis.enums.PointChangeEnum;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.wcs.WCSCommand;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wcs.service.IWCSCallbackService;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.framework.common.message.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WCSCallbackServiceImpl implements IWCSCallbackService {

    private final Logger logger = LoggerFactory.getLogger(WCSCallbackServiceImpl.class);

    @Autowired
    private EisProperties properties;

    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Autowired
    private IPointLocationService pointLocationService;

    @Autowired
    private IWareHousingService wareHousingService;

    @Autowired
    private IStationService stationService;

    @Autowired
    private IWCSService wcsService;

    @Autowired
    private IContainerStoreService containerStoreService;

    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private IInventoryTaskDetailService inventoryTaskDetailService;


    private final RestMessage<String> success = RestMessage.newInstance(true, "200", "操作成功", null);
    private final RestMessage<String> faliure = RestMessage.newInstance(false, "500", "操作失败", null);
    private final RestMessage<String> out = RestMessage.newInstance(false, "300", "订单箱异常", null);

    /**
     * 任务回告
     *
     * @param taskCallbackDTO
     * @return
     */
    @Override
    @LogInfo(desci = "wcs行走任务回告", direction = "wcs->eis", type = LogDto.WCS_TYPE_TASK_CALLBACK, systemType = LogDto.WCS)
    @Transactional(rollbackFor = Exception.class)
    public RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO) {
        if (taskCallbackDTO==null){
            return success;
        }
        try{
            this.doXZTask(taskCallbackDTO);
            return success;
        }catch (Exception e){
            logger.warn("行走任务回告失败",e);
            return faliure;
        }


    }


    /**
     * BCR 回告
     *
     * @param bcrDataDTO
     * @return
     */
    @Override
    @LogInfo(desci = "wcsBCR请求", direction = "wcs->eis", type = LogDto.WCS_TYPE_BCR_REQUEST, systemType = LogDto.WCS)
    public RestMessage<String> executeBcrCallback(BCRDataDTO bcrDataDTO) throws Exception {
        if (bcrDataDTO == null) {
            return success;
        }
        try {
            switch (bcrDataDTO.getType()) {
                case ConstantEnum.TYPE_RK:
                    this.inboundTaskCallback(bcrDataDTO);
                    break;
                case ConstantEnum.TYPE_IN:
                    this.inStation(bcrDataDTO);
                    break;
                    //箱库二楼入库BCR请求
                case ConstantEnum.TYPE_MOVE:
                    this.checkGoOn(bcrDataDTO);
                    break;
                default:
                    throw new Exception("没有找到该类型");
            }
            return success;
        } catch (Exception e) {
            logger.warn("回告失败", e);
            return success;
        }
    }


    /**
     * 行走任务回告,入库点
     *
     * @param taskCallbackDTO
     */
    @Transactional(rollbackFor = Exception.class, timeout = 600)
    public void doXZTask(TaskCallbackDTO taskCallbackDTO) throws Exception {
        String taskId = taskCallbackDTO.getTaskId();
        //通过任务id找对应的任务，如果任务的容器号无法对应上则报错，对应上则删除任务
    }




    /**
     * 入库任务
     *
     * @param bcrDataDTO
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void inboundTaskCallback(BCRDataDTO bcrDataDTO) throws Exception {
        String containerNo = bcrDataDTO.getContainerNo();
        String address = bcrDataDTO.getAddress();
        PointLocation point = pointLocationService.getPointByPointId(address);

        if (point == null) {
            throw new RuntimeException("找不到入口点位");
        }

        //外形检测不合格
        if (!bcrDataDTO.isShapeInspect()) {
            //回退
            this.exitContainer(address, containerNo);
            logger.info("体积不合格");
            return;
        }

        //重量检测不合格
        double weight = Double.parseDouble(bcrDataDTO.getWeightInspect());
        if (weight > properties.getLimitWeight()) {
            this.exitContainer(address, containerNo);
            logger.info("重量不合格{}>{}", weight, properties.getLimitWeight());
            return;
        }
        if ("BCR0209".equals(point) || "BCR0210".equals(point)){
            //此类型为借道
            pathSchedulingService.inboundTask(containerNo,containerNo,point.getPointArea(),point.getPointId(),
                    "WCS052");
        }else {
            //查询是否存在入库任务
            List<WmsInboundTask> wareHousings = wareHousingService.getWareHousingByContainer(containerNo);
            if (wareHousings.size() > 0) {

                String target = null;
                if (ConstantEnum.BCR_TYPE_XKRK==point.getPointType()) {
                    target = "SAS01";
                } else if (ConstantEnum.BCR_TYPE_LKRK == point.getPointType()) {
                    //先分配堆垛机
                    target = containerPathTaskService.computeAreaIn();
                } else {
                    target = "MCS05";
                }
                //先找入库点位
                //调用入库方法
                pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), address, target);
                // 生成库存
                createContainerInfo(wareHousings.get(0));
            }
        }
    }



    /**
     * 生成库存
     * @param wareHousing 入库任务
     */
    private void createContainerInfo(WmsInboundTask wareHousing) {
        // 生成container_store
        ContainerStore containerStore = new ContainerStore();
        containerStore.setContainerNo(wareHousing.getContainerNo());
        containerStore.setContainerType("1");
        containerStore.setTaskType(10);
        containerStore.setTaskStatus(10);
        containerStore.setWorkCount(0);
        containerStore.setGoodsId(Integer.valueOf(wareHousing.getGoodsId()));
        containerStore.setQty(wareHousing.getQty());
        containerStore.setCreateTime(new Date());
        containerStore.setUpdateTime(new Date());
        containerStore.setTaskType(ContainerStore.TASK_TYPE_INBOUND);
        containerStoreService.saveContainerStore(containerStore);
    }

    /**
     * 料箱进站
     *
     * @param bcrDataDTO bcr实体
     */
    private void inStation(BCRDataDTO bcrDataDTO) throws Exception {
        String containerNo = bcrDataDTO.getContainerNo();
        //箱子所找到的所有的站台
        List<ContainerTaskDto> lineBindingDetails = stationService.getTaskByContainerNo(containerNo);
        if (lineBindingDetails.size()>0){
            String taskId = PrologStringUtils.newGUID();
            //1.找到离入站BCR最近的站台
            Integer stationId = lineBindingDetails.stream().sorted(Comparator.comparing(ContainerTaskDto::getStationId)).collect(Collectors.toList()).get(0).getStationId();
            PointLocation point = pointLocationService.getPointByStationId(stationId);
            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,bcrDataDTO.getAddress(),point.getPointId(),containerNo,5);
            wcsService.lineMove(wcsLineMoveDto);
        }

    }

    /**
     * 判断箱子是否继续
     *
     * @param bcrDataDTO bcr实体
     */
    private void checkGoOn(BCRDataDTO bcrDataDTO) throws Exception {
        //1.此BCR 只需要判断 箱子是不是应该回库
        String containerNo = bcrDataDTO.getContainerNo();
        List<ContainerTaskDto> lineBindingDetails = stationService.getTaskByContainerNo(containerNo);
        if (lineBindingDetails.size()>0){
            String taskId = PrologStringUtils.newGUID();
            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,bcrDataDTO.getAddress(),"",containerNo,
                    5);
            wcsService.lineMove(wcsLineMoveDto);
        }else{
            PointLocation point = pointLocationService.getPointByPointId(bcrDataDTO.getAddress());
            //回库
            pathSchedulingService.inboundTask(containerNo,containerNo,point.getPointArea(),point.getPointId(),"SAS01");
            containerStoreService.updateTaskStausByContainer(containerNo,ContainerStore.TASK_TYPE_INBOUND);
        }
    }

    /**
     * 剔除
     */
    private void exitContainer(String address, String containerNo) throws Exception {
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(PrologStringUtils.newGUID(),address,"-1",
                containerNo, 5);
        wcsService.lineMove(wcsLineMoveDto);
    }

}
