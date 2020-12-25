package com.prolog.eis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.dto.wcs.BCRDataDTO;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
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
public class WcsServiceImpl implements WcsService {

    @Autowired
    private HttpUtils httpUtils;

    @Override
    public void doCallBack(WcsLineMoveDto wcsLineMoveDto) {
        String url = "http://10.0.2.135:10010/wcs/task/callback";
        try {
            TaskCallbackDTO taskCallbackDTO = new TaskCallbackDTO();
            taskCallbackDTO.setTaskId(wcsLineMoveDto.getTaskId());
            taskCallbackDTO.setType((short) 5);
            taskCallbackDTO.setAddress(wcsLineMoveDto.getTarget());
            taskCallbackDTO.setStatus((short) 2);
            taskCallbackDTO.setRgvId(null);
            taskCallbackDTO.setContainerNo(wcsLineMoveDto.getContainerNo());
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(taskCallbackDTO),
                    new TypeReference<RestMessage<String>>() {
                    });
            System.out.println("wcscallback成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void doBcrRequest(WcsLineMoveDto wcsLineMoveDto, int type) {
        String url = "http://10.0.2.135:10010/wcs/bcr";
        try {
            BCRDataDTO bcrDataDTO = new BCRDataDTO();
            bcrDataDTO.setContainerNo(wcsLineMoveDto.getContainerNo());
            bcrDataDTO.setTaskId(null);
            bcrDataDTO.setAddress(wcsLineMoveDto.getTarget());
            bcrDataDTO.setShapeInspect(true);
            bcrDataDTO.setType((short) type);
            bcrDataDTO.setShapeInspectDesc("");
            bcrDataDTO.setWeightInspect("");
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(bcrDataDTO),
                    new TypeReference<RestMessage<String>>() {
                    });
            System.out.println("wcscallback成功");
        } catch (Exception e) {
            System.out.println("wcscallback失败");
        }
    }
}
