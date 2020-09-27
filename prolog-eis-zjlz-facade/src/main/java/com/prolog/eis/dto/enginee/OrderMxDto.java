package com.prolog.eis.dto.enginee;

/**
 * @author wangkang
 */
public class OrderMxDto {
	public OrderMxDto() {
		super();
	}

	public OrderMxDto(OrderDto order, int spId, int spCount) {
		this();

		this.order = order;
		this.spId = spId;
		this.spCount = spCount;
	}

	/**
	 * 订单明细ID
	 */
	private int id;

	/**
	 * 订单汇总ID
	 */
	private int orderHzId;

	/**
	 * 商品ID
	 */
	private int spId;

	/**
	 * 明细所在的订单
	 */
	private OrderDto order;

	/**
	 * 应播
	 */
	private int spCount;

	/**
	 * 实际播种
	 */
	private int seedCount;

	/**
	 * 绑定数量
	 */
	private int bindingCount;

	public void addBindingCount(int newBindingCount) throws Exception {
		int totalBindingCount = this.bindingCount + newBindingCount; 
		if (this.seedCount + totalBindingCount  > spCount) {
			throw new Exception("新料箱绑定异常，订单明细spid" + this.spId + "播种数量【" + this.seedCount + "】" + "绑定数量【" + totalBindingCount
					+ "】大于商品数量【" + this.spCount + "】");
		}
		this.bindingCount = totalBindingCount;
	}

	public boolean checkBindingFinish() throws Exception {
		return this.seedCount + this.bindingCount >= spCount;
	}

	public int getLeaveBindingCount() throws Exception {
		if (this.seedCount + this.bindingCount > spCount) {
			return 0;
		}

		return this.spCount - this.seedCount - this.bindingCount;
	}

	public OrderDto getOrder() {
		return order;
	}

	public void setOrder(OrderDto order) {
		this.order = order;
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

	public int getSeedCount() {
		return seedCount;
	}

	public void setSeedCount(int seedCount) {
		this.seedCount = seedCount;
	}

	public int getBindingCount() {
		return bindingCount;
	}

	public void setBindingCount(int bindingCount) {
		this.bindingCount = bindingCount;
	}
}
