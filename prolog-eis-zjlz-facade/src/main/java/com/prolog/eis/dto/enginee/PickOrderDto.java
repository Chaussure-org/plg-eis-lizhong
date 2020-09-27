package com.prolog.eis.dto.enginee;



import com.prolog.eis.dto.dingdantaker.WaybillGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author wangkang
 */
public class PickOrderDto {
	private int pickOrderId;
	private int stationId;
	private List<OrderDto> orderList;
	/**
	 * 料箱集合
	 */
	private List<ContainerDto> containerList;
	private int isAllContainerArrive;

	// ********以下非查询***********
	private HashSet<Integer> spKeyHs;

	public PickOrderDto() {
		this.orderList = new ArrayList<OrderDto>();
		this.containerList = new ArrayList<ContainerDto>();
		this.spKeyHs = new HashSet<Integer>();
	}

	public int getPickOrderId() {
		return pickOrderId;
	}

	public void setPickOrderId(int pickOrderId) {
		this.pickOrderId = pickOrderId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public List<OrderDto> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDto> orderList) {
		this.orderList = orderList;
	}

	public List<ContainerDto> getContainerList() {
		return containerList;
	}

	public void setContainerList(List<ContainerDto> containerList) {
		this.containerList = containerList;
	}

	public int getIsAllContainerArrive() {
		return isAllContainerArrive;
	}

	public void setIsAllContainerArrive(int isAllContainerArrive) {
		this.isAllContainerArrive = isAllContainerArrive;
	}

	public HashSet<Integer> getSpKeyHs() {
		return spKeyHs;
	}

	public void setSpKeyHs(HashSet<Integer> spKeyHs) {
		this.spKeyHs = spKeyHs;
	}

	public Integer getOutboundSpId(HashSet<Integer> outboundFailSpIdSet) throws Exception {
		for (OrderDto order : this.getOrderList()) {
			Integer spId = order.getOutboundSpId();
			if (spId != null) {
				// 判断出库失败的商品是否包含此商品，防止死循环
				if (!outboundFailSpIdSet.contains(spId)) {
					return spId;
				}
			}
		}
		return null;
	}

	public boolean checkIsAllBinding() throws Exception {
		for (OrderDto order : this.orderList) {
			if (!order.checkIsAllBinding()) {
                return false;
            }
		}

		return true;
	}

	public void addWaybill(OrderDto order) {
		this.orderList.add(order);

		for (OrderMxDto orderMx : order.getOrderMxList()) {
			if (!this.spKeyHs.contains(orderMx.getSpId())) {
                this.spKeyHs.add(orderMx.getSpId());
            }
		}
	}

	public void addWaybillGroup(WaybillGroup waybillGroup) {
		if (waybillGroup.getWaybillList().size() == 0) {
            return;
        }
		this.orderList.addAll(waybillGroup.getWaybillList());

		OrderDto firstDingDan = waybillGroup.getWaybillList().get(0);

		for (OrderMxDto mx : firstDingDan.getOrderMxList()) {
			if (!this.spKeyHs.contains(mx.getSpId())) {
                this.spKeyHs.add(mx.getSpId());
            }
		}
	}
}
