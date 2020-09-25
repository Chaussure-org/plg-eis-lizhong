package com.prolog.eis.dto.enginee;

import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.dto.orderpool.OpOrderMx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class XiangKuDto {

	private static final Logger logger = LoggerFactory.getLogger(XiangKuDto.class);

	private List<ZhanTaiDto> ztList;//当前播种作业站台集合
	private Map<Integer, Integer> spStockMap;// 商品库存<商品Id,库存数量>
	
	//***********以下非查询数据**************
	private List<DingDanDto> poolDDList;//当前订单集合
	private List<DingDanDto> ddList;//当前库存满足的订单集合
	private Map<Integer,Integer> kuCunSPMap;//当前库存<商品Id,库存数量>
	private HashSet<Integer> chuKuFailSpIdHs;//当前巷道出库失败的商品Id集合
	
	public XiangKuDto() {
		this.ztList = new ArrayList<ZhanTaiDto>();
		this.poolDDList = new ArrayList<DingDanDto>();
		this.ddList = new ArrayList<DingDanDto>();
		this.kuCunSPMap = new HashMap<Integer,Integer>();
		this.chuKuFailSpIdHs = new HashSet<Integer>();
		this.spStockMap = new HashMap<Integer, Integer>();
	}
	
	public List<ZhanTaiDto> getZtList() {
		return ztList;
	}

	public void setZtList(List<ZhanTaiDto> ztList) {
		this.ztList = ztList;
	}

	public List<DingDanDto> getPoolDDList() {
		return poolDDList;
	}

	public void setPoolDDList(List<DingDanDto> poolDDList) {
		this.poolDDList = poolDDList;
	}

	public List<DingDanDto> getDdList() {
		return ddList;
	}

	public void setDdList(List<DingDanDto> ddList) {
		this.ddList = ddList;
	}

	public Map<Integer, Integer> getKuCunSPMap() {
		return kuCunSPMap;
	}

	public void setKuCunSPMap(Map<Integer, Integer> kuCunSPMap) {
		this.kuCunSPMap = kuCunSPMap;
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

	public boolean checkDingDan(OpOrderHz opOrder) {
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
	
	public void subtractingDingDan(OpOrderHz dd) throws Exception {
		for(OpOrderMx mx : dd.getMxList()) {
			this.subtracting(mx.getGoodsId(), mx.getPlanNum());
		}
	}
	// 扣除库存数
	public void subtracting(int spId, int spCount) throws Exception {
		if (!this.spStockMap.containsKey(spId))
			throw new Exception("subtracting没有spId:" + spId);

		int leaveSpCount = this.spStockMap.get(spId);
		if (leaveSpCount < spCount)
			throw new Exception("subtracting的spId:" + spId + "不满足扣除数量");

		this.spStockMap.put(spId, leaveSpCount - spCount);
	}
}
