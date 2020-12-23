package com.prolog.eis.mcs.controller;

import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.mcs.service.IMcsCallBackService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private IMcsCallBackService mcsCallbackService;

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
