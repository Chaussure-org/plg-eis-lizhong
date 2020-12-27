package com.prolog.eis.page.service.impl;

import com.github.pagehelper.PageHelper;
import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.page.*;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.eis.inventory.service.IInventoryHistoryService;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.inventory.service.IInventoryTaskService;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.log.service.ILogService;
import com.prolog.eis.mcs.service.IMcsService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.order.service.ISeedInfoService;
import com.prolog.eis.page.service.IPageService;
import com.prolog.eis.sas.service.ISasService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.store.service.IStoreLocationService;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 11:02
 */
@Service
public class PageService implements IPageService {
    @Autowired
    private IStationService stationService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISasService sasService;
    @Autowired
    private IMcsService mcsService;
    @Autowired
    private ILogService logService;
    @Autowired
    private ISeedInfoService seedInfoService;
    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private IOrderDetailService detailService;
    @Autowired
    private IWareHousingService inboundService;
    @Autowired
    private IInventoryTaskService inventoryTaskService;
    @Autowired
    private IInventoryTaskDetailService inventoryTaskDetailService;
    @Autowired
    private IInventoryHistoryService inventoryHistoryService;
    @Autowired
    private IStoreLocationService storeLocationService;
    @Autowired
    private AgvLocationService agvLocationService;

    @Override
    public List<StationInfoVo> findStation() {
        return stationService.queryAll();
    }

    @Override
    public void updateStationInfo(StationInfoDto stationInfoDto) throws Exception {
        stationService.updateStationTaskType(stationInfoDto);
    }

    @Override
    public StationInfoVo findStationById(int stationId) throws Exception {
        return stationService.queryById(stationId);
    }

    @Override
    public Page<ContainerInfoDto> getContainerPage(ContainerQueryDto containerQueryDto) {
        return containerStoreService.queryContainersPage(containerQueryDto);
    }

    @Override
    public Page<GoodsInfoDto> getGoodsPage(GoodsQueryPageDto queryPageDto) {
        return goodsService.getGoodsPage(queryPageDto);
    }

    @Override
    public List<FacilityInfoDto> getFacilityInfo(int facilityType) throws Exception {
        List<FacilityInfoDto> lists = new ArrayList<>();
        switch (facilityType) {
            //小车
            case 1:
                List<CarInfoDTO> carInfo = sasService.getCarInfo();

                for (CarInfoDTO carInfoDTO : carInfo) {
                    FacilityInfoDto facilityInfoDto = new FacilityInfoDto();
                    facilityInfoDto.setFacilityName("小车");
                    facilityInfoDto.setFacilityNo(carInfoDTO.getRgvId());
                    facilityInfoDto.setFacilityLayer(carInfoDTO.getLayer());
                    //1-工作中 2-空闲 3-跨层中 4-故障中
                    if (carInfoDTO.getStatus() == 1) {
                        facilityInfoDto.setFacilityStatus("工作中");
                    } else if (carInfoDTO.getStatus() == 2) {
                        facilityInfoDto.setFacilityStatus("空闲");
                    } else if (carInfoDTO.getStatus() == 3) {
                        facilityInfoDto.setFacilityStatus("跨层中");
                    } else {
                        facilityInfoDto.setFacilityStatus("故障中");
                    }
                    lists.add(facilityInfoDto);
                }
                break;
            case 2:
                //提升机
                List<HoisterInfoDto> hoisterInfoDto = sasService.getHoisterInfoDto();
                for (HoisterInfoDto infoDto : hoisterInfoDto) {
                    FacilityInfoDto facilityInfoDto = new FacilityInfoDto();
                    facilityInfoDto.setFacilityName("提升机");
                    facilityInfoDto.setFacilityNo(infoDto.getHoist());
                    facilityInfoDto.setFacilityLayer(0);
                    if (infoDto.getStatus() == 0) {
                        facilityInfoDto.setFacilityStatus("正常");
                    } else if (infoDto.getStatus() == 1) {
                        facilityInfoDto.setFacilityStatus("可用");
                    }
                    lists.add(facilityInfoDto);
                }
                break;
            case 3:
                //堆垛机
                List<McsCarInfoDto> mcsCarInfo = mcsService.getMcsCarInfo();
                for (McsCarInfoDto mcsCarInfoDto : mcsCarInfo) {
                    FacilityInfoDto facilityInfoDto = new FacilityInfoDto();
                    facilityInfoDto.setFacilityName("堆垛机");
                    facilityInfoDto.setFacilityNo(mcsCarInfoDto.getStackerId());
                    facilityInfoDto.setFacilityLayer(1);
                    if (mcsCarInfoDto.getStatus() == 0) {
                        facilityInfoDto.setFacilityStatus("正常");
                    } else if (mcsCarInfoDto.getStatus() == 1) {
                        facilityInfoDto.setFacilityStatus("可用");
                    }
                    lists.add(facilityInfoDto);

                }
                break;
            default:
                throw new Exception("参数错误无对应设备类型");
        }
        return lists;
    }

