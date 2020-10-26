package com.prolog.eis.station.controller;

import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/26 10:22
 */
@RestController
@Api(tags = "原料拣选站")
@RequestMapping("/api/v1/master/station")
public class StationController {
    @Autowired
    private IStationService stationService;
    @ApiOperation(value = "播种索取订单开关", notes = "播种索取订单开关")
    @RequestMapping(value = "/cut")
    public RestMessage beginPicking(@RequestParam(defaultValue = "0") int stationId, @RequestParam int flag) throws Exception {
        try {
            stationService.changeStationIsLock(stationId,flag);
            return RestMessage.newInstance(true,"200","切换成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","切换失败:"+e.getMessage(),null);
        }
    }

    @ApiModelProperty(value = "获取站台id",notes = "获取站台id")
    @RequestMapping(value = "/getId")
    public RestMessage<Map<String,Integer>> getStationId(HttpServletRequest request) throws Exception{
        try {
            int stationId = stationService.getStationId(request);
            Map<String, Integer> map = new HashMap<>(1);
            map.put("stationId",stationId);

            return RestMessage.newInstance(true,"200","获取成功",map);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","获取失败:"+e.getMessage(),null);
        }
    }





}
