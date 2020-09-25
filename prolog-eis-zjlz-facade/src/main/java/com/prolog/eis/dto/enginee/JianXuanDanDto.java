package com.prolog.eis.dto.enginee;



import com.prolog.eis.dto.dingdantaker.YunDanGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JianXuanDanDto {
	private int jxdId;
	private int zhanTaiId;
	private List<DingDanDto> ddList;
	private List<LiaoXiangDto> liangXiangList;// 料箱集合
	private int isAllLiaoXiangArrive;

	// ********以下非查询***********
	private HashSet<Integer> spKeyHs;

	public JianXuanDanDto() {
		this.ddList = new ArrayList<DingDanDto>();
		this.liangXiangList = new ArrayList<LiaoXiangDto>();
		this.spKeyHs = new HashSet<Integer>();
	}

	public int getJxdId() {
		return jxdId;
	}

	public void setJxdId(int jxdId) {
		this.jxdId = jxdId;
	}

	public int getIsAllLiaoXiangArrive() {
		return isAllLiaoXiangArrive;
	}

	public void setIsAllLiaoXiangArrive(int isAllLiaoXiangArrive) {
		this.isAllLiaoXiangArrive = isAllLiaoXiangArrive;
	}

	public List<DingDanDto> getDdList() {
		return ddList;
	}

	public void setDdList(List<DingDanDto> ddList) {
		this.ddList = ddList;
	}

	public List<LiaoXiangDto> getLiangXiangList() {
		return liangXiangList;
	}

	public void setLiangXiangList(List<LiaoXiangDto> liangXiangList) {
		this.liangXiangList = liangXiangList;
	}

	public int isAllLiaoXiangArrive() {
		return isAllLiaoXiangArrive;
	}

	public void setAllLiaoXiangArrive(int isAllLiaoXiangArrive) {
		this.isAllLiaoXiangArrive = isAllLiaoXiangArrive;
	}

	public int getZhanTaiId() {
		return zhanTaiId;
	}

	public void setZhanTaiId(int zhanTaiId) {
		this.zhanTaiId = zhanTaiId;
	}

	public HashSet<Integer> getSpKeyHs() {
		return spKeyHs;
	}

	public void setSpKeyHs(HashSet<Integer> spKeyHs) {
		this.spKeyHs = spKeyHs;
	}

	public Integer GetChuKuSpId(HashSet<Integer> chuKuFailSpIdSet) throws Exception {
		for (DingDanDto dd : this.getDdList()) {
			Integer spId = dd.GetChuKuSpId();
			if (spId != null) {
				// 判断出库失败的商品是否包含此商品，防止死循环
				if (!chuKuFailSpIdSet.contains(spId)) {
					return spId;
				}
			}
		}
		return null;
	}

	public boolean CheckIsAllBinding() throws Exception {
		for (DingDanDto dd : this.ddList) {
			if (!dd.CheckIsAllBinding())
				return false;
		}

		return true;
	}

	public void AddYunDan(DingDanDto dd) {
		this.ddList.add(dd);

		for (DingDanMxDto mx : dd.getDingDanMxList()) {
			if (!this.spKeyHs.contains(mx.getSpId()))
				this.spKeyHs.add(mx.getSpId());
		}
	}

	public void AddYunDanGroup(YunDanGroup ydGroup) {
		if (ydGroup.getYunDanList().size() == 0)
			return;
		this.ddList.addAll(ydGroup.getYunDanList());

		DingDanDto firstDingDan = ydGroup.getYunDanList().get(0);

		for (DingDanMxDto mx : firstDingDan.getDingDanMxList()) {
			if (!this.spKeyHs.contains(mx.getSpId()))
				this.spKeyHs.add(mx.getSpId());
		}
	}
}
