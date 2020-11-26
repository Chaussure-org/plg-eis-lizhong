package com.prolog.eis.page.controller;

import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;
import com.prolog.eis.dto.inventory.InventoryShowDto;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.page.service.IPageService;
import com.prolog.eis.page.service.IWmsPageService;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/19 18:09
 *
 */
@RestController
@Api(tags = "wmsPDA页面")
@RequestMapping("wms/page")
public class WmsPageController {

    @Autowired
    private IWmsPageService wmsPageService;


    @RequestMapping("/tray/release")
    @ApiOperation(value = "接驳口下架",notes = "托盘下架释放货位")
    public RestMessage<String> storeRelease(@RequestParam() String trayNo,@RequestParam() String transhipNo) throws Exception {
        try {
            wmsPageService.storeRelease(trayNo,transhipNo);
            return RestMessage.newInstance(true,"操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }

    @RequestMapping("/inventory/init")
    @ApiOperation(value = "盘点页面初始化",notes = "盘点页面初始化")
    public RestMessage<InventoryShowDto> inventoryInfo(@RequestParam() String containerNo) throws Exception {
        try {
            InventoryShowDto inventoryDetail = wmsPageService.findInventoryDetail(containerNo);
            return RestMessage.newInstance(true,"查询成功",inventoryDetail);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"查询失败:"+e.getMessage(),null);
        }

    }

    @RequestMapping("/inventory/work")
    @ApiOperation(value = "盘点页面执行",notes = "盘点页面执行")
    public RestMessage<String> inventoryWork(@RequestParam() String containerNo,@RequestParam int goodsNum) throws Exception {
        try {
            wmsPageService.doInventoryTask(containerNo,goodsNum);
            return RestMessage.newInstance(true,"查询成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"查询失败:"+e.getMessage(),null);
        }

    }


    @RequestMapping("/tray/inbound")
    @ApiOperation(value = "空托上架入库",notes = "空托上架入库")
    public RestMessage<String> updateStation(@RequestParam() String trayNo,@RequestParam() String transhipNo) throws Exception {
        try {
            wmsPageService.emptyTrayPutaway(trayNo,transhipNo);
            return RestMessage.newInstance(true,"操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }


    @RequestMapping("/station/orderInfo")
    @ApiOperation(value = "成品库订单信息汇总",notes = "成品库订单信息汇总")
    public RestMessage<FinishNotSeedDTO> finishOrderInfo() throws Exception {
        try {
            FinishNotSeedDTO seedCount = wmsPageService.getNotSeedCount();
            return RestMessage.newInstance(true,"操作成功",seedCount);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }


    @RequestMapping("/station/update")
    @ApiOperation(value = "站台开关",notes = "成品库订成品库开始（结束）索取订单单信息汇总")
    public RestMessage<String> changeStationIsLock(@RequestParam() int isLock) throws Exception {
        try {
            wmsPageService.changeStationIsLock(isLock);
            return RestMessage.newInstance(true,"操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }

    @RequestMapping("/station/init")
    @ApiOperation(value = "播种页面初始化",notes = "成品库订成品库开始（结束）索取订单单信息汇总")
    public RestMessage<FinishTrayDTO> seedInfo(@RequestParam() String containerNo) throws Exception {
        try {
            FinishTrayDTO finishSeed = wmsPageService.getFinishSeed(containerNo);
            return RestMessage.newInstance(true,"操作成功",finishSeed);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }


    @RequestMapping("/station/confirm")
    @ApiOperation(value = "成品库播种执行",notes = "成品库播种执行")
    public RestMessage<FinishTrayDTO> seedConfirm(@RequestParam() String containerNo,@RequestParam()int num) throws Exception {
        try {
            wmsPageService.confirmSeed(containerNo,num);
            return RestMessage.newInstance(true,"操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }

}
