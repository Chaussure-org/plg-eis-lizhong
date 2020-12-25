package com.prolog.eis.mcs.controller;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.mcs.service.IMcsCallBackService;
import com.prolog.eis.util.LogInfo;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "堆垛机任务回告", notes = "堆垛机任务回告")
    @RequestMapping("/callback")
    public Map taskReturn(@RequestBody McsCallBackDto mcsCallBackDto) throws Exception {
        logger.info("接收任务回告,{}", JsonUtils.toString(mcsCallBackDto));
        try {
            mcsCallbackService.mcsCallback(mcsCallBackDto);
            Map mcsMap = new HashMap();
            mcsMap.put("ret", true);
            mcsMap.put("msg", "回告成功");
            mcsMap.put("data", null);
            return mcsMap;
        } catch (Exception e) {
            Map mcsMap = new HashMap();
            mcsMap.put("ret", false);
            mcsMap.put("msg", "回告失败");
            mcsMap.put("data", null);
            return mcsMap;
        }
    }

    @ApiOperation(value = "堆垛机信息上报", notes = "堆垛机信息上报")
    @LogInfo(desci = "堆垛机信息上报", direction = "sps->eis", type = LogDto.MCS, systemType = LogDto.MCS)
    @RequestMapping("/info")
    public RestMessage<McsCarInfoDto> mcsInfo(@RequestBody List<McsCarInfoDto> carInfo) throws Exception {
        String json = JsonUtils.toString(carInfo);
        logger.info("接收任务信息,{}", json);
        try {
            redisTemplate.opsForValue().set("mcsInfo", json);
            return RestMessage.newInstance(true, "接收成功");
        } catch (Exception e) {
            return RestMessage.newInstance(false, "接受失败" + e.getMessage());
        }
    }

}
