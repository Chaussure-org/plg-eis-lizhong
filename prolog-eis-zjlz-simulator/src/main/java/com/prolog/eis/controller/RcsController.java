package com.prolog.eis.controller;

import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.service.RcsService;
import com.prolog.eis.utils.CacheListUtils;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: wuxl
 * @create: 2020-09-16 15:19
 * @Version: V1.0
 */
@RestController
@Api(tags = "模拟rcs接口")
@RequestMapping("/eis")
public class RcsController {


    @ApiOperation(value = "Rcs移动", notes = "Rcs移动")
    @PostMapping("/agvMove")
    public RestMessage<String> agvMove(@RequestBody RcsTaskDto rcsTaskDto) throws IOException {
        CacheListUtils.getRcslist().add(rcsTaskDto);
        return RestMessage.newInstance(true,"操作成功");
    }
}
