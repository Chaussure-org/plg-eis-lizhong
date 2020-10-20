package com.prolog.eis.log.controller;

import com.prolog.eis.log.service.ILogService;
import com.prolog.eis.util.IPUtils;
import com.prolog.eis.util.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author wangkang
 * @Description test
 * @CreateTime 2020-10-20 14:43
 */
@RequestMapping("/log")
@RestController
public class LogController {

    @Autowired
    private ILogService logService;

    @PostMapping("/date")
    @LogInfo(direction = "1",desci = "acb",type = 1,systemType = 3)
    public Date newDate(HttpServletRequest request) {
        System.out.println(new Date());
        System.out.println(IPUtils.getIpAddr(request));
        return new Date();
    }




}
