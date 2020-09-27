package com.prolog.eis.dto.enginee;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkang
 */
public class StationDto {
	public static final int STATUS_LOCK = 1;
	public static final int STATUS_UNLOCK = 2;
	public StationDto() {
		this.pickOrderList = new ArrayList<PickOrderDto>();
	}

	/**
	 * 站台号
	 */
	private int stationId;
	/**
	 * 是否锁定 2-unlock
	 */
	private int isLock;

	/**
	 *  1、播种作业。2、停止索取。3、盘点作业
	 */
	private int isClaim;

	/**
	 * 在途料箱最大缓存数
	 */
	private int maxLxCacheCount;

	private int arriveLxCount;

	private int chuKuLxCount;

	/**
	 * 拣选单集合
	 */
	private List<PickOrderDto> pickOrderList;

	// ***********以下非查询数据***********
	/**
	 * 需要出库的拣选单
 	 */
	private PickOrderDto needOutboundPickOrder;

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

	public PickOrderDto getNeedOutboundPickOrder() {
		return needOutboundPickOrder;
	}

	public void setNeedOutboundPickOrder(PickOrderDto needOutboundPickOrder) {
		this.needOutboundPickOrder = needOutboundPickOrder;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public List<PickOrderDto> getPickOrderList() {
		return pickOrderList;
	}

	public void setPickOrderList(List<PickOrderDto> pickOrderList) {
		this.pickOrderList = pickOrderList;
	}

	public boolean checkIsAllBinding() throws Exception {
		for (PickOrderDto jxd : this.getPickOrderList()) {
			if (!jxd.checkIsAllBinding()) {
                return false;
            }
		}
		return true;
	}

	/**
	 * 检查站台所需的料箱是否已经全部到达
	 * @return
	 */
	public boolean checkIsAllContainerArrive() {
		for (PickOrderDto jxd : this.getPickOrderList()) {
			if (jxd.getIsAllContainerArrive() != 1) {
                return false;
            }
		}
		return true;
	}

	public int computeContainerCount() {
		int ztLxCount = 0;
		for (PickOrderDto jxd : this.getPickOrderList()) {
			ztLxCount += jxd.getContainerList().size();
		}
		return ztLxCount;
	}

	public boolean isContainerLimitMax() {
		int totalLxCount = this.chuKuLxCount + this.arriveLxCount;
		return totalLxCount >= this.maxLxCacheCount;
	}
}
