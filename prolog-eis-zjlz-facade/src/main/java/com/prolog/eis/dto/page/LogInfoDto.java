package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prolog.framework.core.annotation.AutoKey;
import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/11 17:16
 * 日志查询dto
 */
@Data
public class LogInfoDto {



    @ApiModelProperty("接口描述")
    private String descri;


    @ApiModelProperty("方法流向")
    private String direct;

    @ApiModelProperty("任务类型：1：入库 2：出库 3:移库 4:小车换层 5:输送线行走 \n" +
            "\t\t\t6-料箱进站 7-订单框进站 8-体积检测 9-入库口 10-订单箱到位 11-料箱到位")
    private Integer type;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("参数")
    private String params;

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("异常信息")
    private String exception;


    @ApiModelProperty("系统类型")
    private String  systemType;

    @ApiModelProperty("本机IP和PORT")
    private String hostPort;


    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
