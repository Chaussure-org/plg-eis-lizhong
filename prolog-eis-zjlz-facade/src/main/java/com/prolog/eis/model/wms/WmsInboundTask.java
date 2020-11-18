package com.prolog.eis.model.wms;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-09-25 
 */
@ApiModel ("入库任务表")
@Table ("wms_inbound_task")
public class WmsInboundTask {

  public static final int TYPE_INBOUND = 1; //入库上架
  public static final int TYPE_TRANSFER = 2;//移库上架
  public static final int TYPE_EMPTY = 3;//空托盘入库


  public static final int STATE_CREATE = 0;//创建
  public static final int STATE_START = 10;//开始
  public static final int STATE_FINISH = 90;//完成

  @Column("id")
  @Id
  @ApiModelProperty("id")
  private Integer id;

  @Column("branch_type")
  @ApiModelProperty("仓库类型")
  private String branchType;

  @Column("bill_type")
  @ApiModelProperty("任务类型(任务类型1 入库上架，2 移库上架，3 空托盘上架)")
  private Integer billType;

  @Column("line_id")
  @ApiModelProperty("任务行号")
  private String lineId;

  @Column("bill_no")
  @ApiModelProperty("单据编号")
  private String billNo;

  @Column("container_no")
  @ApiModelProperty("容器号")
  private String containerNo;

  @Column("seq_no")
  @ApiModelProperty("单据行号")
  private String seqNo;

  @Column("goods_id")
  @ApiModelProperty("商品编码")
  private Integer goodsId;

  @Column("goods_name")
  @ApiModelProperty("商品中文名称")
  private String goodsName;

  @Column("qty")
  @ApiModelProperty("数量")
  private Integer qty;

  @Column("case_specs")
  @ApiModelProperty("箱规 1箱里面多少个中包装")
  private Integer caseSpecs;

  @Column("box_specs")
  @ApiModelProperty("中包装规格  1包里面多少个单支（件）")
  private Integer boxSpecs;

  @Column("task_state")
  @ApiModelProperty("任务进度 0创建 10开始（进入库内） 90完成")
  private Integer taskState;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("start_time")
  @ApiModelProperty("开始入库时间")
  private java.util.Date startTime;

  @Column("complete_time")
  @ApiModelProperty("完成时间")
  private java.util.Date completeTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBranchType() {
    return branchType;
  }

  public void setBranchType(String branchType) {
    this.branchType = branchType;
  }

  public Integer getBillType() {
    return billType;
  }

  public void setBillType(Integer billType) {
    this.billType = billType;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getBillNo() {
    return billNo;
  }

  public void setBillNo(String billNo) {
    this.billNo = billNo;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(String seqNo) {
    this.seqNo = seqNo;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public Integer getCaseSpecs() {
    return caseSpecs;
  }

  public void setCaseSpecs(Integer caseSpecs) {
    this.caseSpecs = caseSpecs;
  }

  public Integer getBoxSpecs() {
    return boxSpecs;
  }

  public void setBoxSpecs(Integer boxSpecs) {
    this.boxSpecs = boxSpecs;
  }

  public Integer getTaskState() {
    return taskState;
  }

  public void setTaskState(Integer taskState) {
    this.taskState = taskState;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public java.util.Date getStartTime() {
    return startTime;
  }

  public void setStartTime(java.util.Date startTime) {
    this.startTime = startTime;
  }

  public java.util.Date getCompleteTime() {
    return completeTime;
  }

  public void setCompleteTime(java.util.Date completeTime) {
    this.completeTime = completeTime;
  }

}
