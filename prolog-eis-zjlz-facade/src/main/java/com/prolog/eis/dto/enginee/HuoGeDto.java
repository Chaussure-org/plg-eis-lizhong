package com.prolog.eis.dto.enginee;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HuoGeDto {
	
	private String huoGeNo;
	private int spId;
	private int spCount;
	
	// 货格所绑定的订单
	private Map<Integer, Integer> lxDingDanMxBindingMap;

	
	//TODO  szz新增
	private String liaoXiangNo;
	
	//以下非查询数据...............
	private LiaoXiangDto liaoXiang;
	
	public HuoGeDto() {
		this.lxDingDanMxBindingMap = new HashMap<Integer, Integer>();
	}
	
	public String getHuoGeNo() {
		return huoGeNo;
	}
	
	public void setHuoGeNo(String huoGeNo) {
		this.huoGeNo = huoGeNo;
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
	
	public Map<Integer, Integer> getLxDingDanMxBindingMap() {
		return lxDingDanMxBindingMap;
	}

	public void setLxDingDanMxBindingMap(Map<Integer, Integer> lxDingDanMxBindingMap) {
		this.lxDingDanMxBindingMap = lxDingDanMxBindingMap;
	}

	/**
	 * @return the liaoXiangNo
	 */
	public String getLiaoXiangNo() {
		return liaoXiangNo;
	}

	/**
	 * @param liaoXiangNo the liaoXiangNo to set
	 */
	public void setLiaoXiangNo(String liaoXiangNo) {
		this.liaoXiangNo = liaoXiangNo;
	}

	/**
	 * @return the liaoXiang
	 */
	public LiaoXiangDto getLiaoXiang() {
		return liaoXiang;
	}

	/**
	 * @param liaoXiang the liaoXiang to set
	 */
	public void setLiaoXiang(LiaoXiangDto liaoXiang) {
		this.liaoXiang = liaoXiang;
	}

	public int GetBindingLeaveCount() {
		int totalBindingCount = 0;
		for (Entry<Integer, Integer> entry : lxDingDanMxBindingMap.entrySet()) {
			totalBindingCount += entry.getValue();
		}

		return this.spCount - totalBindingCount;
	}
}
