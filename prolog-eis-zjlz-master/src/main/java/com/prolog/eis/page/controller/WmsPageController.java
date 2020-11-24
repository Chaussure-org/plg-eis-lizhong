package com.prolog.eis.page.controller;

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
@Api(tags = "PDA页面")
@RequestMapping("wms/page")
public class WmsPageController {

    @Autowired
    private IWmsPageService wmsPageService;


    @RequestMapping("/tray/release")
    @ApiOperation(value = "接驳口下架",notes = "托盘下架释放货位")
    public RestMessage<String> storeRelease(@RequestParam() String trayNo,@RequestParam() String transhipNo) throws Exception {
        try {
            wmsPageService.storeRelease(trayNo,transhipNo);
            return RestMessage.newInstance(true,"200","操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","操作失败:"+e.getMessage(),null);
        }

    }

    @RequestMapping("/tray/inbound")
    @ApiOperation(value = "空托上架入库",notes = "空托上架入库")
    public RestMessage<String> updateStation(@RequestParam() String trayNo,@RequestParam() String transhipNo) throws Exception {
        try {
            wmsPageService.emptyTrayPutaway(trayNo,transhipNo);
            return RestMessage.newInstance(true,"200","操作成功",null);
        } catch (Exception e) {
            return RestMessage.newInstance(false,"500","操作失败:"+e.getMessage(),null);
        }

    }

}
