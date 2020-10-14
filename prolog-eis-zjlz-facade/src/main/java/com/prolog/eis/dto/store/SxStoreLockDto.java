package com.prolog.eis.dto.store;

import lombok.Data;

@Data
public class SxStoreLockDto {

	/**
	 * 货位升位锁
	 */
	private Integer ascentLockState;
	
	/**
	 * 货位组升位锁
	 */
	private Integer ascentGroupLockState;
	
	/**
	 * 货位组是否锁定
	 */
	private Integer isLock;
	
	/**
	 * 移库数
	 */
	private int deptNum;
	
	/**
	 * 库存Id
	 */
	private int storeId;
	
	/**
	 * 货位Id
	 */
	private int locationId;
	
	/**
	 * 库存状态
	 */
	private int storeState;
	/**
	 * 层
	 */
	private int layer;
	
	/**
	 * x
	 */
	private  int x;
	
	/**
	 * y
	 */
	private int y;
}
