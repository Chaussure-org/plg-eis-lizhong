package com.prolog.eis.mcs.controller;

import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.dto.mcs.InBoundRequestDto;
import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.dto.mcs.McsRequestTaskDto;
import com.prolog.eis.dto.mcs.TaskReturnInBoundRequestResponseDto;
import com.prolog.eis.mcs.service.IMCSCallBackService;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:53
 */
@RestController
@RequestMapping("/mcs")
@Api(tags = "MCS回调接口(堆垛机)")
public class McsController {

    private static final Logger logger = LoggerFactory.getLogger(McsController.class);

    @Autowired
    private IMCSCallBackService mcsCallbackService;

    @ApiOperation(value = "堆垛机任务回告", notes = "堆垛机任务回告")
    @RequestMapping("/callback")
    public RestMessage<String> taskReturn(@RequestBody McsCallBackDto mcsCallBackDto) throws Exception {
        logger.info("接收任务回告,{}", JsonUtils.toString(mcsCallBackDto));
        try {
            mcsCallbackService.mcsCallback(mcsCallBackDto);
            return RestMessage.newInstance(true,"回告成功");
        }catch (Exception e){
            return RestMessage.newInstance(false,"回告失败"+e.getMessage());
        }
    }
}
