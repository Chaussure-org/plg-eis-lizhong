package com.prolog.eis.model.wcs;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;

import java.util.Date;

/**
 * 记录正在执行的任务
 */
@Table("wcs_task")
public class WCSTask {

    public static final int STATUS_WAITTING = 0;//未开始
    public static final int STATUS_RUNNING = 1;//执行中
    public static final int STATUS_FINISH = 2;//已完成
    public static final int STATUS_FAILURE = 3;//失败

    @Id
    @Column("id")
    private String id;
    @Column("address")
    private String address;
    @Column("target")
    private String target;
    @Column("eis_type")
    private int eisType;
    @Column("wcs_type")
    private int wcsType;
    @Column("station_id")
    private int stationId;
    @Column("container_no")
    private String containerNo;
    @Column("order_box_no")
    private String orderBoxNo;
    @Column("status")
    private int status;
    @Column("gmt_create_time")
    private Date gmtCreateTime;
    @Column("gmt_start_time")
    private Date gmtStartTime;
    @Column("gmt_finish_time")
    private Date gmtFinishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getEisType() {
        return eisType;
    }

    public void setEisType(int eisType) {
        this.eisType = eisType;
    }

    public int getWcsType() {
        return wcsType;
    }

    public void setWcsType(int wcsType) {
        this.wcsType = wcsType;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getOrderBoxNo() {
        return orderBoxNo;
    }

    public void setOrderBoxNo(String orderBoxNo) {
        this.orderBoxNo = orderBoxNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getGmtCreateTime() {
        return gmtCreateTime;
    }

    public void setGmtCreateTime(Date gmtCreateTime) {
        this.gmtCreateTime = gmtCreateTime;
    }

    public Date getGmtStartTime() {
        return gmtStartTime;
    }

    public void setGmtStartTime(Date gmtStartTime) {
        this.gmtStartTime = gmtStartTime;
    }

    public Date getGmtFinishTime() {
        return gmtFinishTime;
    }

    public void setGmtFinishTime(Date gmtFinishTime) {
        this.gmtFinishTime = gmtFinishTime;
    }

    public WCSHistoryTask getHistoryTask(){
        WCSHistoryTask task = new WCSHistoryTask();
        task.setAddress(this.address);
        task.setContainerNo(this.containerNo);
        task.setEisType(this.eisType);
        task.setGmtCreateTime(this.gmtCreateTime);
        task.setGmtFinishTime(this.gmtFinishTime);
        task.setGmtStartTime(this.gmtStartTime);
        task.setId(this.id);
        task.setOrderBoxNo(this.orderBoxNo);
        task.setStationId(this.stationId);
        task.setStatus(this.status);
        task.setTarget(this.target);
        task.setWcsType(this.wcsType);
        return task;
    }

}
