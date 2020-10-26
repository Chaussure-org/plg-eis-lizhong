package com.prolog.eis.controller;

import com.prolog.eis.dto.sas.SasCarryListDto;
import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.eis.utils.CacheListUtils;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 10:02
 */
@RestController
@ApiModel("sas模拟接口")
@RequestMapping("/eis")
public class SasController {

    @RequestMapping("/getCarInfo")
    @ApiOperation(value = "获取小车信息",notes = "获取小车信息")
    public RestMessage<Map<String,Object>> getCarInfo(){
        List<CarInfoDTO> list = new ArrayList<>();
        for (int i = 1 ; i<28; i++){
            CarInfoDTO carInfoDTO = new CarInfoDTO();
            carInfoDTO.setLayer(i);
            carInfoDTO.setRgvId(String.valueOf(i));
            carInfoDTO.setStatus(1);
            list.add(carInfoDTO);
        }
        Map<String, Object> params = MapUtils.put("carryList", list).getMap();
        return RestMessage.newInstance(true,"操作成功",params);
    }

    @ApiOperation(value = "获取提升机信息",notes = "获取提升机信息")
    @RequestMapping("/getHoisterInfoDto")
    public RestMessage<List<HoisterInfoDto>> getHoisterInfoDto(){
        List<HoisterInfoDto> list = new ArrayList<>();
            HoisterInfoDto mcsCarInfoDto = new HoisterInfoDto();
            mcsCarInfoDto.setHoist("1");
            mcsCarInfoDto.setStatus(1);
            mcsCarInfoDto.setCode(0);
            list.add(mcsCarInfoDto);
        return RestMessage.newInstance(true,"操作成功",list);
    }

    @ApiOperation(value = "出入库任务",notes = "出入库任务")
    @RequestMapping("/sendContainerTask")
    public RestMessage<String> sendContainerTask(@RequestBody SasCarryListDto sasCarryListDto){
        //保存数据
        SasMoveTaskDto sasMoveTaskDto = sasCarryListDto.getCarryList().get(0);
        CacheListUtils.getSaslist().add(sasMoveTaskDto);
        return RestMessage.newInstance(true,"操作成功");
    }
}
