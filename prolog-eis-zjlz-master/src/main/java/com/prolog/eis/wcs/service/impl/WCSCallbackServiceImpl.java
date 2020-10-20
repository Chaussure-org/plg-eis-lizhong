package com.prolog.eis.wcs.service.impl;


import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.enums.ConstantEnum;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wcs.service.IWCSCallbackService;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.framework.common.message.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                case ConstantEnum.TASK_TYPE_RKK:
                    this.inboundTaskCallback(bcrDataDTO);
                    break;
                case ConstantEnum.TASK_TYPE_LXJZ:
                    this.inStation(bcrDataDTO);
                    break;
                case ConstantEnum.TASK_TYPE_TO_AGV:
                    this.checkGoOn(bcrDataDTO);
                    break;
                case ConstantEnum.TASK_TYPE_BORROW:
                    this.borrowOutBound(bcrDataDTO);
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

    @Override
    public RestMessage<String> executeBoxArriveCallback(BoxCallbackDTO boxCallbackDTO) {
        return null;
    }

    @Override
    public RestMessage<String> executeCompleteBoxCallback(BoxCompletedDTO boxCompletedDTO) {
        return null;
    }

    @Override
    public RestMessage<String> executeLightCallback(LightDTO lightDTO) {
        return null;
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
     * 外形检测结果处理
     *
     * @param bcrDataDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void shapeInspect(BCRDataDTO bcrDataDTO) throws Exception {

    }

    /**
     * 入库任务回告
     *
     * @param taskCallbackDTO
     */
    @Transactional(rollbackFor = Exception.class, timeout = 600)
    public void doInboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

    /**
     * 换层任务回告
     *
     * @param taskCallbackDTO
     */
    private void doHcTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

    /**
     * 入库任务
     *
     * @param bcrDataDTO
     * @throws Exception
     */
    private void inboundTaskCallback(BCRDataDTO bcrDataDTO) throws Exception {
        String containerNo = bcrDataDTO.getContainerNo();

        List<PointLocation> pointLocations = pointLocationService.getPointByType(PointLocation.POINT_TYPE_IN_BCR);

        if (pointLocations.size() == 0) {
            throw new RuntimeException("找不到入口点位");
        }
        String address = pointLocations.get(0).getPointId();

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

        //查询是否存在入库任务
        List<WmsInboundTask> wareHousingByContainer = wareHousingService.getWareHousingByContainer(containerNo);
        if (wareHousingByContainer.size() > 0) {
            //先找入库点位
            //调用入库方法
            pathSchedulingService.inboundTask(containerNo, containerNo, "c", "BCR0101", "A");
        }
    }

    /**
     * 料箱进站
     *
     * @param bcrDataDTO bcr实体
     */
    private void inStation(BCRDataDTO bcrDataDTO) {
        //找到所有站台判定的拣选单
        //通过拣选单找到订单
        //通过订单判断这个箱子是否需要去拣选站
        String containerNo = bcrDataDTO.getContainerNo();

        List<ContainerTaskDto> lineBindingDetails = stationService.getTaskByContainerNo(containerNo);
        if (lineBindingDetails.size()>0){
            String taskId = PrologStringUtils.newGUID();

            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,bcrDataDTO.getAddress(),"",containerNo,
                    ConstantEnum.TASK_TYPE_XZ);
            wcsService.lineMove(wcsLineMoveDto);
        }


    }

    /**
     * 判断箱子是否继续
     *
     * @param bcrDataDTO bcr实体
     */
    private void checkGoOn(BCRDataDTO bcrDataDTO) {
    }


    /**
     * 借道出库(成品库借道)
     *
     * @param bcrDataDTO bcr实体
     */
    private void borrowOutBound(BCRDataDTO bcrDataDTO) {
    }

    /**
     * 剔除
     */
    private void exitContainer(String address, String containerNo) {
//        List<PointLocation> ep = pointService.getPointByStation(0,PointLocation.TYPE_EXCEPTION_CONTAINER);
//        if(ep.size()==0)
//            throw new RuntimeException("找不到异常剔除口点位");
//        String target = ep.get(0).getPointId();
//        WCSCommand wcsCommand = new WCSCommand(TaskUtils.gerenateTaskId(),Constant.COMMAND_TYPE_XZ,address,target,
//        containerNo);
//        commandService.add(wcsCommand);
    }

}
