package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/11 16:02
 */
@Data
public class LogQueryDto {
    @NotNull
    @ApiModelProperty("日志类型1,WMS = 1   2,WCS = 2   3,SAS = 3   4,MCS = 4    5,RCS = 5")
    private int logType;
    @ApiModelProperty("接口描述")
    private String descri;
    @ApiModelProperty("方向")
    private String direct;

    @ApiModelProperty("参数")
    private String params;
    @ApiModelProperty("是否成功(1:成功,0:失败)")
    private Boolean success;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("开始时间")
    private Date startTime;
    
    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;
}
