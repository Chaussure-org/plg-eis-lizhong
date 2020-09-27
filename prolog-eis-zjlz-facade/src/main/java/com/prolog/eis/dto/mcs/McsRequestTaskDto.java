package com.prolog.eis.dto.mcs;

import lombok.Data;

@Data
public class McsRequestTaskDto {

	/**
	 * mcs任务类型
	 */
	private int type;
	/**
	 * 母托盘编号
	 */
	private String stockId;
	/**
	 * 请求位置:原坐标
	 */
	private String source;
	
	/**
	 * 目的位置：目的坐标
	 */
	private String target;
	
	private boolean success;
	
	private String errorMessage;
}
