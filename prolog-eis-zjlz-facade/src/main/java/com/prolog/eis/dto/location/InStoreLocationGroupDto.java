package com.prolog.eis.dto.location;

public class InStoreLocationGroupDto {

	private int storeLocationGroupId;	//货位组Id
	private int containerCount;		//货位组容器数量
	private int inOutNum;		//出入口数量
	private int distance;		//离原点的距离
	private int reservedLocation;		//货位类型（：1、空托盘垛货位组，2、理货预留货位组，3、融合/HUB货位组，4、MIT货位组）
	private int x;
	private int y;
	private int subtract;		//托盘数减去出口数
	private String entrance1Property1;
	private String entrance1Property2;
	private String entrance2Property1;
	private String entrance2Property2;


	private int layer;
	
	public int getStoreLocationGroupId() {
		return storeLocationGroupId;
	}
	public void setStoreLocationGroupId(int storeLocationGroupId) {
		this.storeLocationGroupId = storeLocationGroupId;
	}
	public int getContainerCount() {
		return containerCount;
	}
	public void setContainerCount(int containerCount) {
		this.containerCount = containerCount;
	}
	public int getInOutNum() {
		return inOutNum;
	}
	public void setInOutNum(int inOutNum) {
		this.inOutNum = inOutNum;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getReservedLocation() {
		return reservedLocation;
	}
	public void setReservedLocation(int reservedLocation) {
		this.reservedLocation = reservedLocation;
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
	public int getSubtract() {
		return subtract;
	}
	public void setSubtract(int subtract) {
		this.subtract = subtract;
	}
	public String getEntrance1Property1() {
		return entrance1Property1;
	}
	public void setEntrance1Property1(String entrance1Property1) {
		this.entrance1Property1 = entrance1Property1;
	}
	public String getEntrance1Property2() {
		return entrance1Property2;
	}
	public void setEntrance1Property2(String entrance1Property2) {
		this.entrance1Property2 = entrance1Property2;
	}
	public String getEntrance2Property1() {
		return entrance2Property1;
	}
	public void setEntrance2Property1(String entrance2Property1) {
		this.entrance2Property1 = entrance2Property1;
	}
	public String getEntrance2Property2() {
		return entrance2Property2;
	}
	public void setEntrance2Property2(String entrance2Property2) {
		this.entrance2Property2 = entrance2Property2;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
}
