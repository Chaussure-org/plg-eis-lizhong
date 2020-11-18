package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/13 12:18
 * 盘点历史dto
 */
@Data
public class InventoryHistoryDto {
    @ApiModelProperty("盘点计划id")
    private int inventoryId;
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
    
    @ApiModelProperty("站台id")
    private Integer stationId;
    
    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date endTime;
}
