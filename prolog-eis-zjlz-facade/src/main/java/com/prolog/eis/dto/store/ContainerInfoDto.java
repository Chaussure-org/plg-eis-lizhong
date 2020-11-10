package com.prolog.eis.dto.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 14:04
 * 容器资料信息dto
 */
@Data
public class ContainerInfoDto {

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("图号")
    private String ownerDrawnNo;

    @ApiModelProperty("批次")
    private String lotId;

    @ApiModelProperty("库存数量")
    private Integer qty;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
