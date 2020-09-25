package com.prolog.eis.dto.enginee;

public class HuoGeDingDanDto {

	private String huoGeNo;	//货格号
	
	private int dingDanMxId;		//订单明细Id
	
	private int num ;		//订单数量

	/**
	 * @return the huoGeNo
	 */
	public String getHuoGeNo() {
		return huoGeNo;
	}

	/**
	 * @param huoGeNo the huoGeNo to set
	 */
	public void setHuoGeNo(String huoGeNo) {
		this.huoGeNo = huoGeNo;
	}

	/**
	 * @return the dingDanMxId
	 */
	public int getDingDanMxId() {
		return dingDanMxId;
	}

	/**
	 * @param dingDanMxId the dingDanMxId to set
	 */
	public void setDingDanMxId(int dingDanMxId) {
		this.dingDanMxId = dingDanMxId;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	public HuoGeDingDanDto(String huoGeNo, int dingDanMxId, int num) {
		super();
		this.huoGeNo = huoGeNo;
		this.dingDanMxId = dingDanMxId;
		this.num = num;
	}

	public HuoGeDingDanDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
