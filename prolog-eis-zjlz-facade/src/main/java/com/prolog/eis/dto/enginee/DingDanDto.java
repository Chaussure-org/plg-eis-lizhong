package com.prolog.eis.dto.enginee;

import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.dto.orderpool.OpOrderMx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DingDanDto {
	public DingDanDto() {
		this.dingDanMxList = new ArrayList<DingDanMxDto>();
	}

	private int id;
	// 拣选单Id
	private int jxdId;	
	// 订单优先级
	private Integer priority;
	// 时效时间
	private Date shiXiaoTime;	
	// ************以下不查询*********
	// 商品集合
	private List<DingDanMxDto> dingDanMxList;
//
//
//	// 商品明细，唯一id和数量
//
//	private Map<Integer, DingDanMxDto> SPmap;
//
//	public Map<Integer, DingDanMxDto> getSPmap() {
//		return SPmap;
//	}
//
//	public void setSPmap(Map<Integer, DingDanMxDto> sPmap) {
//		SPmap = sPmap;
//	}

	// addbywx end

	// **************以下不是查询结果**************
	private String yunDanSPKey;

	public boolean CheckIsFinish() {
		for (DingDanMxDto ddMx : this.dingDanMxList) {
			if (ddMx.getBoZhongCount() < ddMx.getSpCount())
				return false;
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

	public Date getShiXiaoTime() {
		return shiXiaoTime;
	}

	public void setShiXiaoTime(Date shiXiaoTime) {
		this.shiXiaoTime = shiXiaoTime;
	}
	
	public int getJxdId() {
		return jxdId;
	}

	public void setJxdId(int jxdId) {
		this.jxdId = jxdId;
	}

	public List<DingDanMxDto> getDingDanMxList() {
		return dingDanMxList;
	}

	public void setDingDanMxList(List<DingDanMxDto> dingDanMxList) {
		this.dingDanMxList = dingDanMxList;
	}

	public boolean CheckIsAllBinding() throws Exception {
		for(DingDanMxDto ddMx:this.dingDanMxList) {
			if(!ddMx.CheckBindingFinish())
				return false;
		}
		
		return true;
	}

	public Integer GetChuKuSpId() throws Exception {
		for (DingDanMxDto mx : this.dingDanMxList) {
			if (!mx.CheckBindingFinish()) {
				return mx.getSpId();
			}
		}
		return null;
	}

	public String getYunDanSPKey() {
		return yunDanSPKey;
	}

	public void setYunDanSPKey(String yunDanSPKey) {
		this.yunDanSPKey = yunDanSPKey;
	}	
	
	public static DingDanDto CopyDingDan(OpOrderHz opOrder) {
		DingDanDto dd = new DingDanDto();
		dd.setId(opOrder.getId());
		dd.setPriority(opOrder.getPriority());
		dd.setShiXiaoTime(opOrder.getExpectTime());
		
		for(OpOrderMx opmx : opOrder.getMxList()) {
			DingDanMxDto ddmx = new DingDanMxDto();
			ddmx.setId(opmx.getId());
			ddmx.setOrderHzId(opmx.getOrderHzId());
			ddmx.setSpId(opmx.getGoodsId());
			ddmx.setSpCount(opmx.getPlanNum());
			
			dd.getDingDanMxList().add(ddmx);
		}
		
		return dd;
	}

	public void initYunDanSPKey() {
		this.yunDanSPKey = "";

		List<Integer> spIdList = new ArrayList<Integer>();

		for (DingDanMxDto mx : this.dingDanMxList) {
			spIdList.add(mx.getSpId());
		}

		spIdList.sort((spId1, spId2) -> {
			return spId1 - spId2;
		});

		for (int i = 0; i < spIdList.size(); i++) {
			this.yunDanSPKey += spIdList.get(i);
			if (i != spIdList.size() - 1)
				this.yunDanSPKey += "@";
		}
	}
}
