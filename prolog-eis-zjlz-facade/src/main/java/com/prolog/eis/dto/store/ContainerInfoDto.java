package com.prolog.eis.dto.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
