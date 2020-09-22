package com.prolog.eis.model.store;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Ignore;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@Table("SX_STORE_LOCATION")
public class SxStoreLocation {

    @Id
    @ApiModelProperty("货位ID")
    private int id;        //货位ID

    @Column("store_no")
    @ApiModelProperty("货位编号")
    private String storeNo;

    @Column("store_location_group_id")
    @ApiModelProperty("货位组ID")
    private int storeLocationGroupId;        //货位组ID

    @Column("layer")
    @ApiModelProperty("层")
    private int layer;        //层

    @Column("x")
    @ApiModelProperty("X")
    private int x;        //X

    @Column("y")
    @ApiModelProperty("Y")
    private int y;        //Y

    @Column("store_location_id1")
    @ApiModelProperty("相邻货位ID1")
    private Integer storeLocationId1;        //相邻货位ID1

    @Column("store_location_id2")
    @ApiModelProperty("相邻货位ID2")
    private Integer storeLocationId2;        //相邻货位ID2


    @Column("ascent_lock_state")
    @ApiModelProperty("货位组升位锁")
    private int ascentLockState;        //货位组升位锁

    @Column("location_index")
    @ApiModelProperty("货位组位置索引(从上到下、从左到右)")
    private int locationIndex;        //货位组位置索引(从上到下、从左到右)

    @Column("dept_num")
    @ApiModelProperty("移库数")
    private int deptNum;

    @Column("depth")
    @ApiModelProperty("深度")
    private int depth;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;        //创建时间
    
    @Column("vertical_location_group_id")
    @ApiModelProperty("垂直货位Id")
    private Integer verticalLocationGroupId;
    
    @Column("actual_weight")
    @ApiModelProperty("实际重量")
    private Double actualWeight;		//实际重量

    @Column("limit_weight")
    @ApiModelProperty("限重")
    private Double limitWeight;		//限重
    
    @Column("is_inbound_location")
    @ApiModelProperty("是否为入库货位")
    private int isInBoundLocation;		//是否为入库货位(0.否、1、是)
    
    @Column("wms_store_no")
    @ApiModelProperty("Wms货位编号")
    private String wmsStoreNo;



	@Ignore
	@ApiModelProperty("方向编号 02 03")
	private String directionCoding;

	@Column("is_exception")
	@ApiModelProperty("是否为异常箱位")
	private Boolean isException;

	public Boolean getException() {
		return isException;
	}

	public void setException(Boolean exception) {
		isException = exception;
	}

	public SxStoreLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	public String getDirectionCoding() {
		return directionCoding;
	}

	public void setDirectionCoding(String directionCoding) {
		this.directionCoding = directionCoding;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public int getStoreLocationGroupId() {
		return storeLocationGroupId;
	}

	public void setStoreLocationGroupId(int storeLocationGroupId) {
		this.storeLocationGroupId = storeLocationGroupId;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Integer getStoreLocationId1() {
		return storeLocationId1;
	}

	public void setStoreLocationId1(Integer storeLocationId1) {
		this.storeLocationId1 = storeLocationId1;
	}

	public Integer getStoreLocationId2() {
		return storeLocationId2;
	}

	public void setStoreLocationId2(Integer storeLocationId2) {
		this.storeLocationId2 = storeLocationId2;
	}

	public int getAscentLockState() {
		return ascentLockState;
	}

	public void setAscentLockState(int ascentLockState) {
		this.ascentLockState = ascentLockState;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public int getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(int deptNum) {
		this.deptNum = deptNum;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getVerticalLocationGroupId() {
		return verticalLocationGroupId;
	}

	public void setVerticalLocationGroupId(Integer verticalLocationGroupId) {
		this.verticalLocationGroupId = verticalLocationGroupId;
	}

	public Double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public Double getLimitWeight() {
		return limitWeight;
	}

	public void setLimitWeight(Double limitWeight) {
		this.limitWeight = limitWeight;
	}

	public int getIsInBoundLocation() {
		return isInBoundLocation;
	}

	public void setIsInBoundLocation(int isInBoundLocation) {
		this.isInBoundLocation = isInBoundLocation;
	}

	public String getWmsStoreNo() {
		return wmsStoreNo;
	}

	public void setWmsStoreNo(String wmsStoreNo) {
		this.wmsStoreNo = wmsStoreNo;
	}

}
