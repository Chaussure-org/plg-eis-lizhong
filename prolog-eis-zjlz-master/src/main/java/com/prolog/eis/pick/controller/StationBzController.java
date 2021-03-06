package com.prolog.eis.pick.controller;

import com.prolog.eis.dto.bz.OrderTrayWeighDTO;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 9:37
 */

@RestController
@Api(tags = "半成品原料拣选播种")
@RequestMapping("/api/v1/master/bcp/picking")
public class StationBzController {
    @Autowired
    private IStationBZService stationBZService;
    @ApiOperation(value = "拣选页面推送", notes = "拣选页面推送")
    @RequestMapping(value = "/init")
    public RestMessage<BCPPcikingDTO> beginPicking(@RequestParam int stationId,@RequestParam String containerNo,
                                                   @RequestParam String locationNo,@RequestParam String orderBoxNo) throws Exception {
        BCPPcikingDTO bcpPcikingDTO = null;
        try {
            bcpPcikingDTO = stationBZService.startBZPicking(stationId, containerNo, orderBoxNo,locationNo);
            return RestMessage.newInstance(true,"200","查询成功",bcpPcikingDTO);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败:"+e.getMessage(),null);
        }

    }

    @ApiOperation(value = "拣选确认", notes = "拣选确认")
    @RequestMapping("/confirm")
    public RestMessage<String> pickConfirm(@RequestParam int stationId,@RequestParam String containerNo,@RequestParam String orderBoxNo,@RequestParam(defaultValue = "-1") int completeNum) throws Exception {
        try {
            stationBZService.pickingConfirm(stationId, containerNo, orderBoxNo,completeNum);
            return RestMessage.newInstance(true,"200","操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","操作失败:"+e.getMessage(),null);
        }

    }


    @ApiOperation(value = "拣选完成放行", notes = "拣选完成放行")
    @RequestMapping("/complete")
    public RestMessage<String> pickingComplete(@RequestParam(defaultValue = "0") int stationId,@RequestParam String containerNo,@RequestParam String orderBoxNo,@RequestParam(defaultValue = "0") int orderDetailId) throws Exception {
        try {
            stationBZService.pickingComplete(stationId,containerNo,orderBoxNo,orderDetailId);
            return RestMessage.newInstance(true,"200","操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","操作失败:"+e.getMessage(),null);
        }

    }



    @ApiOperation(value = "称重检测", notes = "称重检测")
    @RequestMapping("/checkWeigh")
    public RestMessage<OrderTrayWeighDTO> checkWeigh(@RequestParam(defaultValue = "0") int stationId, @RequestParam(defaultValue = "0") int orderDetailId,@RequestParam(defaultValue = "0") String passBoxNo) throws Exception {
        try {
            OrderTrayWeighDTO orderTrayWeighDTO = stationBZService.weighCheck(stationId, orderDetailId, passBoxNo);
            return RestMessage.newInstance(true,"200","操作成功",orderTrayWeighDTO);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","操作失败:"+e.getMessage(),null);
        }

    }


    @ApiModelProperty(value = "更换订单框",notes = "更换订单框")
    @RequestMapping("/change")
    public RestMessage changeOrderTray(@RequestParam int stationId,@RequestParam String orderBoxNo){
        try {
            stationBZService.changeOrderTray(orderBoxNo,stationId);
            return RestMessage.newInstance(true,"200","换拖成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","操作失败:"+e.getMessage(),null);
        }
    }


    @ApiOperation(value = "拣选称重操作",notes = "称重检测 一次称重不合格进行二次称重")
    @RequestMapping("/weighWork")
    public RestMessage<OrderTrayWeighDTO> pickWeighWork(@RequestParam int stationId,@RequestParam String containerNo,@RequestParam String orderTrayNo,
                                                        @RequestParam(defaultValue = "0") String passBoxNo,@RequestParam int orderDetailId,@RequestParam int completeNum) {
        try {
            OrderTrayWeighDTO orderTrayWeighDTO = stationBZService.pickingWeighWork(stationId, containerNo, orderTrayNo, passBoxNo, orderDetailId, completeNum);
            return RestMessage.newInstance(true,"200","操作成功",orderTrayWeighDTO);
        } catch (Exception e) {
            return RestMessage.newInstance(false, "500", "操作失败:" + e.getMessage(), null);
        }
    }

}
