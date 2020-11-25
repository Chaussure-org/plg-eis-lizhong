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
@ApiModel("托盘库存表")
@Table("container_store")
public class ContainerStore {

    public static final Integer TASK_TYPE_INBOUND = 10;

    /**
     * 出库
     */
    public static final Integer TASK_TYPE_OUTBOUND = 20;
    /**
     * 盘点出库
     */
    public static final Integer TASK_TYPE_INVENTORY_OUTBOUND = 21;
    public static final Integer TASK_TYPE_MOVE = 22;
    public static final Integer TASK_TYPE_BACK = 23;


    /**
     * 任务状态：入库
     */
    public static final int TASK_STATUS_IN = 10;

    /**
     * 任务状态：出库
     */
    public static final int TASK_STATUS_OUT = 20;

    public static final int EMPTY_BOX= -1;
    public static final int EMPTY_TRAY = -2;
    @Column("id")
    @Id
    @ApiModelProperty("托盘库存ID")
    private Integer id;

    @Column("container_no")
    @ApiModelProperty("托盘号")
    private String containerNo;


    @Column("task_type")
    @ApiModelProperty("任务类型(0无业务任务;10;入库;11补货入库;12移库入库;20出库;21盘点出库;22移库出库)")
    private Integer taskType;

    @Column("task_status")
    @ApiModelProperty("任务类型(0 无任务 10 入库中 20 出库中)")
    private Integer taskStatus;


    @Column("work_count")
    @ApiModelProperty("作业次数")
    private Integer workCount;

    @Column("owner_id")
    @ApiModelProperty("业主")
    private String ownerId;

    @Column("goods_id")
    @ApiModelProperty("商品ID")
    private Integer goodsId;

    @Column("lot_id")
    @ApiModelProperty("批次id")
    private String lotId;

    @Column("lot")
    @ApiModelProperty("批次")
    private String lot;

    @Column("goods_order_no")
    @ApiModelProperty("商品订单号")
    private String goodsOrderNo;

    @Column("qty")
    @ApiModelProperty("库存数量")
    private Integer qty;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;

    @Column("update_time")
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Integer workCount) {
        this.workCount = workCount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getGoodsOrderNo() {
        return goodsOrderNo;
    }

    public void setGoodsOrderNo(String goodsOrderNo) {
        this.goodsOrderNo = goodsOrderNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}
