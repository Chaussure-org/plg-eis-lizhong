package com.prolog.eis.dto.log;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 20:20
 */
public class LogDto {

    public static final int WMS = 1;
    public static final int WCS = 2;
    public static final int SAS = 3;
    public static final int MCS = 4;
    public static final int RCS = 5;



    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("接口描述")
    private String descri;

    @ApiModelProperty("方法流向")
    private String direct;

    @ApiModelProperty("任务类型：1：入库 2：出库 3:移库 4:小车换层 5:输送线行走 \n" +
            "\t\t\t6-料箱进站 7-订单框进站 8-体积检测 9-入库口 10-订单箱到位 11-料箱到位")
    private Integer type;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("参数")
    private String params;

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("异常信息")
    private String exception;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("系统类型")
    private int systemType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    @Override
    public String toString() {
        return "LogDto{" +
                "id=" + id +
                ", descri='" + descri + '\'' +
                ", direct='" + direct + '\'' +
                ", type=" + type +
                ", methodName='" + methodName + '\'' +
                ", params='" + params + '\'' +
                ", success=" + success +
                ", exception='" + exception + '\'' +
                ", createTime=" + createTime +
                ", systemType=" + systemType +
                '}';
    }
}
