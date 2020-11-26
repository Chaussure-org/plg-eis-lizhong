package com.prolog.eis.page.controller;

import com.prolog.eis.dto.page.*;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.page.service.IPageService;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.core.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/9 9:25
 */
@RestController
@Api(tags = "通用页面")
@RequestMapping("api/v1/master")
public class PageController {
    @Autowired
    private IPageService pageService;

    @RequestMapping("/station/findAll")
    @ApiOperation(value = "查询所有站台信息", notes = "查询所有站台信息")
    public RestMessage<List<StationInfoVo>> findStation() {
        List<StationInfoVo> stations = null;
        try {
            stations = pageService.findStation();
            return RestMessage.newInstance(true, "200", "查询成功", stations);
        } catch (Exception e) {
            return RestMessage.newInstance(false, "500", "查询失败:" + e.getMessage(), null);

        }
    }
    @RequestMapping("/station/findById")
    @ApiOperation(value = "根据站台id查看站台信息",notes = "根据站台id查看站台信息")
    public RestMessage<StationInfoVo> updateStation(@ApiParam(name = "stationId",value = "站台id",required = true)int stationId) throws Exception {
        try {
            StationInfoVo station = pageService.findStationById(stationId);
            return RestMessage.newInstance(true,"200","查询成功",station);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败"+e.getMessage(),null);
        }
    }
    @RequestMapping("/station/updateType")
    @ApiOperation(value = "根据站台id修改站台信息",notes = "根据站台id修改站台信息")
    public RestMessage<String> updateStation(@RequestBody @Validated StationInfoDto stationInfoDto) throws Exception {
        try {
            pageService.updateStationInfo(stationInfoDto);
            return RestMessage.newInstance(true,"200","修改成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","修改失败:"+e.getMessage(),null);
        }

    }

