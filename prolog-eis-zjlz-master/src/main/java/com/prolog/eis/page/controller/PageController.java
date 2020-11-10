package com.prolog.eis.page.controller;

import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.page.service.IPageService;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "站台管理页面")
@RequestMapping("master")
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
            return RestMessage.newInstance(false, "500", "查询失败:" + e.toString(), null);

        }
    }
    @RequestMapping("/station/findById")
    @ApiOperation(value = "根据站台id查看站台信息",notes = "根据站台id查看站台信息")
    public RestMessage<StationInfoVo> updateStation(@RequestParam(defaultValue = "0")int stationId) throws Exception {
        try {
            StationInfoVo station = pageService.findStationById(stationId);
            return RestMessage.newInstance(true,"200","查询成功",station);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","查询失败"+e.toString(),null);
        }
    }
    @RequestMapping("/station/updateType")
    @ApiOperation(value = "根据站台id修改站台信息",notes = "根据站台id修改站台信息")
    public RestMessage<String> updateStation(@RequestBody @Validated StationInfoDto stationInfoDto) throws Exception {
        try {
            pageService.updateStationInfo(stationInfoDto);
            return RestMessage.newInstance(true,"200","修改成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","修改失败",null);
        }

    }
}
