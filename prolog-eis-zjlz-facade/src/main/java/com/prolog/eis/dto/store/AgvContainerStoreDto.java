package com.prolog.eis.dto.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AgvContainerStoreDto {

	@ApiModelProperty(value = "托盘号")
	private String containerNo;

	@ApiModelProperty(value = "托盘状态")
	private int containerState;

	@ApiModelProperty(value = "业主")
	private String ownerId;

	@ApiModelProperty(value = "商品编码")
	private String itemId;

	@ApiModelProperty(value = "批次号")
	private String lotId;

	@ApiModelProperty(value = "商品订单号")
	private String itemOrderNo;

	@ApiModelProperty(value = "中文名称")
	private String chineseName;

	@ApiModelProperty(value = "库存数量")
	private int qty;

	@ApiModelProperty(value = "商品ABC品类型")
	private String abcType;

}
