package com.prolog.eis.dto.mcs;

import lombok.Data;

@Data
public class McsSendTaskDto {

	/**
	 * 规则HHmmss+四位流水码(1-9999)
	 */
	private String taskId;
	/**
	 * 任务类型：1：入库:2：出库 3 移位 4输送线前进
	 */
	private int type;
	
	/**
	 * 库编号
	 */
	private int bankId;
	/**
	 * 母托盘编号
	 */
	private String containerNo;
	/**
	 * 请求位置:原坐标
	 */
	private String address;
	/**
	 * 目的位置：目的坐标
	 */
	private String target;
	/**
	 * 入库重量
	 */
	private String weight;
	/**
	 * 任务优先级,0-99,0优先级最大
	 */
	private String priority;
	/**
	 * 任务状态
	 */
	private int status;
}
