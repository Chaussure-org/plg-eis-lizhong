package com.prolog.eis.sas.service.impl;

import com.prolog.eis.dto.wcs.BCRDataDTO;
import com.prolog.eis.dto.wcs.TaskCallbackDTO;
import com.prolog.eis.sas.service.ISASCallbackService;
import com.prolog.framework.common.message.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SASCallbackServiceImpl implements ISASCallbackService {
    private final Logger logger = LoggerFactory.getLogger(SASCallbackServiceImpl.class);



    private final RestMessage<String> success = RestMessage.newInstance(true, "200", "操作成功", null);
    private final RestMessage<String> faliure = RestMessage.newInstance(false, "500", "操作失败", null);
    private final RestMessage<String> out = RestMessage.newInstance(false, "300", "订单箱异常", null);

    private void pwait() {
         try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 任务回告
     *
     * @param taskCallbackDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,timeout = 600)
    public RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO) {

        return success;
    }


    /**
     * 出库任务
     *
     * @param taskCallbackDTO
     */
    private void doOutboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

    /**
     * 入库任务回告
     *
     * @param taskCallbackDTO
     */
    private void doInboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

    /**
     * 换层任务回告
     * @param taskCallbackDTO
     */
    private void doHcTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

}
