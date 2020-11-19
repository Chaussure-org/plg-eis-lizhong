package com.prolog.eis.wcs.service.impl;


import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.engin.service.IInventoryBoxOutService;
import com.prolog.eis.enums.BranchTypeEnum;
import com.prolog.eis.enums.ConstantEnum;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wcs.service.IOpenDiskService;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.eis.wcs.service.IWcsCallbackService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WcsCallbackServiceImpl implements IWcsCallbackService {

    private final Logger logger = LoggerFactory.getLogger(WcsCallbackServiceImpl.class);

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
    private IWcsService wcsService;

    @Autowired
    private IContainerStoreService containerStoreService;

    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private IInventoryTaskDetailService inventoryTaskDetailService;
    @Autowired
    private IInventoryBoxOutService inventoryBoxOutService;
    @Autowired
    private IOpenDiskService openDiskService;

    @Autowired
    private StationMapper stationMapper;
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
        if (taskCallbackDTO == null) {
            return success;
        }
        try {
            this.doXZTask(taskCallbackDTO);
            return success;
        } catch (Exception e) {
            logger.warn("行走任务回告失败", e);
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
            return faliure;
        }
    }

    @Override
    @LogInfo(desci = "wcs拆盘机入口任务回告", direction = "wcs->eis", type = LogDto.WCS_TYPE_OPEN_DISK_IN, systemType = LogDto.WCS)
    public RestMessage<String> openDiskEntranceCallback(OpenDiskDto openDiskDto) {
        if (openDiskDto == null){
            return success;
        }
        try {
            this.openDiskIn(openDiskDto);
            return success;
        } catch (Exception e) {
            logger.warn("wcs拆盘机入口回告失败", e);
            return faliure;
        }
    }

    @Override
    @LogInfo(desci = "wcs拆盘机出口任务回告", direction = "wcs->eis", type = LogDto.WCS_TYPE_OPEN_DISK_OUT, systemType = LogDto.WCS)
    public RestMessage<String> openDiskOuTCallback(OpenDiskFinishDto openDiskDto) {
        if (openDiskDto == null){
            return success;
        }
        try {
            this.openDiskOut(openDiskDto);
            return success;
        } catch (Exception e) {
            logger.warn("wcs拆盘机出口任务回告失败", e);
            return faliure;
        }
    }

    /**
     * 拆盘机出口agv接驳口回告
     * @param openDiskDto
     */
    private void openDiskOut(OpenDiskFinishDto openDiskDto) throws Exception {
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("deviceNo", openDiskDto.getDeviceId()).
                put("openDiskId", OpenDisk.OPEN_DISK_OUT).getMap());
        OpenDisk openDisk = openDisks.get(0);
        if (openDisks.size() == 0){
            throw new Exception("【"+openDiskDto.getDeviceId()+"】拆盘机点位没有被管理");
        }
        if (openDiskDto.getIsArrive().equals("1")){
            openDisk.setTaskStatus(1);
            openDiskService.updateOpenDisk(openDisk);
        }
    }

    /**
     * 拆盘机入口回告
     * @param openDiskDto
     */
    public void openDiskIn(OpenDiskDto openDiskDto) throws Exception {
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("deviceNo", openDiskDto.getDeviceId()).
                put("openDiskId", OpenDisk.OPEN_DISK_IN).getMap());
        OpenDisk openDisk = openDisks.get(0);
        if (openDisks.size() == 0){
            throw new Exception("【"+openDiskDto.getDeviceId()+"】拆盘机点位没有被管理");
        }
        if (openDiskDto.getStatus().equals("0")){
            openDisk.setTaskStatus(0);
            openDiskService.updateOpenDisk(openDisk);
        }
    }


    /**
     * 行走任务回告,入库点
     *
     * @param taskCallbackDTO
     */
    @Transactional(rollbackFor = Exception.class, timeout = 600)
    public void doXZTask(TaskCallbackDTO taskCallbackDTO) throws Exception {
        //判断 点位属于站台
        List<PointLocation> pointLocations = pointLocationService.getPointByType(20);
        List<PointLocation> collect = pointLocations.stream().filter(x -> x.getPointId().equals(taskCallbackDTO.getAddress())).collect(Collectors.toList());
        if (collect.size() > 0) {
            //料箱到拣选站则将箱号写入到
//            PointLocation pointLocation = pointLocationService.getPointByPointId(taskCallbackDTO.getAddress());
//            if (pointLocation == null){
//                throw new Exception("坐标点位【"+taskCallbackDTO.getAddress()+"】没有被管理");
//            }
            Station station = stationService.findById(collect.get(0).getStationId());
            if (station == null) {
                throw new Exception("【站台" + collect.get(0) + "】不存在");
            }
            station.setContainerNo(taskCallbackDTO.getContainerNo());
            station.setUpdateTime(new Date());
            stationService.updateStation(station);
        }
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
        if ("BCR0209".equals(point) || "BCR0210".equals(point)) {
            //此类型为借道
            pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), point.getPointId(),
                    "WCS052");
        } else {
            //查询是否存在入库任务
            List<WmsInboundTask> wareHousings = wareHousingService.getWareHousingByContainer(containerNo);
            if (wareHousings.size() == 0) {
                throw new Exception("此容器" + containerNo + "没有入库任务");
            }
            String target = null;
            if (ConstantEnum.BCR_TYPE_XKRK == point.getPointType()) {
                if (!BranchTypeEnum.XSK.getWmsBranchType().equals(wareHousings.get(0).getBranchType())) {
                    throw new Exception("入库输送线有误，请核对");
                }
                target = "SAS01";
            } else {
                if (!BranchTypeEnum.LTK.getWmsBranchType().equals(wareHousings.get(0).getBranchType())) {
                    throw new Exception("入库输送线有误，请核对");
                }
                if (ConstantEnum.BCR_TYPE_LKRK == point.getPointType()) {
                    //先分配堆垛机
                    target = containerPathTaskService.computeAreaIn();
                } else {
                    target = "MCS05";
                }
            }
            //先找入库点位
            //调用入库方法
            pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), address, target);
            // 生成库存
            createContainerInfo(wareHousings.get(0));
        }
    }


    /**
     * 生成库存
     *
     * @param wareHousing 入库任务
     */
    private void createContainerInfo(WmsInboundTask wareHousing) {
        // 生成container_store
        ContainerStore containerStore = new ContainerStore();
        containerStore.setContainerNo(wareHousing.getContainerNo());
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
        List<ContainerTaskDto> containerType = stationMapper.findContainerType(containerNo);
        switch (containerType.get(0).getTaskType()) {
            //播种
            case 20:
                //箱子所找到的所有的站台
                List<ContainerTaskDto> lineBindingDetails = stationService.getTaskByContainerNo(containerNo);
                if (lineBindingDetails.size() > 0) {
                    String taskId = PrologStringUtils.newGUID();
                    //1.找到离入站BCR最近的站台
                    Integer stationId = lineBindingDetails.stream().sorted(Comparator.comparing(ContainerTaskDto::getStationId)).collect(Collectors.toList()).get(0).getStationId();
                    PointLocation point = pointLocationService.getPointByStationId(stationId);
                    WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId, bcrDataDTO.getAddress(), point.getPointId(), containerNo, 5);
                    wcsService.lineMove(wcsLineMoveDto, 0);
                }
                break;
            //盘点
            case 21:
                inventoryBoxOutService.inventoryAllotStation(containerNo,bcrDataDTO.getAddress());
                break;
            //移库
            case 22:

                break;
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
        if (lineBindingDetails.size() > 0) {
            String taskId = PrologStringUtils.newGUID();
            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId, bcrDataDTO.getAddress(), "", containerNo,
                    5);
            wcsService.lineMove(wcsLineMoveDto, 0);
        } else {
            PointLocation point = pointLocationService.getPointByPointId(bcrDataDTO.getAddress());
            //回库
            pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), point.getPointId(), "SAS01");
            containerStoreService.updateTaskStausByContainer(containerNo, ContainerStore.TASK_TYPE_INBOUND);
        }
    }

    /**
     * 剔除
     */
    private void exitContainer(String address, String containerNo) throws Exception {
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(PrologStringUtils.newGUID(), address, "-1",
                containerNo, 5);
        wcsService.lineMove(wcsLineMoveDto, 0);
    }

}
