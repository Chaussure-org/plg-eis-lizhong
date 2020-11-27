package com.prolog.eis.out.controller;

import com.prolog.eis.dto.out.EmptyBoxBackDto;
import com.prolog.eis.out.service.EmptyBoxBackService;
import com.prolog.framework.common.message.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:空箱退库
 * 三十功名尘与土，八千里路云和月
 *
 * @date:2020/10/26 9:14
 * @author:SunPP
 */
@RestController
@RequestMapping("wms/page")
public class EmptyBoxBackController {

    @Autowired
    private EmptyBoxBackService emptyBoxBackService;

    @RequestMapping("back")
    public RestMessage back(@RequestParam()int qty) throws Exception {
        try {
//            emptyBoxBackService.outEmptyBox(qty);
            return RestMessage.newInstance(true,"",null);
        } catch (Exception e) {
           return RestMessage.newInstance(false,"操作失败:"+e.getMessage(),null);
        }

    }
}
