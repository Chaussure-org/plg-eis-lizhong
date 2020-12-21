package com.prolog.eis.sas.service.impl;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IContainerPathTaskDetailService;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.sas.service.ISasCallbackService;
import com.prolog.eis.sas.service.ISasLogicService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.framework.common.message.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SasCallbackServiceImpl implements ISasCallbackService {

    private final Logger logger = LoggerFactory.getLogger(SasCallbackServiceImpl.class);
    private final RestMessage<String> success = RestMessage.newInstance(true, "200", "操作成功", null);
    private final RestMessage<String> faliure = RestMessage.newInstance(false, "500", "操作失败", null);
    private final RestMessage<String> out = RestMessage.newInstance(false, "300", "订单箱异常", null);

    @Autowired
    private ISasLogicService sasLogicService;

    /**
     * 任务回告
     *
     * @param taskCallbackDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,timeout = 6000)
    public RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO) throws Exception {
        if (taskCallbackDTO == null) {
            return success;
        }
        try {
            switch (taskCallbackDTO.getType()) {
                case 1:
                    sasLogicService.doInboundTask(taskCallbackDTO);
                    break;
                case 2:
                    sasLogicService.doOutboundTask(taskCallbackDTO);
                    break;
                case 3:
                    sasLogicService.doMoveTask(taskCallbackDTO);
                    break;
                case 4:
                    sasLogicService.doHcTask(taskCallbackDTO);
                    break;
                default:
                    throw new Exception("回告类型未找到");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return success;
    }




}
