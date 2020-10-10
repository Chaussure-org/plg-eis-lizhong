package com.prolog.eis.model.location.sxk;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@Table("SX_STORE_LOCATION")
public class SxStoreLocation {

	@Id
	@Column("id")
    @ApiModelProperty("货位ID")
    private Integer id;        //货位ID

    @Column("store_no")
    @ApiModelProperty("货位编号")
    private String storeNo;

    @Column("STORE_LOCATION_GROUP_ID")
    @ApiModelProperty("货位组ID")
    private int storeLocationGroupId;        //货位组ID

    @Column("LAYER")
    @ApiModelProperty("层")
    private int layer;        //层

    @Column("X")
    @ApiModelProperty("X")
    private int x;        //X

    @Column("Y")
    @ApiModelProperty("Y")
    private int y;        //Y

    @Column("STORE_LOCATION_ID1")
    @ApiModelProperty("相邻货位ID1")
    private Integer storeLocationId1;        //相邻货位ID1

    @Column("STORE_LOCATION_ID2")
    @ApiModelProperty("相邻货位ID2")
    private Integer storeLocationId2;        //相邻货位ID2


    @Column("ASCENT_LOCK_STATE")
    @ApiModelProperty("货位组升位锁")
    private int ascentLockState;        //货位组升位锁

    @Column("LOCATION_INDEX")
    @ApiModelProperty("货位组位置索引(从上到下、从左到右)")
    private Integer locationIndex;        //货位组位置索引(从上到下、从左到右)

    @Column("dept_num")
    @ApiModelProperty("移库数")
    private int deptNum;

    @Column("depth")
    @ApiModelProperty("深度")
    private int depth;

    @Column("CREATE_TIME")
    @ApiModelProperty("创建时间")
    private Date createTime;        //创建时间
    
    @Column("vertical_location_group_id")
    @ApiModelProperty("垂直货位Id")
    private Integer verticalLocationGroupId;
    
    @Column("actual_weight")
    @ApiModelProperty("实际重量")
    private Double actualWeight;		//实际重量

    @Column("limit_weight")
    @ApiModelProperty("限重")
    private Double limitWeight;		//限重
    
    @Column("is_inBound_location")
    @ApiModelProperty("是否为入库货位")
    private int isInBoundLocation;		//是否为入库货位(0.否、1、是)
    
    @Column("wms_store_no")
    @ApiModelProperty("Wms货位编号")
    private String wmsStoreNo;

    @Column("task_lock")
    @ApiModelProperty("任务锁")
    private int taskLock;
}
