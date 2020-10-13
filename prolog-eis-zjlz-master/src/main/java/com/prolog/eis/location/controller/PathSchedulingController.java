package com.prolog.eis.location.controller;

import com.prolog.eis.dto.location.ContainerPathTaskDTO;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.util.location.CommonConstants;
import com.prolog.framework.common.message.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wuxl
 * @create: 2020-09-28 16:35
 * @Version: V1.0
 */
@RestController
@RequestMapping("/api/v1/btn/task")
public class PathSchedulingController {
    @Autowired
    private PathSchedulingService pathSchedulingService;

    @PostMapping("/outbound/start")
    public RestMessage<ContainerPathTaskDTO> outboundTaskToStart(@RequestBody String json) throws Exception {
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);
        int goodsId = helper.getInt("goodsId");
        String stationId = helper.getString("stationId");
        if(StringUtils.isEmpty(stationId)){
            return RestMessage.newInstance(false, "站台号不能为空", null);
        }
        ContainerPathTaskDTO containerPathTaskDTO = pathSchedulingService.outboundTaskForUpdate(goodsId, stationId);
        if(null == containerPathTaskDTO){
            return RestMessage.newInstance(false, "无库存", null);
        }
        return RestMessage.newInstance(true, "操作成功", containerPathTaskDTO);
    }

    @PostMapping("/outbound/success")
    public RestMessage<String> outboundTaskToSuccess(@RequestBody String json) throws Exception {
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);
        ContainerPathTaskDTO containerPathTaskDTO= helper.getObject(ContainerPathTaskDTO.class);
        if(null == containerPathTaskDTO){
            return RestMessage.newInstance(false, "数据为空", CommonConstants.ERROR);
        }
        String msg = pathSchedulingService.outboundTaskForSuccess(containerPathTaskDTO);

        return RestMessage.newInstance(true, "操作成功", msg);
    }

    @PostMapping("/outbound/fail")
    public RestMessage<String> outboundTaskToFail(@RequestBody String json) throws Exception {
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);
        ContainerPathTaskDTO containerPathTaskDTO= helper.getObject(ContainerPathTaskDTO.class);
        if(null == containerPathTaskDTO){
            return RestMessage.newInstance(false, "数据为空", CommonConstants.ERROR);
        }
        String msg = pathSchedulingService.outboundTaskForFail(containerPathTaskDTO);

        return RestMessage.newInstance(true, "操作成功", msg);
    }
}
