package com.prolog.eis.wcs.service.impl;


import com.alibaba.druid.sql.visitor.functions.If;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.engin.service.IInventoryBoxOutService;
import com.prolog.eis.enums.BcrPointEnum;
import com.prolog.eis.enums.BranchTypeEnum;
import com.prolog.eis.enums.ConstantEnum;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IContainerPathTaskDetailService;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.Assert;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
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

import java.util.*;
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
    private IContainerBindingDetailService containerBindingDetailService;
    @Autowired
    private IStationBZService stationBZService;

    @Autowired
    private IContainerPathTaskDetailService containerPathTaskDetailService;

    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;


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
                //入库类型
                case ConstantEnum.TYPE_IN:
                    this.inboundTaskCallback(bcrDataDTO);
                    break;
                //出库类型
                case ConstantEnum.TYPE_OUT:
                    this.outBcrCallBack(bcrDataDTO);
                    break;
                //进站
                case ConstantEnum.TYPE_STATION:
                    this.inStation(bcrDataDTO);
                    break;
                default:
                    throw new Exception("没有找到该类型");
            }
            return success;
        } catch (Exception e) {
            if ("BCR0102".equals(bcrDataDTO.getAddress()) || "BCR0103".equals(bcrDataDTO.getAddress())) {
                return RestMessage.newInstance(false, "300", "托盘异常" + e.getMessage(), null);
            }
            if ("BCR0101".equals(bcrDataDTO.getAddress())) {
                return RestMessage.newInstance(false, "300", "托盘异常" + e.getMessage(), null);
            } else {
                return faliure;
            }
        }
    }

    @Override
    @LogInfo(desci = "wcs拆盘机入口任务回告", direction = "wcs->eis", type = LogDto.WCS_TYPE_OPEN_DISK_IN, systemType = LogDto.WCS)
    public RestMessage<String> openDiskEntranceCallback(OpenDiskDto openDiskDto) {
        if (openDiskDto == null) {
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
        if (openDiskDto == null) {
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

    @Override
    @LogInfo(desci = "wcs拣选站料箱放行", direction = "wcs->eis", type = LogDto.WCS_TYPE_CONTAINER_LEAVE, systemType = LogDto.WCS)
    public RestMessage<String> containerLeave(ContainerLeaveDto containerLeaveDto) {
        if (containerLeaveDto == null) {
            return faliure;
        }
        try {
            this.stationContainerLeave(containerLeaveDto);
            return success;
        } catch (Exception e) {
            logger.warn("wcs拣选站料箱放行失败", e);
            return faliure;
        }
    }

    /**
     * 拆盘机出口agv接驳口回告
     *
     * @param openDiskDto
     */
    private void openDiskOut(OpenDiskFinishDto openDiskDto) throws Exception {
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("deviceNo", openDiskDto.getDeviceId()).
                put("openDiskId", OpenDisk.OPEN_DISK_OUT).getMap());
        OpenDisk openDisk = openDisks.get(0);
        if (openDisks.size() == 0) {
            throw new Exception("【" + openDiskDto.getDeviceId() + "】拆盘机点位没有被管理");
        }
        if (openDiskDto.getIsArrive().equals("1")) {
            openDisk.setTaskStatus(1);
            openDiskService.updateOpenDisk(openDisk);
        }
    }

    /**
     * 拆盘机入口回告
     *
     * @param openDiskDto
     */
    public void openDiskIn(OpenDiskDto openDiskDto) throws Exception {
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("deviceNo", openDiskDto.getDeviceId()).
                put("openDiskId", OpenDisk.OPEN_DISK_IN).getMap());
        OpenDisk openDisk = openDisks.get(0);
        if (openDisks.size() == 0) {
            throw new Exception("【" + openDiskDto.getDeviceId() + "】拆盘机点位没有被管理");
        }
        if (openDiskDto.getStatus().equals("0")) {
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

        List<PointLocation> pointLocations = pointLocationService.getPointByType(20);
        List<PointLocation> collect = pointLocations.stream().filter(x -> x.getPointId().equals(taskCallbackDTO.getAddress())).collect(Collectors.toList());
        if (collect.size() > 0) {
            //判断 点位 属于站台料箱到拣选站,则将箱号写入到
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
        } else {
            //判断在路径中是否存在任务
            List<ContainerPathTaskDetail> containerPathTaskDetailList
                    = containerPathTaskDetailService.getTaskDetailByTaskId(taskCallbackDTO.getTaskId());
            if (containerPathTaskDetailList != null && containerPathTaskDetailList.size() > 0) {
                ContainerPathTask containerPathTask = containerPathTaskService.getContainerPathTask(containerPathTaskDetailList.get(0));
                //如果第一条路径不等于终点区域,则更新第一条路径
                if (!containerPathTask.getSourceLocation().equals(containerPathTask.getTargetArea())) {
                    containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetailList.get(0),
                            containerPathTask, PrologDateUtils.parseObject(new Date()));
                }
            } else {
                throw new Exception("此任务不存在");
            }
        }
    }

    /**
     * 出库 类型 BCR 请求
     *
     * @param bcrDataDTO
     * @throws Exception
     */
    private void outBcrCallBack(BCRDataDTO bcrDataDTO) throws Exception {
        String containerNo = bcrDataDTO.getContainerNo();
        String address = bcrDataDTO.getAddress();
        PointLocation point = pointLocationService.getPointByPointId(address);
        if (point == null) {
            throw new RuntimeException("找不到入口点位");
        }
        //二楼出库 BCR 回告
        if (ConstantEnum.secondOutBcrs.contains(address)) {
            String mcsPoint = BcrPointEnum.findMcsPoint(address);
            List<ContainerPathTaskDetail> list = containerPathTaskDetailMapper.findByMap(
                    MapUtils.put("sourceLocation", mcsPoint).put("containerNo", containerNo).getMap(), ContainerPathTaskDetail.class);
            Assert.notEmpty(list, "无此任务路径明细");
            ContainerPathTask containerPathTask = containerPathTaskService.getContainerPathTask(list.get(0));
            containerPathTaskService.updateNextContainerPathTaskDetail(list.get(0),
                    containerPathTask, PrologDateUtils.parseObject(new Date()));
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
        //上报BCR
        String address = bcrDataDTO.getAddress();
        PointLocation point = pointLocationService.getPointByPointId(address);
        if (point == null) {
            throw new Exception("找不到入口点位");
        }

        //查询入库 任务
        List<WmsInboundTask> wareHousings = wareHousingService.getWareHousingByContainer(containerNo);
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

        // 一楼 堆垛机库区 入库 BCR请求
        if ("BCR0102".equals(address) || "BCR0103".equals(address)) {
            Assert.notEmpty(wareHousings, "未查到入库任务" + containerNo);
            if (!BranchTypeEnum.LTK.getWmsBranchType().equals(wareHousings.get(0).getBranchType())) {
                throw new Exception("入库输送线有误，请核对");
            }
            String target = "MCS04"; //= containerPathTaskService.computeAreaIn();
            Assert.notEmpty(target, "一楼入库堆垛机库区，未找到库区");
            pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), address, target);
            createContainerInfo(wareHousings.get(0));
        }

        //一楼 穿梭车库 入库 BCR请求
        if ("BCR0101".equals(address)) {
            Assert.notEmpty(wareHousings, "未查到入库任务" + containerNo);
            if (!BranchTypeEnum.XSK.getWmsBranchType().equals(wareHousings.get(0).getBranchType())) {
                throw new Exception("入库输送线有误，请核对");
            }

            pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), address, StoreArea.SAS01);
            createContainerInfo(wareHousings.get(0));
        }

        //二楼 成品库 BCR请求
        if ("BCR0209".equals(address) || "BCR0210".equals(address)) {
            if (wareHousings.size() == 0) {
                // 成品库借道任务 请求
                pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), point.getPointId(), "WCS052");
            } else {
                //成品库 入库任务请求
                pathSchedulingService.inboundTask(containerNo, containerNo, point.getPointArea(), address, StoreArea.MCS05);
                createContainerInfo(wareHousings.get(0));
            }
        }
        //二楼 循环线Bcr 请求
        //this.checkGoOn(bcrDataDTO);

        //二楼 入库BCR 请求
        if (ConstantEnum.secondInBcrs.contains(address)) {
            /**
             * BCR 请求 判断 此容器是否可以进行 行走任务 下发。
             */

            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto("12345",
                    "RTM0208",
                    "RTM0207", "8888", 5);
            RestMessage<String> result = wcsService.lineMove(wcsLineMoveDto, 0);
            return;
            /*String rcsPoint = BcrPointEnum.findRcsPoint(address);
            List<ContainerPathTaskDetail> list = containerPathTaskDetailMapper.findByMap(
                    MapUtils.put("sourceLocation", rcsPoint).put("containerNo", containerNo).getMap(), ContainerPathTaskDetail.class);
            Assert.notEmpty(list, "无此任务路径明细");
            ContainerPathTask containerPathTask = containerPathTaskService.getContainerPathTask(list.get(0));
            containerPathTaskService.updateNextContainerPathTaskDetail(list.get(0),
                    containerPathTask, PrologDateUtils.parseObject(new Date()));*/

        }

    }

    /**
     * 生成库存
     *
     * @param wareHousing 入库任务
     */
    private void createContainerInfo(WmsInboundTask wareHousing) {
        // GoodsInfo containerStockInfo = containerStoreService.findByMap(MapUtils.put("",1))
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
                inventoryBoxOutService.inventoryAllotStation(containerNo, bcrDataDTO.getAddress());
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

    /**
     * 拣选站料箱按钮放行处理逻辑
     *
     * @param containerLeaveDto
     */
    private void stationContainerLeave(ContainerLeaveDto containerLeaveDto) throws Exception {
        List<Station> stations = stationService.findStationByMap(MapUtils.put("id", Integer.parseInt(containerLeaveDto.getDeviceId())).getMap());
        if (stations.size() == 0) {
            throw new Exception("拣选站编号【" + containerLeaveDto.getDeviceId() + "】EIS管理");
        }
        Station station = stations.get(0);
        //校验播种是否完成
        //判断当容器是否还有绑定明细
        List<Integer> stationIds = containerBindingDetailService.getContainerBindingToStation(containerLeaveDto.getContainerNo());
        if (stationIds.contains(station.getId())) {
            throw new Exception("容器【" + containerLeaveDto.getContainerNo() + "】播种未完成，放行失败");
        }
        if (stationIds.size() == 0) {
            //放行回库
            String taskId = PrologStringUtils.newGUID();
            PointLocation point = pointLocationService.getPointByStationId(station.getId());
            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId, point.getPointId(), PointLocation.POINT_ID_LXHK, containerLeaveDto.getContainerNo(), 5);
            wcsService.lineMove(wcsLineMoveDto, 0);
        } else {
            //计算合适拣选站，发往
            int targetStationId = stationBZService.computeContainerTargetStation(stationIds, station.getId());
            //上层输送线 发送点位
            String taskId = PrologStringUtils.newGUID();
            PointLocation point = pointLocationService.getPointByStationId(station.getId());
            PointLocation targetPoint = pointLocationService.getPointByStationId(targetStationId);
            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId, point.getPointId(), targetPoint.getPointId(), containerLeaveDto.getContainerNo(), 5);
            wcsService.lineMove(wcsLineMoveDto, 0);
        }

    }

}
