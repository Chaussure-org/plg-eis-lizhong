package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prolog.framework.core.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/13 11:18
 * 盘点计划明细dto
 */
@Data
public class InventoryDetailInfoDto {


    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("商品图号")
    private String goodsNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("原始数量")
    private Integer originalCount;

    @ApiModelProperty("修改数量")
    private Integer modifyCount;

    @ApiModelProperty("任务状态 0新建 10下发 20出库 30进行中 40已完成")
    private String taskState;

    @ApiModelProperty("站台id")
    private Integer stationId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

    @ApiModelProperty("出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date outboundTime;



}
