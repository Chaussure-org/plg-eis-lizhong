package com.prolog.eis.dto.mcs;

import lombok.Data;

@Data
public class InBoundRequestDto {

	/**
	 * 规则HHmmss+四位流水码(1-9999)
	 */
	private String taskId;
	/**
	 * 任务类型：1：入库:2：出库 3 跨层
	 */
	private int type;
	/**
	 * 母托盘编号
	 */
	private String stockId;
	/**
	 * 字托盘编号
	 */
	private String stockIdSub;
	/**
	 * 请求位置:原坐标
	 */
	private String source;
	/**
	 * 目的位置：目的坐标
	 */
	private String target;
	/**
	 * 入库重量
	 */
	private String weight;
	/**
	 * 尺寸检测信息：1：正常，2：左超长，3：右超长；4：上超长；5：下超长，6：超高
	 */
	private int detection;
	/**
	 * 任务优先级,0-99,0优先级最大
	 */
	private int priority;
	/**
	 *【任务状态：1：任务开始；2：任务完成】
	 */
	private int status;
}
