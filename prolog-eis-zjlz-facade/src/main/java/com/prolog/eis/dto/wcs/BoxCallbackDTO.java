package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings({"ALL", "AlibabaPojoMustOverrideToString"})
@ApiModel
public class BoxCallbackDTO {
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("任务类型 10-订单箱到位 11-料箱到位")
    private short type;
    @ApiModelProperty("箱号")
    private String containerNo;
    @ApiModelProperty("当前位置坐标")
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
