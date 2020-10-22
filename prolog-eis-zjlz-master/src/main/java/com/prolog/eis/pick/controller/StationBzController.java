package com.prolog.eis.pick.controller;

import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @RequestMapping("/init")
    public RestMessage<BCPPcikingDTO> beginPicking(@RequestParam(defaultValue = "0") int stationId,@RequestParam String containerNo,@RequestParam String orderBoxNo) throws Exception {
        BCPPcikingDTO bcpPcikingDTO = stationBZService.startBZPicking(stationId, containerNo, orderBoxNo);
        return RestMessage.newInstance(true,"200","查询成功",bcpPcikingDTO);
    }

}
