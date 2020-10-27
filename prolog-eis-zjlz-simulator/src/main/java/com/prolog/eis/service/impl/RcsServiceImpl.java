package com.prolog.eis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.service.RcsService;
import com.prolog.eis.service.WcsService;
import com.prolog.eis.util.HttpUtils;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:46
 */
@Service
public class RcsServiceImpl implements RcsService {

    @Autowired
    private HttpUtils httpUtils;

    @Override
    public void doCallBack(RcsTaskDto rcsTaskDto) {
        String startUrl = "http://service-ai-eis-zjlz-master-wk/rcs/agvCallback";
        try {
            RestMessage<String> result = httpUtils.post(startUrl, MapUtils.put("reqCode", rcsTaskDto.getReqCode()).put("taskCode",
                    rcsTaskDto.getReqCode()).put("method", "outbin").getMap(),
                    new TypeReference<RestMessage<String>>() {
                    });
            System.out.println("rcscallback成功");
        } catch (Exception e) {
            System.out.println("rcscallback失败");
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String endUrl = "http://service-ai-eis-zjlz-master-wk/rcs/agvCallback";
        try {
            RestMessage<String> result = httpUtils.post(endUrl, MapUtils.put("reqCode", rcsTaskDto.getReqCode()).put("taskCode",
                    rcsTaskDto.getReqCode()).put("method", "end").getMap(),
                    new TypeReference<RestMessage<String>>() {
                    });
            System.out.println("rcscallback成功");
        } catch (Exception e) {
            System.out.println("rcscallback失败");
        }
    }
}
