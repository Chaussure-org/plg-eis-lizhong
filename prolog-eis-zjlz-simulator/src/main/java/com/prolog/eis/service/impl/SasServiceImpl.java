package com.prolog.eis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.dto.mcs.McsCallBackDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.service.McsService;
import com.prolog.eis.service.SasService;
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
public class SasServiceImpl implements SasService {

    @Autowired
    private HttpUtils httpUtils;

    @Override
    public synchronized void doCallBack(SasMoveTaskDto sasMoveTaskDto) {
        String url = "http://service-ai-eis-zjlz-master-wk/sas/task/callback";
        try {
            TaskCallbackDTO taskCallbackDTO = new TaskCallbackDTO();
            taskCallbackDTO.setTaskId(sasMoveTaskDto.getTaskId());
            taskCallbackDTO.setContainerNo(sasMoveTaskDto.getContainerNo());
            taskCallbackDTO.setType((short) sasMoveTaskDto.getType());
            taskCallbackDTO.setStatus((short) 2);
            taskCallbackDTO.setRgvId(null);
            taskCallbackDTO.setAddress(null);
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(taskCallbackDTO),
                    new TypeReference<RestMessage<String>>() {});
            System.out.println("=======回告EIS Sas  callback成功");
        } catch (Exception e) {
            System.out.println("sascallback失败");
        }
    }
}
