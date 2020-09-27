package com.prolog.eis.dto.enginee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangkang
 */
public class ContainerDto {
	public ContainerDto() {
		super();
	}

	private int pickOrderId;
	/**
	 * 库存ID
	 */
	private Integer boxLibStockId;
	/**
	 * 站台ID
	 */
	private Integer stationId;
	/**
	 * 托盘号
	 */
	private String containerNo;
	private int spId;
	private int spCount;
	/**
	 * 	料箱所绑定的订单 key orderBindMxId ORDER_DETAIL_QTY
 	 */
	private Map<Integer, Integer> containerOrderBindingMap;

	//TODO ADD添加状态
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 货格号
	 */
	private String subContainerNo;

	public Integer getBoxLibStockId() {
		return boxLibStockId;
	}

	public void setBoxLibStockId(Integer boxLibStockId) {
		this.boxLibStockId = boxLibStockId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}


	public int getPickOrderId() {
		return pickOrderId;
	}

	public void setPickOrderId(int pickOrderId) {
		this.pickOrderId = pickOrderId;
	}

	public Map<Integer, Integer> getContainerOrderBindingMap() {
		return containerOrderBindingMap;
	}

	public void setContainerOrderBindingMap(Map<Integer, Integer> containerOrderBindingMap) {
		this.containerOrderBindingMap = containerOrderBindingMap;
	}

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public int getSpCount() {
		return spCount;
	}

	public void setSpCount(int spCount) {
		this.spCount = spCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSubContainerNo() {
		return subContainerNo;
	}

	public void setSubContainerNo(String subContainerNo) {
		this.subContainerNo = subContainerNo;
	}

	public int getBindingLeaveCount() {
		int totalBindingCount = 0;
		for (Map.Entry<Integer, Integer> entry : containerOrderBindingMap.entrySet()) {
			totalBindingCount += entry.getValue();
		}

		return this.spCount - totalBindingCount;
	}
}
