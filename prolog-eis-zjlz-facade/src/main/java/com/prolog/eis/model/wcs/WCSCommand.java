package com.prolog.eis.model.wcs;

import com.prolog.framework.core.annotation.AutoKey;
import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;

import java.util.Date;

@Table("wcs_command")
public class WCSCommand {
    @Id
    @AutoKey(type=AutoKey.TYPE_IDENTITY)
    private int id;
    @Column("task_id")
    private String taskId;
    @Column("type")
    private int type;
    @Column("address")
    private String address;
    @Column("target")
    private String target;
    @Column("container_no")
    private String containerNo;
    @Column("weight")
    private String weight;
    @Column("priority")
    private String priority;
    @Column("station_no")
    private String stationNo;
    @Column("lights")
    private String lights;
    @Column("source_layer")
    private int sourceLayer;
    @Column("target_layer")
    private int targetLayer;
    @Column("bank_id")
    private int bankId;
    @Column("gmt_create_time")
    private Date createTime;
    @Column("status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WCSCommand(){
        this.createTime = new Date();
    }

    public WCSCommand(String taskId, int type, String address, String target, String containerNo, int status) {
        this.taskId = taskId;
        this.type = type;
        this.address = address;
        this.target = target;
        this.containerNo = containerNo;
        this.status = status;
        this.createTime = new Date();
    }

    public WCSCommand(String taskId, int type, String address, String target, String containerNo) {
        this.taskId = taskId;
        this.type = type;
        this.address = address;
        this.target = target;
        this.containerNo = containerNo;
        this.createTime = new Date();
    }

    public WCSCommand(int id, String taskId, int type, String address, String target, String containerNo, String weight, String priority, String stationNo, String lights, int sourceLayer, int targetLayer, int bankId) {
        this.id = id;
        this.taskId = taskId;
        this.type = type;
        this.address = address;
        this.target = target;
        this.containerNo = containerNo;
        this.weight = weight;
        this.priority = priority;
        this.stationNo = stationNo;
        this.lights = lights;
        this.sourceLayer = sourceLayer;
        this.targetLayer = targetLayer;
        this.bankId = bankId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public int getSourceLayer() {
        return sourceLayer;
    }

    public void setSourceLayer(int sourceLayer) {
        this.sourceLayer = sourceLayer;
    }

    public int getTargetLayer() {
        return targetLayer;
    }

    public void setTargetLayer(int targetLayer) {
        this.targetLayer = targetLayer;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public WCSHistoryCommand getHistoryCommand(){
        WCSHistoryCommand his = new WCSHistoryCommand();
        his.setAddress(this.getAddress());
        his.setBankId(this.getBankId());
        his.setContainerNo(this.getContainerNo());
        his.setCreateTime(this.getCreateTime());
        his.setLights(this.getLights());
        his.setPriority(this.getPriority());
        his.setSourceLayer(this.getSourceLayer());
        his.setStationNo(this.getStationNo());
        his.setTarget(this.getTarget());
        his.setTargetLayer(this.getTargetLayer());
        his.setTaskId(this.getTaskId());
        his.setType(this.getType());
        his.setWeight(this.getWeight());
        return his;
    }
}
