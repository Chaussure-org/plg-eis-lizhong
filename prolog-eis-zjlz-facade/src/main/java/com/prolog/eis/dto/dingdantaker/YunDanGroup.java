package com.prolog.eis.dto.dingdantaker;

import com.prolog.eis.dto.enginee.DingDanDto;
import com.prolog.eis.dto.enginee.DingDanMxDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class YunDanGroup {
	private String yunDanSPKey;
	private HashSet<Integer> spIdHS;
	private List<DingDanDto> yunDanList;

	/// <summary>
	/// 匹配数量
	/// </summary>
	private int matchCount;

	/// <summary>
	/// 不匹配数量
	/// </summary>
	private int notMatchCount;

	/// <summary>
	/// 与其他站台商品品种重复数
	/// </summary>
	private int otherZtSameCount;

	public YunDanGroup() {
		this.spIdHS = new HashSet<Integer>();
		this.yunDanList = new ArrayList<DingDanDto>();

//		try {
//			this.minShiXiaoTime = new SimpleDateFormat("yyyy-MM-dd").parse("9999-01-01");
//		} catch (ParseException e) {
//			
//		}
	}

	public String getYunDanSPKey() {
		return yunDanSPKey;
	}

	public void setYunDanSPKey(String yunDanSPKey) {
		this.yunDanSPKey = yunDanSPKey;
	}

	public HashSet<Integer> getSpIdHS() {
		return spIdHS;
	}

	public void setSpIdHS(HashSet<Integer> spIdHS) {
		this.spIdHS = spIdHS;
	}

	public List<DingDanDto> getYunDanList() {
		return yunDanList;
	}

	public void setYunDanList(List<DingDanDto> yunDanList) {
		this.yunDanList = yunDanList;
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

	public void initSPIdHS(DingDanDto yd) {
		this.setYunDanSPKey(yd.getYunDanSPKey());
		for (DingDanMxDto mx : yd.getDingDanMxList()) {
			if (!this.spIdHS.contains(mx.getSpId()))
				this.spIdHS.add(mx.getSpId());
		}
	}
}
