package com.prolog.eis.dto.station;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-19 11:33
 */
public class ContainerTaskDto {

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("站台号")
    private Integer stationId;

    @ApiModelProperty("订单号")
    private String orderBillId;

    @ApiModelProperty("拣选单号")
    private Integer pickOrderId;

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    @ApiModelProperty("任务类型")
    private Integer taskType;

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getOrderBillId() {
        return orderBillId;
    }

    public void setOrderBillId(String orderBillId) {
        this.orderBillId = orderBillId;
    }

    public Integer getPickOrderId() {
        return pickOrderId;
    }

    public void setPickOrderId(Integer pickOrderId) {
        this.pickOrderId = pickOrderId;
    }

    @Override
    public String toString() {
        return "ContainerTaskDto{" +
                "containerNo='" + containerNo + '\'' +
                ", stationId=" + stationId +
                ", orderBillId='" + orderBillId + '\'' +
                ", pickOrderId=" + pickOrderId +
                '}';
    }
}
