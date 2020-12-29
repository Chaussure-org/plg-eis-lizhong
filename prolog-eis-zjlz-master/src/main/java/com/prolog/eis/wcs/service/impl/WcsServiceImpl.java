package com.prolog.eis.wcs.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wcs.CheckPositionDto;
import com.prolog.eis.dto.wcs.TrayCallbackDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.model.wcs.WcsCommandRepeat;
import com.prolog.eis.util.HttpUtils;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.wcs.service.IWcsCommandRepeatService;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @Author wangkang
 * @Description wcs服务实现
 * @CreateTime 2020-11-02 9:20
 */
@Service
public class WcsServiceImpl implements IWcsService {
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private EisProperties properties;

    @Autowired
    private IWcsCommandRepeatService wcsCommandRepeatService;

    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(WcsServiceImpl.class);

    /**
     * 获取完整路径
     *
     * @param path
     * @return
     */
    private String getUrl(String path) {
        return String.format("http://%s:%s%s", properties.getWcs().getHost(), properties.getWcs().getPort(),path);
    }

    /**
     * 输送线行走
     *
     * @param wcsLineMoveDto 输送线行走实体
     * @return
     */
    @Override
    @LogInfo(desci = "eis发送输送线行走命令", direction = "eis->wcs", type = LogDto.WCS_TYPE_LINE_MOVE, systemType = LogDto.WCS)
    public RestMessage<String> lineMove(WcsLineMoveDto wcsLineMoveDto, int i) throws Exception {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());

        logger.info("EIS -> WCS 输送线行走:{}", url);
        RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(wcsLineMoveDto),
                new TypeReference<RestMessage<String>>() {
                });

        // 输送线任务不成功，则存表，定时器扫描后再次发送 add sunpp,发送失败 存表有 bug
        if (!result.isSuccess() && i == 0) {
            WcsCommandRepeat wcsCommandRepeat = new WcsCommandRepeat(wcsLineMoveDto.getTaskId(),
                    wcsLineMoveDto.getAddress(), wcsLineMoveDto.getTarget(), wcsLineMoveDto.getContainerNo(),
                    wcsLineMoveDto.getType(), new Date());
            wcsCommandRepeatService.saveWcsCommand(wcsCommandRepeat);
        }
        return result;
    }

    /**
     * 拆盘机入口托盘到位通知wcs
     *
     * @param trayCallbackDto
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(desci = "eis回告wcs拆盘机口有订单拖", direction = "eis->wcs", type = LogDto.WCS_TYPE_TARY_ARRIVE, systemType = LogDto.WCS)
    public RestMessage<String> trayToOpenDisk(TrayCallbackDto trayCallbackDto) throws Exception {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WCS 输送线行走:{}", url);
        RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(trayCallbackDto),
                new TypeReference<RestMessage<String>>() {
                });
        return result;
    }


    @LogInfo(desci = "eis请求wcs出库口点位是否空闲", direction = "eis->wcs", type = 888, systemType = LogDto.WCS)
    public RestMessage<String> checkPosition(CheckPositionDto checkPositionDto) throws Exception {
        String url = this.getUrl(properties.getWcs().getCheckPosition());
        RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(checkPositionDto),
                new TypeReference<RestMessage<String>>() {
                });
        return null;
    }

}
