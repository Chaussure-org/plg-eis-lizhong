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
@ApiModel("站台表")
@Table("station")
public class Station {
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
    @ApiModelProperty("是否锁定")
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

}
