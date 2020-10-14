package com.prolog.eis.dto.store;

import lombok.Data;

@Data
public class SxStoreGroupDto {

	/**
	 * 存储位编号
	 */
	private String storeNo;
	/**
	 * 货位组升位锁
	 */
	private int ascentLockState;
	
	/**
	 * 移库数
	 */
	private int deptNum;

	/**
	 * 层
	 */
	private int layer;
	
	/**
	 * 货位组位置索引(从上到下、从左到右)
	 */
	private int locationIndex;		
	
	/**
	 * 货位组ID
	 */
	private int storeLocationGroupId;
	
	/**
	 * 相邻货位ID1
	 */
	private Integer storeLocationId1;		
	
	/**
	 * 相邻货位ID2
	 */
	private Integer storeLocationId2;

	/**
	 * X
	 */
	private int x;		
	
	/**
	 * Y
	 */
	private int y;		
	
	/**
	 * 容器编号
	 */
	private String containerNo;
	
	/**
	 * 库存属性 业主
	 */
	private String ownerId;
	
	/**
	 * 库存属性 商品编码
	 */
	private String goodsId;
	
	/**
	 * 库存属性 批次号
	 */
	private String lotId;
	
	/**
	 * 商品订单号
	 */
	private String goodsOrderNo;
	
	/**
	 * 库存状态
	 */
	private int storeState;
	
	private int sotreId;
}
