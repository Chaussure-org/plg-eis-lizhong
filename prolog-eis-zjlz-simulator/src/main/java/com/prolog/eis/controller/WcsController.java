package com.prolog.eis.controller;

import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.service.WcsService;
import com.prolog.eis.utils.CacheListUtils;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 10:01
 */
@RestController
@ApiModel("wcs模拟接口")
@RequestMapping("/eis")
public class WcsController {

    @Autowired
    private WcsService wcsService;
    @ApiOperation(value = "输送线行走")
    @RequestMapping("/lineMove")
    public RestMessage<String> lineMove(@RequestBody WcsLineMoveDto wcsLineMoveDto) {
        new Thread(()->{
            wcsService.doCallBack(wcsLineMoveDto);
        }).start();
        return RestMessage.newInstance(true, "200", "操作成功", null);
    }

}