    @RequestMapping("/container/page")
    @ApiOperation(value = "容器资料分页查询",notes = "容器资料分页查询")
    public RestMessage<Page<ContainerInfoDto>> containerPage(@RequestBody @Validated ContainerQueryDto containerQueryDto) throws Exception {
        try {
            Page<ContainerInfoDto> containerPage = pageService.getContainerPage(containerQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",containerPage);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }

    }
    @RequestMapping("/goods/page")
    @ApiOperation(value = "商品资料分页查询",notes = "商品资料分页查询")
    public RestMessage<Page<GoodsInfoDto>> goodsPage(@RequestBody @Validated GoodsQueryPageDto goodsQueryPageDto){
        try {
            Page<GoodsInfoDto> goodsPage = pageService.getGoodsPage(goodsQueryPageDto);
            return RestMessage.newInstance(true,"200","查询成功",goodsPage);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/facility/page")
    @ApiOperation(value = "设备状态查询",notes = "设备状态查询")
    public RestMessage<List<FacilityInfoDto>> facilityPage(@RequestParam(defaultValue = "1") int facilityType){
        try {
            List<FacilityInfoDto> facilityInfo = pageService.getFacilityInfo(facilityType);
            return RestMessage.newInstance(true,"200","查询成功",facilityInfo);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/log/page")
    @ApiOperation(value = "交互日志分页查询",notes = "交互日志分页查询")
    public RestMessage<Page<LogInfoDto>> LogPage(@RequestBody @Validated LogQueryDto logQueryDto){
        try {
            Page<LogInfoDto> page = pageService.getLogPage(logQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/picking/page")
    @ApiOperation(value = "派工单分页查询",notes = "派工单分页查询")
    public RestMessage<Page<PickingPrintDto>> pickingPage(@RequestBody @Validated PickingPrintQueryDto printQueryDto){
        try {
            Page<PickingPrintDto> page = pageService.getPickingPrintPage(printQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/picking/print")
    @ApiOperation(value = "派工单打印",notes = "派工单打印")
    public RestMessage<String> pickingPage(@RequestBody @Validated SeedPrintDto seedPrintDto){
        try {
            pageService.seedPrint(seedPrintDto);
            return RestMessage.newInstance(true,"200","打印成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/order/page")
    @ApiOperation(value = "订单汇总分页查询",notes = "订单汇总分页查询")
    public RestMessage<Page<OrderInfoDto>> orderPage(@RequestBody @Validated OrderQueryDto queryDto){
        try {
            Page<OrderInfoDto> orderPage = pageService.getOrderPage(queryDto);
            return RestMessage.newInstance(true,"200","查询成功",orderPage);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/order/detail")
    @ApiOperation(value = "订单明细查询",notes = "订单明细查询")
    public RestMessage<List<OrderDetailInfoDto>> orderDetailInfo(@RequestParam(defaultValue = "0")int orderId){
        try {
            List<OrderDetailInfoDto> orderDetails = pageService.getOrderDetail(orderId);
            return RestMessage.newInstance(true,"200","查询成功",orderDetails);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/inbound/page")
    @ApiOperation(value = "wms入库任务分页查询",notes = "wms入库任务分页查询")
    public RestMessage<Page<WmsInboundInfoDto>> wmsInboundPage(@RequestBody @Validated InboundQueryDto inboundQueryDto){
        try {
            Page<WmsInboundInfoDto> page = pageService.getInboundPage(inboundQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/inventory/page")
    @ApiOperation(value = "盘点计划分页查询",notes = "盘点计划分页查询")
    public RestMessage<Page<InventoryInfoDto>> inventoryPage(@RequestBody @Validated InventoryQueryDto inboundQueryDto){
        try {
            Page<InventoryInfoDto> page = pageService.getInventoryPage(inboundQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/inventory/detail")
    @ApiOperation(value = "盘点明细查询",notes = "盘点明细查询")
    public RestMessage<List<InventoryDetailInfoDto>> inventoryDetailInfo(@RequestParam(defaultValue = "0") @Validated int id){
        try {
            List<InventoryDetailInfoDto> page = pageService.getInventoryDetail(id);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }
    @RequestMapping("/inventory/history/page")
    @ApiOperation(value = "盘点历史分页查询",notes = "盘点历史分页查询")
    public RestMessage<Page<InventoryHistoryDto>> inventoryHistoryPage(@RequestBody @Validated InventoryHistoryQueryDto inventoryQueryDto){
       try {
            Page<InventoryHistoryDto> page = pageService.getInventoryHistoryPage(inventoryQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/box/store/page")
    @ApiOperation(value = "箱库货位信息分析查询",notes = "箱库货位信息分析查询")
    public RestMessage<Page<StoreInfoDto>> boxStorePage(@RequestBody @Validated StoreInfoQueryDto storeQueryDto){
        try {
            Page<StoreInfoDto> page = pageService.getBxoStorePage(storeQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/box/store/update")
    @ApiOperation(value = "箱库异常货位组解锁",notes = "箱库异常货位组解锁")
    public RestMessage<String> updateBoxGroup(@RequestParam(defaultValue = "001")String groupNo,@RequestParam(defaultValue = "-1")int isLock){
        try {
             pageService.updateBoxGroupLock(groupNo,isLock);
            return RestMessage.newInstance(true,"200","修改成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","修改失败:"+e.getMessage(),null);
        }
    }

    @RequestMapping("/tray/store/page")
    @ApiOperation(value = "立库货位信息分析查询",notes = "立库货位信息分析查询")
    public RestMessage<Page<StoreInfoDto>> trayStorePage(@RequestBody @Validated StoreInfoQueryDto storeQueryDto){
        try {
            Page<StoreInfoDto> page = pageService.getTrayStorePage(storeQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/tray/store/update")
    @ApiOperation(value = "箱库异常货位组解锁",notes = "箱库异常货位组解锁")
    public RestMessage<String> updateTrayGroup(@RequestParam(defaultValue = "001")String groupNo,@RequestParam(defaultValue = "-1")int isLock){
        try {
            pageService.updateTrayGroupLock(groupNo,isLock);
            return RestMessage.newInstance(true,"200","修改成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","修改失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/agv/store/page")
    @ApiOperation(value = "agv区域货位信息查询",notes = "agv区域货位信息查询")
    public RestMessage<Page<AgvStoreInfoDto>> agvStorePage(@RequestBody @Validated AgvStoreQueryDto agvQueryDto){
        try {
            Page<AgvStoreInfoDto> page = pageService.getAgvStorePage(agvQueryDto);
            return RestMessage.newInstance(true,"200","查询成功",page);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }
    }


    @RequestMapping("/agv/store/update")
    @ApiOperation(value = "agv区域货位锁定修改",notes = "agv区域货位锁定修改")
    public RestMessage<String> updateAgvStoreLock(@RequestParam(defaultValue = "0") int agvStoreId,
                                                                 @RequestParam(defaultValue = "-1")int storagelock){
        try {
            pageService.updateAgvStoreLock(agvStoreId,storagelock);
            return RestMessage.newInstance(true,"200","修改成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","修改失败:"+e.getMessage(),null);
        }
    }


}
