package com.prolog.eis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
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

    @Override
    public void doCallBack(McsMoveTaskDto mcsMoveTaskDto){
        String startUrl = "http://service-ai-eis-zjlz-master-wk/mcs/callback";
        try {
            McsCallBackDto mcsCallBackDto = new McsCallBackDto();
            mcsCallBackDto.setTaskId(mcsMoveTaskDto.getTaskId());
            mcsCallBackDto.setContainerNo(mcsMoveTaskDto.getContainerNo());
            mcsCallBackDto.setType((short) mcsMoveTaskDto.getType());
            mcsCallBackDto.setStatus((short) 1);
            mcsCallBackDto.setRgvId(null);
            mcsCallBackDto.setAddress(mcsMoveTaskDto.getTarget());
            RestMessage<String> result = httpUtils.post(startUrl, MapUtils.convertBean(mcsCallBackDto),
                    new TypeReference<RestMessage<String>>() {});
            System.out.println("mcscallback成功");
        } catch (Exception e) {
            System.out.println("mcscallback失败");
        }
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        String endUrl = "http://service-ai-eis-zjlz-master-wk/mcs/callback";
        try {
            McsCallBackDto mcsCallBackDto = new McsCallBackDto();
            mcsCallBackDto.setTaskId(mcsMoveTaskDto.getTaskId());
            mcsCallBackDto.setContainerNo(mcsMoveTaskDto.getContainerNo());
            mcsCallBackDto.setType((short) mcsMoveTaskDto.getType());
            mcsCallBackDto.setStatus((short) 2);
            mcsCallBackDto.setRgvId(null);
            mcsCallBackDto.setAddress(mcsMoveTaskDto.getTarget());
            RestMessage<String> result = httpUtils.post(endUrl, MapUtils.convertBean(mcsCallBackDto),
                    new TypeReference<RestMessage<String>>() {});
            System.out.println("mcscallback成功");
        } catch (Exception e) {
            System.out.println("mcscallback失败");
        }
    }
}
