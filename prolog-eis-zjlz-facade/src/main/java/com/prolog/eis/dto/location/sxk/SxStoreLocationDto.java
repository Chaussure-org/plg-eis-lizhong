package com.prolog.eis.dto.location.sxk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SxStoreLocationDto {

	private int id;        //货位ID

    @ApiModelProperty("货位编号")
    private String storeNo;

    @ApiModelProperty("货位组ID")
    private int storeLocationGroupId;        //货位组ID

    @ApiModelProperty("层")
    private int layer;        //层

    @ApiModelProperty("X")
    private int x;        //X

    @ApiModelProperty("Y")
    private int y;        //Y

    @ApiModelProperty("相邻货位ID1")
    private Integer storeLocationId1;        //相邻货位ID1

    @ApiModelProperty("相邻货位ID2")
    private Integer storeLocationId2;        //相邻货位ID2

    @ApiModelProperty("货位组升位锁")
    private int ascentLockState;        //货位组升位锁

    @ApiModelProperty("货位组位置索引(从上到下、从左到右)")
    private int locationIndex;        //货位组位置索引(从上到下、从左到右)

    @ApiModelProperty("移库数")
    private int deptNum;

    @ApiModelProperty("深度")
    private int depth;

    @ApiModelProperty("创建时间")
    private Date createTime;        //创建时间
    
    @ApiModelProperty("垂直货位Id")
    private Integer verticalLocationGroupId;
    
    @ApiModelProperty("实际重量")
    private Double actualWeight;		//实际重量

    @ApiModelProperty("限重")
    private Double limitWeight;		//限重
    
    @ApiModelProperty("是否为入库货位")
    private int isInBoundLocation;		//是否为入库货位(0.否、1、是)
    
    @ApiModelProperty("Wms货位编号")
    private String wmsStoreNo;

    @ApiModelProperty("任务锁")
    private int taskLock;
    
    @ApiModelProperty("原货位 托盘号")
    private String sourceContainerNo;
    
    @ApiModelProperty("目标货位 托盘号")
    private String targetContainerNo;
}