    @Override
    public Page<LogInfoDto> getLogPage(LogQueryDto queryDto) throws Exception {
//        1,WMS = 1   2,WCS = 2   3,SAS = 3   4,MCS = 4    5,RCS = 5
        String tableName = null;
        String systemType = "";
        switch (queryDto.getLogType()){
            case LogDto.WMS:
                tableName = "wms_log";
                systemType = "WMS";
                break;
            case LogDto.WCS:
                tableName = "wcs_log";
                systemType = "WCS";
                break;
            case LogDto.SAS:
                tableName = "sas_log";
                systemType = "SAS";
                break;
            case LogDto.MCS:
                tableName = "mcs_log";
                systemType = "MCS";
                break;
            case LogDto.RCS:
                tableName = "rcs_log";
                systemType = "RCS";
                break;
            default:
                throw new Exception("日志类型【"+queryDto.getLogType()+"】不符");

        }
        PageHelper.startPage(queryDto.getPageNum(),queryDto.getPageSize());
        List<LogInfoDto> logPage = logService.getLogPage(queryDto,tableName,systemType);
        Page<LogInfoDto> page = PageUtils.getPage(logPage);
        return page;
    }

    @Override
    public Page<PickingPrintDto> getPickingPrintPage(PickingPrintQueryDto printDto) {
        return seedInfoService.getPrintPage(printDto);
    }

    @Override
    public void seedPrint(SeedPrintDto seedPrintDto) {
        //todo：
    }

    @Override
    public Page<OrderInfoDto> getOrderPage(OrderQueryDto orderQueryDto) {
        return orderBillService.getOrderPage(orderQueryDto);
    }

    @Override
    public List<OrderDetailInfoDto> getOrderDetail(int orderId) {
        return detailService.getOrderDetail(orderId);
    }

    @Override
    public Page<WmsInboundInfoDto> getInboundPage(InboundQueryDto inboundQueryDto) {
        return inboundService.getInboundPage(inboundQueryDto);
    }

    @Override
    public Page<InventoryInfoDto> getInventoryPage(InventoryQueryDto inboundQueryDto) {
        return inventoryTaskService.getInventoryPage(inboundQueryDto);
    }

    @Override
    public List<InventoryDetailInfoDto> getInventoryDetail(int inventoryId) {
        return inventoryTaskDetailService.getInventoryDetail(inventoryId);
    }

    @Override
    public Page<InventoryHistoryDto> getInventoryHistoryPage(InventoryHistoryQueryDto inventoryQueryDto) {
        return inventoryHistoryService.getInventoryHistoryPage(inventoryQueryDto);
    }

    @Override
    public Page<StoreInfoDto> getBxoStorePage(StoreInfoQueryDto storeQueryDto) {
        return storeLocationService.getBoxStorePage(storeQueryDto);
    }

    @Override
    public void updateBoxGroupLock(String groupNo,int isLock) throws Exception {
        storeLocationService.updateGroupLock(groupNo,isLock);
    }

    @Override
    public Page<StoreInfoDto> getTrayStorePage(StoreInfoQueryDto storeQueryDto) {
        return storeLocationService.getTrayStorePage(storeQueryDto);
    }

    @Override
    public void updateTrayGroupLock(String groupNo,int isLock) throws Exception {
        storeLocationService.updateGroupLock(groupNo,isLock);
    }

    @Override
    public Page<AgvStoreInfoDto> getAgvStorePage(AgvStoreQueryDto agvQueryDto) {
        return agvLocationService.getAgvStorePage(agvQueryDto);
    }

    @Override
    public void updateAgvStoreLock(int agvStoreId, int storagelock) throws Exception {
        agvLocationService.updateStoreLock(agvStoreId,storagelock);
    }
}
