package com.prolog.eis.dto.enginee;

public class DingDanMxDto {
	public DingDanMxDto() {
		super();
	}

	public DingDanMxDto(OrderBillDto dingDan, int spId, int spCount) {
		this();

		this.dingDan = dingDan;
		this.spId = spId;
		this.spCount = spCount;
	}
	
	// 订单明细ID
	private int id;

	// 订单汇总ID
	private int orderHzId;

	// 商品ID
	private int spId;

	// 明细所在的订单
	private OrderBillDto dingDan;

	/// <summary>
	/// 应播
	/// </summary>
	private int spCount;

	/// <summary>
	/// 实际播种
	/// </summary>
	private int boZhongCount;

	// 绑定数量
	private int bindingCount;

	public void AddBindingCount(int newBindingCount) throws Exception {
		int totalBindingCount = this.bindingCount + newBindingCount; 
		if (this.boZhongCount + totalBindingCount  > spCount) {
			throw new Exception("新料箱绑定异常，订单明细spid" + this.spId + "播种数量【" + this.boZhongCount + "】" + "绑定数量【" + totalBindingCount
					+ "】大于商品数量【" + this.spCount + "】");
		}
		this.bindingCount = totalBindingCount;
	}

	public boolean CheckBindingFinish() throws Exception {
		return this.boZhongCount + this.bindingCount >= spCount;
		
//		if (this.boZhongCount + this.bindingCount > spCount) {			
//			throw new Exception("订单明细id" + this.id + "播种数量【" + this.boZhongCount + "】" + "绑定数量【" + this.bindingCount
//					+ "】大于商品数量【" + this.spCount + "】");
//		}
//
//		return this.boZhongCount + this.bindingCount == spCount;
	}

	public int GetLeaveBindingCount() throws Exception {
		if (this.boZhongCount + this.bindingCount > spCount) {
			return 0;
//			throw new Exception("订单明细spid" + this.spId + "播种数量【" + this.boZhongCount + "】" + "绑定数量【" + this.bindingCount
//					+ "】大于商品数量【" + this.spCount + "】");
		}

		return this.spCount - this.boZhongCount - this.bindingCount;
	}

	public OrderBillDto getDingDan() {
		return dingDan;
	}

	public void setDingDan(OrderBillDto dingDan) {
		this.dingDan = dingDan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderHzId() {
		return orderHzId;
	}

	public void setOrderHzId(int orderHzId) {
		this.orderHzId = orderHzId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public int getSpId() {
		return spId;
	}

	public void setLotId(int spId) {
		this.spId = spId;
	}

	public int getSpCount() {
		return spCount;
	}

	public void setSpCount(int spCount) {
		this.spCount = spCount;
	}

	public int getBoZhongCount() {
		return boZhongCount;
	}

	public void setBoZhongCount(int boZhongCount) {
		this.boZhongCount = boZhongCount;
	}

	public int getBindingCount() {
		return bindingCount;
	}

	public void setBindingCount(int bindingCount) {
		this.bindingCount = bindingCount;
	}
}
