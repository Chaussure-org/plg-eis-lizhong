package com.prolog.eis.dto.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContainerStoreInfoDto {
	@ApiModelProperty("托盘号")
	private String containerNo;

	@ApiModelProperty("-1 空托剁 1任务托")
	private int containerType;

	@ApiModelProperty("0无业务任务 10 入库 11补货入库 12移库入库 20出库 21盘点出库 22移库出库")
	private int taskType;

	@ApiModelProperty("作业次数")
	private int workCount;

	@ApiModelProperty("商品编码")
	private String goodsCode;

	@ApiModelProperty("业主")
	private String ownerId;

	@ApiModelProperty("批次号")
	private String lotId;

	@ApiModelProperty("商品订单号")
	private String goodsOrderNo;

	@ApiModelProperty("商品条码")
	private String goodsBarcode;

	@ApiModelProperty("中文名称")
	private String chineseName;

	@ApiModelProperty("库存数量")
	private int qty;

	@ApiModelProperty("箱规格")
	private String caseSpecs;

	@ApiModelProperty("中盒规格")
	private String boxSpecs;

	@ApiModelProperty("品种类型")
	private String abcType;

	@ApiModelProperty("终点区域")
	private String targetArea;

	@ApiModelProperty("载具编号")
	private String palletNo;

	@ApiModelProperty("容器高度")
	private Integer actualHeight;

}
