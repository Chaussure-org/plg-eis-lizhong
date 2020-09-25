package com.prolog.eis.dto.enginee;

import java.util.Map;

public class HuoGeSpMxDto {

	private String huoGeNo;	//货格号
	
	private Map<Integer, Integer> lxDingDanMxBindingMap;

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
	 * @return the lxDingDanMxBindingMap
	 */
	public Map<Integer, Integer> getLxDingDanMxBindingMap() {
		return lxDingDanMxBindingMap;
	}

	/**
	 * @param lxDingDanMxBindingMap the lxDingDanMxBindingMap to set
	 */
	public void setLxDingDanMxBindingMap(Map<Integer, Integer> lxDingDanMxBindingMap) {
		this.lxDingDanMxBindingMap = lxDingDanMxBindingMap;
	}
	
	
}
