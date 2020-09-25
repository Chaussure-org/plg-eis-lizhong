package com.prolog.eis.dto.enginee;

import java.util.Date;

public class OutStoreHuoWeiDto {

	private int storeId;
	
	private String containerNo;
	
	private String huoWeiId;
	
	private int x;
	
	private int y;
	
	private int distance;
	
	private Date expiry;

	
	
	/**
	 * @return the storeId
	 */
	public int getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the containerNo
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * @param containerNo the containerNo to set
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * @return the huoWeiId
	 */
	public String getHuoWeiId() {
		return huoWeiId;
	}

	/**
	 * @param huoWeiId the huoWeiId to set
	 */
	public void setHuoWeiId(String huoWeiId) {
		this.huoWeiId = huoWeiId;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return the expiry
	 */
	public Date getExpiry() {
		return expiry;
	}

	public OutStoreHuoWeiDto(int storeId, String containerNo, String huoWeiId, int x, int y, int distance,
			Date expiry) {
		super();
		this.storeId = storeId;
		this.containerNo = containerNo;
		this.huoWeiId = huoWeiId;
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.expiry = expiry;
	}

	public OutStoreHuoWeiDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
