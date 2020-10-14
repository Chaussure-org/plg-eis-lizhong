package com.prolog.eis.wcs.service.impl;


import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.wcs.service.IWCSCallbackService;
import com.prolog.framework.common.message.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WCSCallbackServiceImpl implements IWCSCallbackService {
    private final Logger logger = LoggerFactory.getLogger(WCSCallbackServiceImpl.class);



    private final RestMessage<String> success = RestMessage.newInstance(true, "200", "操作成功", null);
    private final RestMessage<String> faliure = RestMessage.newInstance(false, "500", "操作失败", null);
    private final RestMessage<String> out = RestMessage.newInstance(false, "300", "订单箱异常", null);

    /**
     * 任务回告
     *
     * @param taskCallbackDTO
     * @return
     */
    @Override
    @LogInfo(desci = "wcs行走任务回告",direction = "wcs->eis",type = LogDto.WCS_TYPE_TASK_CALLBACK,systemType = LogDto.WCS)
    @Transactional(rollbackFor = Exception.class)
    public RestMessage<String> executeTaskCallback(TaskCallbackDTO taskCallbackDTO) {

        return success;
    }




    /**
     * BCR 回告
     *
     * @param bcrDataDTO
     * @return
     */
    @Override
    @LogInfo(desci = "wcsBCR请求",direction = "wcs->eis",type = LogDto.WCS_TYPE_BCR_REQUEST,systemType = LogDto.WCS)
    public RestMessage<String> executeBcrCallback(BCRDataDTO bcrDataDTO) {
        return success;
    }

    /**
     * 料箱、订单框到位回告
     *
     * @param boxCallbackDTO
     * @return
     */
    @Override
    public RestMessage<String> executeBoxArriveCallback(BoxCallbackDTO boxCallbackDTO) {
        return success;
    }

    /**
     * 料箱弹出完成回告
     *
     * @param boxCompletedDTO
     * @return
     */
    @Override
    public RestMessage<String> executeCompleteBoxCallback(BoxCompletedDTO boxCompletedDTO) {
        return success;
    }


    /**
     * 拍灯回告
     *
     * @param lightDTO
     * @return
     */
    @Override
    public RestMessage<String> executeLightCallback(LightDTO lightDTO) {
        return success;
    }


    /**
     * 出库任务
     *
     * @param taskCallbackDTO
     */
    @Transactional(rollbackFor = Exception.class,timeout = 600)
    public void doOutboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }


    /**
     * 行走任务回告,入库点
     *
     * @param taskCallbackDTO
     */
    @Transactional(rollbackFor = Exception.class,timeout = 600)
    public void doXZTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

    /**
     * 外形检测结果处理
     *
     * @param bcrDataDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void shapeInspect(BCRDataDTO bcrDataDTO) throws Exception {

    }

    /**
     * 入库任务回告
     *
     * @param taskCallbackDTO
     */
    @Transactional(rollbackFor = Exception.class,timeout = 600)
    public void doInboundTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

    /**
     * 换层任务回告
     * @param taskCallbackDTO
     */
    private void doHcTask(TaskCallbackDTO taskCallbackDTO) throws Exception {

    }

}
