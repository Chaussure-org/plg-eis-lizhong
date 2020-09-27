package com.prolog.eis.dto.enginee;

import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.dto.orderpool.OpOrderMx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangkang
 */
public class OrderDto {
	public OrderDto() {
		this.orderMxList = new ArrayList<OrderMxDto>();
	}

	private int id;
	/**
	 * 拣选单Id
	 */
	private int jxdId;
	/**
	 * 订单优先级
	 */
	private Integer priority;
	/**
	 * 时效
	 */
	private Date expectTime;
	// ************以下不查询*********
	/**
	 *  商品集合
	 */
	private List<OrderMxDto> orderMxList;
	// **************以下不是查询结果**************

	private String waybillSpKey;

	public boolean checkIsFinish() {
		for (OrderMxDto ddMx : this.orderMxList) {
			if (ddMx.getSeedCount() < ddMx.getSpCount()) {
                return false;
            }
		}

		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(Date expectTime) {
		this.expectTime = expectTime;
	}

	public int getJxdId() {
		return jxdId;
	}

	public void setJxdId(int jxdId) {
		this.jxdId = jxdId;
	}

	public List<OrderMxDto> getOrderMxList() {
		return orderMxList;
	}

	public void setOrderMxList(List<OrderMxDto> orderMxList) {
		this.orderMxList = orderMxList;
	}

	public String getWaybillSpKey() {
		return waybillSpKey;
	}

	public void setWaybillSpKey(String waybillSpKey) {
		this.waybillSpKey = waybillSpKey;
	}

	public boolean checkIsAllBinding() throws Exception {
		for(OrderMxDto ddMx:this.orderMxList) {
			if(!ddMx.checkBindingFinish()) {
                return false;
            }
		}
		
		return true;
	}

	public Integer getOutboundSpId() throws Exception {
		for (OrderMxDto mx : this.orderMxList) {
			if (!mx.checkBindingFinish()) {
				return mx.getSpId();
			}
		}
		return null;
	}

	public static OrderDto copyOrder(OpOrderHz opOrder) {
		OrderDto order = new OrderDto();
		order.setId(opOrder.getId());
		order.setPriority(opOrder.getPriority());
		order.setExpectTime(opOrder.getExpectTime());
		
		for(OpOrderMx opmx : opOrder.getMxList()) {
			OrderMxDto orderMx = new OrderMxDto();
			orderMx.setId(opmx.getId());
			orderMx.setOrderHzId(opmx.getOrderHzId());
			orderMx.setSpId(opmx.getGoodsId());
			orderMx.setSpCount(opmx.getPlanNum());
			
			order.getOrderMxList().add(orderMx);
		}
		
		return order;
	}

	public void initWayBillSpKey() {
		this.waybillSpKey = "";

		List<Integer> spIdList = new ArrayList<Integer>();

		for (OrderMxDto mx : this.orderMxList) {
			spIdList.add(mx.getSpId());
		}

		spIdList.sort((spId1, spId2) -> {
			return spId1 - spId2;
		});

		for (int i = 0; i < spIdList.size(); i++) {
			this.waybillSpKey += spIdList.get(i);
			if (i != spIdList.size() - 1) {
                this.waybillSpKey += "@";
            }
		}
	}
}
