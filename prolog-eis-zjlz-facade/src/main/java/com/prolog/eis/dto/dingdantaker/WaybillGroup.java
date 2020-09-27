package com.prolog.eis.dto.dingdantaker;

import com.prolog.eis.dto.enginee.OrderDto;
import com.prolog.eis.dto.enginee.OrderMxDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author wangkang
 */
public class WaybillGroup {
	private String waybillSpKey;
	private HashSet<Integer> spIdHs;
	private List<OrderDto> waybillList;

	/**
	 * 匹配数量
	 */
	private int matchCount;

	/**
	 * 不匹配数量
	 */
	private int notMatchCount;

	/**
	 * 与其他站台商品品种重复数
	 */
	private int otherZtSameCount;

	public WaybillGroup() {
		this.spIdHs = new HashSet<Integer>();
		this.waybillList = new ArrayList<OrderDto>();
	}

	public String getWaybillSpKey() {
		return waybillSpKey;
	}

	public void setWaybillSpKey(String waybillSpKey) {
		this.waybillSpKey = waybillSpKey;
	}

	public HashSet<Integer> getSpIdHs() {
		return spIdHs;
	}

	public void setSpIdHs(HashSet<Integer> spIdHs) {
		this.spIdHs = spIdHs;
	}

	public List<OrderDto> getWaybillList() {
		return waybillList;
	}

	public void setWaybillList(List<OrderDto> waybillList) {
		this.waybillList = waybillList;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getNotMatchCount() {
		return notMatchCount;
	}

	public void setNotMatchCount(int notMatchCount) {
		this.notMatchCount = notMatchCount;
	}

	public int getOtherZtSameCount() {
		return otherZtSameCount;
	}

	public void setOtherZtSameCount(int otherZtSameCount) {
		this.otherZtSameCount = otherZtSameCount;
	}

	public void initSpIdHs(OrderDto yd) {
		this.setWaybillSpKey(yd.getWaybillSpKey());
		for (OrderMxDto mx : yd.getOrderMxList()) {
			if (!this.spIdHs.contains(mx.getSpId())) {
                this.spIdHs.add(mx.getSpId());
            }
		}
	}
}
