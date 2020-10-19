package com.prolog.eis.model.log;

import com.prolog.framework.core.annotation.AutoKey;
import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 20:20
 */
@Table("mcs_log")
public class McsLog {

    @Id
    @Column("id")
    @ApiModelProperty("id")
    @AutoKey(type = AutoKey.TYPE_IDENTITY)
    private Integer id;

    @Column("descri")
    @ApiModelProperty("接口描述")
    private String descri;

    @Column("direct")
    @ApiModelProperty("方法流向")
    private String direct;

    @Column("type")
    @ApiModelProperty("任务类型：1：入库 2：出库 3:移库 4:小车换层 5:输送线行走 \n" +
            "\t\t\t6-料箱进站 7-订单框进站 8-体积检测 9-入库口 10-订单箱到位 11-料箱到位")
    private Integer type;

    @Column("method_name")
    @ApiModelProperty("方法名")
    private String methodName;

    @Column("params")
    @ApiModelProperty("参数")
    private String params;

    @Column("success")
    @ApiModelProperty("是否成功")
    private Boolean success;

    @Column("exception")
    @ApiModelProperty("异常信息")
    private String exception;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column("system_type")
    @ApiModelProperty("系统类型")
    private int systemType;

    @Column("host_port")
    @ApiModelProperty("本机IP和PORT")
    private String hostPort;

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

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

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }

    @Override
    public String toString() {
        return "McsLog{" +
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
                ", hostPort=" + hostPort +
                '}';
    }
}
