package com.prolog.eis.model.location.sxk;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@Table("SX_STORE_LOCATION_GROUP")
public class SxStoreLocationGroup {

	@Id
	@Column("ID")
	@ApiModelProperty("货位组ID")
	private int id;		//货位组ID
	
	@Column("GROUP_NO")
	@ApiModelProperty("货位组编号")
	private String groupNo;		//货位组编号
	
	@Column("ENTRANCE")
	@ApiModelProperty("入口类型：1、仅入口1，2、仅入口2，3、入口1+入口2")
	private int entrance;		//入口类型：1、仅入口1，2、仅入口2，3、入口1+入口2
	
	@Column("IN_OUT_NUM")
	@ApiModelProperty("出入口数量")
	private int inOutNum;		//出入口数量
	
	@Column("IS_LOCK")
	@ApiModelProperty("是否锁定")
	private int isLock;		//是否锁定(1.锁定 、0.不锁定)
	
	@Column("ASCENT_LOCK_STATE")
	@ApiModelProperty("货位组升位锁")
	private int ascentLockState;		//货位组升位锁 (1.锁定 、0.不锁定)
	
	@Column("READY_OUT_LOCK")
	@ApiModelProperty("待出库锁")
	private int readyOutLock;		//待出库锁 (1.锁定 、0.不锁定)
	
	@Column("LAYER")
	@ApiModelProperty("层")
	private int layer;		//层

	@Column("X")
	@ApiModelProperty("X")
	private int x;		//X
	
	@Column("Y")
	@ApiModelProperty("Y")
	private int y;		//Y
	
	@Column("location_num")
	@ApiModelProperty("货位数量")
	private Integer locationNum;		//货位数量
	
	@Column("entrance1_property1")
	@ApiModelProperty("入口1的属性1(无入口则值为'none')")
	private String entrance1Property1;		//入口1的属性1(无入口则值为'none')
	
	@Column("entrance1_property2")
	@ApiModelProperty("入口1的属性2(无入口则值为'none')")
	private String entrance1Property2;		//入口1的属性2(无入口则值为'none')
	
	@Column("entrance2_property1")
	@ApiModelProperty("入口2的属性1(无入口则值为'none')")
	private String entrance2Property1;		//入口2的属性1(无入口则值为'none')
	
	@Column("entrance2_property2")
	@ApiModelProperty("入口2的属性2(无入口则值为'none')")
	private String entrance2Property2;		//入口2的属性2(无入口则值为'none')

	@Column("reserved_location")
	@ApiModelProperty("预留货位")
	private int reservedLocation;		//预留货位1.空托盘预留货位、2.理货预留货位、3.不用预留货位

	@Column("belong_area")
	@ApiModelProperty("所属区域")
	private String belongArea;		//所属区域
	
	@Column("CREATE_TIME")
	@ApiModelProperty("创建时间")
	private Date createTime;		//创建时间
}
