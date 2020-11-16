package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 14:12
 */
@Data
public class ContainerQueryDto {
    @ApiModelProperty("商品id")
    private int goodsId;
    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("图号")
    private String ownerDrawnNo;

    @ApiModelProperty("批次")
    private String lotId;

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
