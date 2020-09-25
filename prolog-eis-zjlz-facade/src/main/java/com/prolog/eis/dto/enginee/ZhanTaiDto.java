package com.prolog.eis.dto.enginee;

import java.util.ArrayList;
import java.util.List;

public class ZhanTaiDto {
	public static final int STATUS_LOCK = 1;
	public static final int STATUS_UNLOCK = 2;
	public ZhanTaiDto() {
		this.jxdList = new ArrayList<JianXuanDanDto>();
	}

	private int zhanTaiId;
	// 是否锁定 2-unlock
	private int isLock;

	// 1、播种作业。2、停止索取。3、盘点作业
	private int isClaim;

	// 在途料箱最大缓存数
	private int maxLxCacheCount;

	private int arriveLxCount;

	private int chuKuLxCount;

	// 拣选单集合
	private List<JianXuanDanDto> jxdList;

	// ***********以下非查询数据***********
	// 需要出库的拣选单
	private JianXuanDanDto needChuKuJXD;

	public int getArriveLxCount() {
		return arriveLxCount;
	}

	public void setArriveLxCount(int arriveLxCount) {
		this.arriveLxCount = arriveLxCount;
	}

	public int getChuKuLxCount() {
		return chuKuLxCount;
	}

	public void setChuKuLxCount(int chuKuLxCount) {
		this.chuKuLxCount = chuKuLxCount;
	}

	public int getZhanTaiId() {
		return zhanTaiId;
	}

	public void setZhanTaiId(int zhanTaiId) {
		this.zhanTaiId = zhanTaiId;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public int getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(int isClaim) {
		this.isClaim = isClaim;
	}

	public int getMaxLxCacheCount() {
		return maxLxCacheCount;
	}

	public void setMaxLxCacheCount(int maxLxCacheCount) {
		this.maxLxCacheCount = maxLxCacheCount;
	}

	public JianXuanDanDto getNeedChuKuJXD() {
		return needChuKuJXD;
	}

	public void setNeedChuKuJXD(JianXuanDanDto needChuKuJXD) {
		this.needChuKuJXD = needChuKuJXD;
	}

	public List<JianXuanDanDto> getJxdList() {
		return jxdList;
	}

	public void setJxdList(List<JianXuanDanDto> jxdList) {
		this.jxdList = jxdList;
	}

	public boolean CheckIsAllBinding() throws Exception {
		for (JianXuanDanDto jxd : this.getJxdList()) {
			if (!jxd.CheckIsAllBinding())
				return false;
		}
		return true;
	}

	// 检查站台所需的料箱是否已经全部到达
	public boolean CheckIsAllLXArrive() {
		for (JianXuanDanDto jxd : this.getJxdList()) {
			if (jxd.getIsAllLiaoXiangArrive() != 1)
				return false;
		}
		return true;
	}

	public int ComputeLiaoXiangCount() {
		int ztLxCount = 0;
		for (JianXuanDanDto jxd : this.getJxdList()) {
			ztLxCount += jxd.getLiangXiangList().size();
		}
		return ztLxCount;
	}

	public boolean IsLiaoXiangLimitMax() {
		int totalLxCount = this.chuKuLxCount + this.arriveLxCount;
		return totalLxCount >= this.maxLxCacheCount;
	}
}
