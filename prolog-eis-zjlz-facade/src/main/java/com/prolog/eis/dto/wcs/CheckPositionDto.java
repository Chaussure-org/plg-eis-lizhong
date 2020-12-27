package com.prolog.eis.dto.wcs;

import lombok.Data;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020/12/26 15:45
 */
@Data
public class CheckPositionDto {
    /**
     * 36位GUID
     */
    private String taskId;

    /**
     * 发送时间，时间格式采用yyyy-MM-dd HH:mm:ss
     */
    private String time;

    /**
     * 查询出库货位是否空闲，具体编号可现场沟通确认
     */
    private String deviceId;
}

