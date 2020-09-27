package com.prolog.eis.dto.enginee;

import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.dto.orderpool.OpOrderMx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BoxLibDto {

	private static final Logger logger = LoggerFactory.getLogger(BoxLibDto.class);


	/**
	 * 当前播种作业站台集合
	 */
	private List<StationDto> stations;
	/**
	 * 商品库存<商品Id,库存数量>
	 */
	private Map<Integer, Integer> spStockMap;
	
	//***********以下非查询数据**************
	/**
	 * 	当前订单集合
	 */
	private List<OrderDto> orderPoolList;
	/**
	 * 	当前库存满足的订单集合
	 */
	private List<OrderDto> orderList;
	/**
	 * 当前库存<商品Id,库存数量>
	 */
	private Map<Integer,Integer> currentSpMap;
	/**
	 * 当前巷道出库失败的商品Id集合
	 */
	private HashSet<Integer> chuKuFailSpIdHs;
	
	public BoxLibDto() {
		this.stations = new ArrayList<StationDto>();
		this.orderPoolList = new ArrayList<OrderDto>();
		this.orderList = new ArrayList<OrderDto>();
		this.currentSpMap = new HashMap<Integer,Integer>();
		this.chuKuFailSpIdHs = new HashSet<Integer>();
		this.spStockMap = new HashMap<Integer, Integer>();
	}


	public List<StationDto> getStations() {
		return stations;
	}

	public void setStations(List<StationDto> stations) {
		this.stations = stations;
	}

	public List<OrderDto> getOrderPoolList() {
		return orderPoolList;
	}

	public void setOrderPoolList(List<OrderDto> orderPoolList) {
		this.orderPoolList = orderPoolList;
	}

	public List<OrderDto> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDto> orderList) {
		this.orderList = orderList;
	}

	public Map<Integer, Integer> getCurrentSpMap() {
		return currentSpMap;
	}

	public void setCurrentSpMap(Map<Integer, Integer> currentSpMap) {
		this.currentSpMap = currentSpMap;
	}

	public HashSet<Integer> getChuKuFailSpIdHs() {
		return chuKuFailSpIdHs;
	}

	public void setChuKuFailSpIdHs(HashSet<Integer> chuKuFailSpIdHs) {
		this.chuKuFailSpIdHs = chuKuFailSpIdHs;
	}

	public Map<Integer, Integer> getSpStockMap() {
		return spStockMap;
	}

	public void setSpStockMap(Map<Integer, Integer> spStockMap) {
		this.spStockMap = spStockMap;
	}

	public boolean checkOrder(OpOrderHz opOrder) {
		//前提是订单下的一定不能有相同SPId的明细
		for(OpOrderMx mx : opOrder.getMxList()) {
			if(!this.checkStock(mx.getGoodsId(), mx.getPlanNum())) {
				return false;
			}
		}
		
		return true;
	}

	// 检查库存数
	private boolean checkStock(int spId, int spCount) {
		if (!this.spStockMap.containsKey(spId)) {
			logger.info("+++++++++++++++++++" + spId + "商品不在库存中+++++++++++++++++++");
			return false;
		}

		int leaveSpCount = this.spStockMap.get(spId);
		
		if (leaveSpCount >= spCount) {
			return true;
		} else {
			logger.info("++++++++++++++++++"+spId+"商品库存不足,需:"+spCount+"有:"+leaveSpCount+"+++++++++++++++++");
			return false;
		}
	}
	
	public void subtractingOrder(OpOrderHz dd) throws Exception {
		for(OpOrderMx mx : dd.getMxList()) {
			this.subtracting(mx.getGoodsId(), mx.getPlanNum());
		}
	}
	// 扣除库存数
	public void subtracting(int spId, int spCount) throws Exception {
		if (!this.spStockMap.containsKey(spId)) {
            throw new Exception("subtracting没有spId:" + spId);
        }

		int leaveSpCount = this.spStockMap.get(spId);
		if (leaveSpCount < spCount) {
            throw new Exception("subtracting的spId:" + spId + "不满足扣除数量");
        }

		this.spStockMap.put(spId, leaveSpCount - spCount);
	}
}
