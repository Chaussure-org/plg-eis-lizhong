package com.prolog.eis.model;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description
 * @Author Hunter
 * @Date 2020-09-25
 */
@ApiModel("点位表")
@Table("point_location")
public class PointLocation {

    public static final int TYPE_IN_BCR = 1;
    @Column("point_id")
    @ApiModelProperty("POINTID")
    private String pointId;

    @Column("point_name")
    @ApiModelProperty("POINT名称")
    private String pointName;

    @Column("point_type")
    @ApiModelProperty("点位类型 1;-入库接驳口 2-出库接驳口 3-料箱bcr 4-订单箱bcr 5-料箱位 6-订单框位 7-外形检测 8-异常出库口 9-异常出库口")
    private Integer pointType;

    @Column("station_id")
    @ApiModelProperty("站台ID")
    private Integer stationId;

    @Column("point_area")
    @ApiModelProperty("POINT区域")
    private String pointArea;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getPointArea() {
        return pointArea;
    }

    public void setPointArea(String pointArea) {
        this.pointArea = pointArea;
    }

}
