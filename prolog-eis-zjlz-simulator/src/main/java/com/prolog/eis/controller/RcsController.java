package com.prolog.eis.controller;

import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.service.RcsRequestService;
import com.prolog.eis.util.PrologApiJsonHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: wuxl
 * @create: 2020-09-16 15:19
 * @Version: V1.0
 */
@RestController
@Api(tags = "模拟rcs接口")
@RequestMapping("/api/v1/agv/rcsRequest")
public class RcsController {

    @Autowired
    private RcsRequestService rcsRequestService;

    @ApiOperation(value = "Rcs移动", notes = "Rcs移动")
    @PostMapping("/agvMove")
    public void agvMove(@RequestBody String json, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        OutputStream out = response.getOutputStream();
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);
        String reqCode = null;
        String taskTyp = null;

        try {
            reqCode = helper.getString("reqCode");
            taskTyp = helper.getString("taskTyp");

            String resultStr = returnSuccess(reqCode);
            out.write(resultStr.getBytes("UTF-8"));
            out.flush();
            out.close();
        }catch (Exception e) {
            String resultStr = returnError(reqCode);
            out.write(resultStr.getBytes("UTF-8"));
            out.flush();
            out.close();
        }

        rcsRequestService.sendTask(reqCode, taskTyp);
    }


    private String returnSuccess(String reqCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "0");
        jsonObject.put("message", "成功");
        jsonObject.put("reqCode", reqCode);
        jsonObject.put("data", "");
        return jsonObject.toString();
    }

    private String returnError(String reqCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "99");
        jsonObject.put("message", "失败");
        jsonObject.put("reqCode", reqCode);
        jsonObject.put("data", "");
        return jsonObject.toString();
    }
}
