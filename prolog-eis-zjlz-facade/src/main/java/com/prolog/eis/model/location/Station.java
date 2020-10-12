package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 站台表(Station)实体类
 *
 * @author yf
 * @since 2020-09-22 11:33:25
 */
@Data
@Table("STATION")
public class Station implements Serializable {
	private static final long serialVersionUID = -78151290235330580L;

	@Id
	@Column("ID")
	@ApiModelProperty("站台ID")
	private Integer id;

	@Column("STATION_NO")
	@ApiModelProperty("站台号")
	private String stationNo;

	@Column("CURRENT_STATION_PICK_ID")
	@ApiModelProperty("当前拣选单ID")
	private Integer currentStationPickId;

	@Column("IS_LOCK")
	@ApiModelProperty("是否锁定(0否;1是)")
	private Integer isLock;

	@Column("PICKING_USER_ID")
	@ApiModelProperty("拣选人员ID")
	private String pickingUserId;

	@Column("PICKING_USER_NAME")
	@ApiModelProperty("拣选人员名称")
	private String pickingUserName;

	@Column("SEED_USER_ID")
	@ApiModelProperty("播种人员ID")
	private String seedUserId;

	@Column("SEED_USER_NAME")
	@ApiModelProperty("播种人员名称")
	private String seedUserName;

	@Column("MAX_ORDER_COUNT")
	@ApiModelProperty("最大订单数量")
	private Integer maxOrderCount;

	@Column("TYPE")
	@ApiModelProperty("站台作业类型(10播种拣选;20盘点;30合托;40养护)")
	private Integer type;

	@Column("PALLET_MAX_CACHE_QTY")
	@ApiModelProperty("托盘最大缓存数")
	private Integer palletMaxCacheQty;

	@Column("AREA_NO")
	@ApiModelProperty("区域编号")
	private String areaNo;


}