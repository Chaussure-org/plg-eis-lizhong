package com.prolog.eis.model;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table("goods_info")
public class GoodsInfo {

	@Id
	@Column("id")
	@ApiModelProperty("主键id")
	private Integer id;
	
	@Column("goods_code")
	@ApiModelProperty("商品编码")
	private String goodsCode;
	
	@Column("owner_id")
	@ApiModelProperty("业主")
	private String ownerId;
	
	@Column("lot_id")
	@ApiModelProperty("批次号")
	private String lotId;
	
	@Column("goods_order_no")
	@ApiModelProperty("商品订单号")
	private String goodsOrderNo;
	
	@Column("goods_barcode")
	@ApiModelProperty("商品条码")
	private String goodsBarcode;
	
	@Column("chinese_name")
	@ApiModelProperty("中文名称")
	private String chineseName;
}
