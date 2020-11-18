package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 15:22
 *
 */
@Data
public class AgvStoreQueryDto {

    @ApiModelProperty("区域编号")
    private String areaNo;

    @ApiModelProperty("点位编号")
    private String locationNo;

    @ApiModelProperty("x轴")
    private int x;

    @ApiModelProperty("y轴")
    private int y;

    @ApiModelProperty("容器编号")
    private String containerNo;

    @ApiModelProperty("位置类型 1存储位 2 输送线 3托盘作业位")
    private int locationType;

    @ApiModelProperty("任务锁  0 空闲1任务中")
    private int taskLock;

    @ApiModelProperty("货位锁   0不锁定 1锁定 ")
    private int storagelock;


    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;

}
