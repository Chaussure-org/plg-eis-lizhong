package com.prolog.eis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.service.FeignService;
import com.prolog.eis.service.McsService;
import com.prolog.eis.util.HttpUtils;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:25
 */
@Service
public class McsServiceImpl implements McsService {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private FeignService feignService;

    @Override
    public  void doCallBack(McsMoveTaskDto mcsMoveTaskDto) throws Exception {

        String startUrl = "http://127.0.0.1:10010/mcs/callback";
        try {
            McsCallBackDto mcsCallBackDto = new McsCallBackDto();
            mcsCallBackDto.setTaskId(mcsMoveTaskDto.getTaskId());
            mcsCallBackDto.setContainerNo(mcsMoveTaskDto.getContainerNo());
            mcsCallBackDto.setType((short) mcsMoveTaskDto.getType());
            mcsCallBackDto.setStatus((short) 1);
            mcsCallBackDto.setStackerId(null);
            mcsCallBackDto.setAddress(mcsMoveTaskDto.getTarget());
            Thread.sleep(10000);
            RestMessage<String> result = httpUtils.post(startUrl, MapUtils.convertBean(mcsCallBackDto),
                    new TypeReference<RestMessage<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
           throw e;
        }
        System.out.println(mcsMoveTaskDto.getContainerNo()+"mcs回告开始");
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        String endUrl = "http://127.0.0.1:10010/mcs/callback";
        try {
            McsCallBackDto mcsCallBackDto = new McsCallBackDto();
            mcsCallBackDto.setTaskId(mcsMoveTaskDto.getTaskId());
            mcsCallBackDto.setContainerNo(mcsMoveTaskDto.getContainerNo());
            mcsCallBackDto.setType((short) mcsMoveTaskDto.getType());
            mcsCallBackDto.setStatus((short) 2);
            mcsCallBackDto.setStackerId(null);
            mcsCallBackDto.setAddress(mcsMoveTaskDto.getTarget());
            RestMessage<String> result = httpUtils.post(endUrl, MapUtils.convertBean(mcsCallBackDto),
                    new TypeReference<RestMessage<String>>() {});
            System.out.println(mcsMoveTaskDto.getContainerNo()+"mcs回告结束");
        } catch (Exception e) {
            System.out.println("mcscallback失败");
        }
    }

    public void testFeign(){
        StationInfoDto stationInfoDto = new StationInfoDto();
        stationInfoDto.setStationTaskType(20);
        stationInfoDto.setStationId(11);
        stationInfoDto.setIsLock(0);
        stationInfoDto.setStationIp("111.1111");

        RestMessage<String> restMessage = feignService.updateStation(stationInfoDto);
        System.out.println("aaaa");
    }



}
