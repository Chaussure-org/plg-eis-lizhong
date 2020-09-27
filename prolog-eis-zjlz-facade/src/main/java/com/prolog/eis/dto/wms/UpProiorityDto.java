package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 15:06
 */
public class UpProiorityDto {

    @ApiModelProperty("单据号")
    private String billNo;

    @ApiModelProperty("单据日期")
    private Date billDate;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("时间戳")
    private Date sjc;

    @ApiModelProperty("单据类型")
    private Integer billType;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSjc() {
        return sjc;
    }

    public void setSjc(Date sjc) {
        this.sjc = sjc;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    @Override
    public String toString() {
        return "UpProiorityDto{" +
                "billNo='" + billNo + '\'' +
                ", billDate=" + billDate +
                ", status=" + status +
                ", sjc=" + sjc +
                ", billType=" + billType +
                '}';
    }
}
