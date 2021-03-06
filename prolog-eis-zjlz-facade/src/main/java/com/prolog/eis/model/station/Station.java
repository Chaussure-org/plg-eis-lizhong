package com.prolog.eis.model.station;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description
 * @Author Hunter
 * @Date 2020-10-15
 */
@ApiModel("站台表")
@Table("station")
public class Station {
    /**
     * 站台作业类型：播种
     */
    public static final int TASK_TYPE_SEED = 20;
    /**
     * 站台作业类型：盘点
     */
    public static final int TASK_TYPE_INVENTORY = 10;

    /**
     * 站台作业类型：空闲
     */
    public static final int TASK_TYPE_EMPTY= 0;

    /**
     * 半成品库
     */
    public static final int STATION_TYPE_UNFINISHEDPROD = 1;
    /**
     * 成品库
     */
    public static final int STATION_TYPE_FINISHEDPROD = 2;

    /**
     * 铁笼站台
     */
    public static final int STATION_TYPE_IRON = 3;

    public static final int UN_LOCK = 0;
    public static final int LOCK = 1;
    @Column("id")
    @Id
    @ApiModelProperty("站台ID")
    private Integer id;

    @Column("current_station_pick_id")
    @ApiModelProperty("当前拣选单ID")
    private Integer currentStationPickId;

    @Column("is_lock")
    @ApiModelProperty("是否锁定 0不锁定（索取）  1锁定（不索取）")
    private Integer isLock;

    @Column("picking_user_id")
    @ApiModelProperty("拣选人员ID")
    private String pickingUserId;

    @Column("picking_user_name")
    @ApiModelProperty("拣选人员名称")
    private String pickingUserName;

    @Column("seed_user_id")
    @ApiModelProperty("播种人员ID")
    private String seedUserId;

    @Column("seed_user_name")
    @ApiModelProperty("播种人员名称")
    private String seedUserName;

    @Column("max_order_count")
    @ApiModelProperty("最大订单数量")
    private Integer maxOrderCount;

    @Column("station_task_type")
    @ApiModelProperty("站台作业类型 0 - 空闲  10- 盘点 20 - 播种")
    private Integer stationTaskType;

    @Column("update_time")
    @ApiModelProperty("修改时间")
    private java.util.Date updateTime;

    @Column("container_no")
    @ApiModelProperty("料箱编号（拣选站有料箱有值）")
    private String containerNo;

    @Column("station_type")
    @ApiModelProperty("站台类型（1-半成品站台，2-成品站台 3- 铁笼站台））")
    private Integer stationType;

    @Column("station_ip")
    @ApiModelProperty("站台ip")
    private String stationIp;

    @Column("max_cache_position")
    @ApiModelProperty("最大缓存位数量")
    private Integer maxCachePosition;

    @Column("current_num")
    @ApiModelProperty("当前占用缓存数量")
    private Integer currentNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrentStationPickId() {
        return currentStationPickId;
    }

    public void setCurrentStationPickId(Integer currentStationPickId) {
        this.currentStationPickId = currentStationPickId;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getPickingUserId() {
        return pickingUserId;
    }

    public void setPickingUserId(String pickingUserId) {
        this.pickingUserId = pickingUserId;
    }

    public String getPickingUserName() {
        return pickingUserName;
    }

    public void setPickingUserName(String pickingUserName) {
        this.pickingUserName = pickingUserName;
    }

    public String getSeedUserId() {
        return seedUserId;
    }

    public void setSeedUserId(String seedUserId) {
        this.seedUserId = seedUserId;
    }

    public String getSeedUserName() {
        return seedUserName;
    }

    public void setSeedUserName(String seedUserName) {
        this.seedUserName = seedUserName;
    }

    public Integer getMaxOrderCount() {
        return maxOrderCount;
    }

    public void setMaxOrderCount(Integer maxOrderCount) {
        this.maxOrderCount = maxOrderCount;
    }

    public Integer getStationTaskType() {
        return stationTaskType;
    }

    public void setStationTaskType(Integer stationTaskType) {
        this.stationTaskType = stationTaskType;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getStationType() {
        return stationType;
    }

    public void setStationType(Integer stationType) {
        this.stationType = stationType;
    }

    public String getStationIp() {
        return stationIp;
    }

    public void setStationIp(String stationIp) {
        this.stationIp = stationIp;
    }

    public Integer getMaxCachePosition() {
        return maxCachePosition;
    }

    public void setMaxCachePosition(Integer maxCachePosition) {
        this.maxCachePosition = maxCachePosition;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }
}

