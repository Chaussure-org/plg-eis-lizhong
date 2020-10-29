package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/28 10:57
 * 盘点出库dto
 */
public class InventoryOutDto {

    @ApiModelProperty("容器编号")
    private String containerNo;

    @ApiModelProperty("层")
    private Integer layer;

    @ApiModelProperty("区域")
    private String areaNo;


    @ApiModelProperty("移位数")
    private Integer deptNum;

    @ApiModelProperty("盘点创建时间")
    private Date createTime;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public Integer getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(Integer deptNum) {
        this.deptNum = deptNum;
    }

    @Override
    public String toString() {
        return "InventoryOutDto{" +
                "containerNo='" + containerNo + '\'' +
                ", layer=" + layer +
                ", areaNo='" + areaNo + '\'' +
                ", deptNum=" + deptNum +
                ", createTime=" + createTime +
                '}';
    }
}
