package com.prolog.eis.engin.dispatch;

import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.util.EisRestMessage;
import com.prolog.eis.util.EisStringUtils;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author dengj
 * @Date 2021/1/25 15:22
 * @Version 1.0
 *
 *wms回传定时器
 */
@Component
public class CallBackWms {

    public static final Logger logger = LoggerFactory.getLogger(CallBackWms.class);

    @Autowired
    private IWareHousingService wareHousingService;
    @Autowired
    private IWmsService wmsService;
    /**
     * 入库任务回告每20分钟回告一次
     */
    @Scheduled(cron = "0 5/20 * * * ? ")
    public void inboundTaskSend(){

        List<WmsInboundTask> wmsInboundTasks = wareHousingService.findInboundByMap(MapUtils.put("callState", WmsInboundTask.CALL_STATE_IN).getMap());
        if (wmsInboundTasks.size() == 0){
            return;
        }
        for (WmsInboundTask wmsInboundTask : wmsInboundTasks) {
            WmsInboundCallBackDto wmsInboundCallBackDto = new WmsInboundCallBackDto();
            wmsInboundCallBackDto.setITEMID(EisStringUtils.getRemouldId(wmsInboundTask.getGoodsId()));
            wmsInboundCallBackDto.setLINEID(wmsInboundTask.getLineId());
            wmsInboundCallBackDto.setBILLNO(wmsInboundTask.getBillNo());
            wmsInboundCallBackDto.setBILLTYPE(wmsInboundTask.getBillType());
            wmsInboundCallBackDto.setCONTAINERNO(wmsInboundTask.getContainerNo());

            wmsInboundCallBackDto.setITEMNAME(wmsInboundTask.getGoodsName());
            wmsInboundCallBackDto.setSEQNO(wmsInboundTask.getSeqNo());
            try {
                EisRestMessage<String> restMessage = wmsService.inboundTaskCallBack(wmsInboundCallBackDto);
                if (restMessage.getCode().equals("True")){
                    wareHousingService.inboundToHistory(wmsInboundTask);
                }
            } catch (Exception e) {
                logger.error(wmsInboundTask.getContainerNo()+"eis ->wms 入库任务回告失败"+e.getMessage(),e);
            }
        }
    }
}
