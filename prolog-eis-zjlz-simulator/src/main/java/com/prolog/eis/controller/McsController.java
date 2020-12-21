package com.prolog.eis.controller;

import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.mcs.McsCarryListDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.service.McsService;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.utils.CacheListUtils;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 10:02
 */
@RestController
@ApiModel("mcs模拟接口")
@RequestMapping("/eis")
public class McsController {

    @Autowired
    private McsService mcsService;


    @ApiOperation(value = "托盘移动",notes = "托盘移动")
    @RequestMapping("/mcsContainerMove")
    public RestMessage<String> mcsContainerMove(@RequestBody McsCarryListDto mcsCarryListDto) throws Exception {
        //报存数据
        McsMoveTaskDto mcsMoveTaskDto = mcsCarryListDto.getCarryList().get(0);
        new Thread(()->{
            try {
                mcsService.doCallBack(mcsMoveTaskDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return RestMessage.newInstance(true,"操作成功");
    }

    @ApiOperation(value = "获取堆垛机信息",notes = "获取堆垛机信息")
    @RequestMapping("/getMcsCarInfo")
    public RestMessage<List<McsCarInfoDto>> getMcsCarInfo(){
        List<McsCarInfoDto> list = new ArrayList<>();
        for (int i = 1;i<6; i++){
            McsCarInfoDto mcsCarInfoDto = new McsCarInfoDto();
            mcsCarInfoDto.setRgvId(String.valueOf(i));
            mcsCarInfoDto.setStatus(1);
            mcsCarInfoDto.setCode(0);
            list.add(mcsCarInfoDto);
        }
        return RestMessage.newInstance(true,"操作成功",list);
    }
}
