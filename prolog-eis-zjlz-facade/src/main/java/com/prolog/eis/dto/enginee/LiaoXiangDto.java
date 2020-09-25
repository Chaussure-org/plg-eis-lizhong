package com.prolog.eis.dto.enginee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LiaoXiangDto {
	public LiaoXiangDto() {
		super();
	}

	private int jxdId;
	private Integer xkKuCunId;		//库存ID
	private Integer stationId;		//站台ID
	private String liaoXiangNo;		//托盘号
	private int spId;
	private int spCount;
	// 料箱所绑定的订单 key orderBindMxId ORDER_DETAIL_QTY
	private Map<Integer, Integer> lxDingDanMxBindingMap;

	//TODO ADD添加状态
	private int status;		//状态
	private String subContainerNo;  //料箱号

	public Integer getXkKuCunId() {
		return xkKuCunId;
	}

	public void setXkKuCunId(Integer xkKuCunId) {
		this.xkKuCunId = xkKuCunId;
	}


	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getLiaoXiangNo() {
		return liaoXiangNo;
	}

	public void setLiaoXiangNo(String liaoXiangNo) {
		this.liaoXiangNo = liaoXiangNo;
	}

	public int getJxdId() {
		return jxdId;
	}

	public void setJxdId(int jxdId) {
		this.jxdId = jxdId;
	}

	public Map<Integer, Integer> getLxDingDanMxBindingMap() {
		return lxDingDanMxBindingMap;
	}

	public void setLxDingDanMxBindingMap(Map<Integer, Integer> lxDingDanMxBindingMap) {
		this.lxDingDanMxBindingMap = lxDingDanMxBindingMap;
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

	public int GetBindingLeaveCount() {
		int totalBindingCount = 0;
		for (Map.Entry<Integer, Integer> entry : lxDingDanMxBindingMap.entrySet()) {
			totalBindingCount += entry.getValue();
		}

		return this.spCount - totalBindingCount;
	}
}
