package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BCRDataDTO {

    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("任务类型，6-料箱进站 7-订单框进站 8-体积检测 9-入库口")
    private short type;
    @ApiModelProperty("箱号")
    private String containerNo;
    @ApiModelProperty("外形检测结果")
    private boolean shapeInspect;
    @ApiModelProperty("外形检测结果描述")
    private String shapeInspectDesc;
    @ApiModelProperty("重量检测结果，数字字符串")
    private String weightInspect;
    @ApiModelProperty("容器当前坐标点")
    private String address;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public boolean isShapeInspect() {
        return shapeInspect;
    }

    public void setShapeInspect(boolean shapeInspect) {
        this.shapeInspect = shapeInspect;
    }

    public String getShapeInspectDesc() {
        return shapeInspectDesc;
    }

    public void setShapeInspectDesc(String shapeInspectDesc) {
        this.shapeInspectDesc = shapeInspectDesc;
    }

    public String getWeightInspect() {
        return weightInspect;
    }

    public void setWeightInspect(String weightInspect) {
        this.weightInspect = weightInspect;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BCRDataDTO{" +
                "taskId='" + taskId + '\'' +
                ", type=" + type +
                ", containerNo='" + containerNo + '\'' +
                ", shapeInspect=" + shapeInspect +
                ", shapeInspectDesc='" + shapeInspectDesc + '\'' +
                ", weightInspect='" + weightInspect + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
