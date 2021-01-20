package com.prolog.eis.rcs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.dto.rcs.RcsCallbackDto;
import com.prolog.eis.rcs.service.IRcsCallbackService;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.wcs.controller.WcsController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "海康rcs接口(agv)")
@RequestMapping("rcs")
public class RcsController {

    private final Logger logger = LoggerFactory.getLogger(RcsController.class);

    @Autowired
    private IRcsCallbackService rcsCallbackService;

    @ApiOperation(value = "Rcs回告", notes = "Rcs回告")
    @PostMapping("/agvCallback")
    public String agvCallback(@RequestBody String json) throws Exception {
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);
        logger.info("eis <- rcs 任务：{}",json);
        String reqCode = helper.getString("reqCode");
        try {
            String taskCode = helper.getString("taskCode");
            String method = helper.getString("method");
            System.out.println("=================="+taskCode+"."+method);
            RcsCallbackDto rcsCallbackDto = new RcsCallbackDto();
            rcsCallbackDto.setMethod(method);
            rcsCallbackDto.setTaskCode(taskCode);
            rcsCallbackService.rcsCallback(rcsCallbackDto);
            String resultStr = returnSuccess(reqCode);
            return resultStr;
        } catch (Exception e) {
            String resultStr = returnSuccess(reqCode);
            logger.info("eis <- rcs 接收任务回告失败"+e.getMessage(),e);
            //String errorMsg = "RCS-> EIS[agvCallback]返回" + reqCode +" json:" + resultStr;
            //LogServices.logSys(e);
            return resultStr;
        }
    }

    private String returnSuccess(String reqCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "0");
        jsonObject.put("message", "成功");
        jsonObject.put("reqCode", reqCode);
        jsonObject.put("data", "");
        return jsonObject.toString();
    }

    private String returnError(String reqCode, String errorMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "99");
        jsonObject.put("message", errorMsg);
        jsonObject.put("reqCode", reqCode);
        jsonObject.put("data", "");
        return jsonObject.toString();
    }
}
