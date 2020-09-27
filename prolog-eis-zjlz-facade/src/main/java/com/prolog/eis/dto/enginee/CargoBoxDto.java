package com.prolog.eis.dto.enginee;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author wangkang
 */
public class CargoBoxDto {
	
	private String cargoBoxNo;
	private int spId;
	private int spCount;

	/**
	 * 	货格所绑定的订单
 	 */
	private Map<Integer, Integer> containerSubOrderMxBindingMap;


	private String containerNo;

	private ContainerDto container;
	
	public CargoBoxDto() {
		this.containerSubOrderMxBindingMap = new HashMap<Integer, Integer>();
	}

	public String getCargoBoxNo() {
		return cargoBoxNo;
	}

	public void setCargoBoxNo(String cargoBoxNo) {
		this.cargoBoxNo = cargoBoxNo;
	}

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public int getSpCount() {
		return spCount;
	}

	public void setSpCount(int spCount) {
		this.spCount = spCount;
	}

	public Map<Integer, Integer> getContainerSubOrderMxBindingMap() {
		return containerSubOrderMxBindingMap;
	}

	public void setContainerSubOrderMxBindingMap(Map<Integer, Integer> containerSubOrderMxBindingMap) {
		this.containerSubOrderMxBindingMap = containerSubOrderMxBindingMap;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public ContainerDto getContainer() {
		return container;
	}

	public void setContainer(ContainerDto container) {
		this.container = container;
	}

	public int getBindingLeaveCount() {
		int totalBindingCount = 0;
		for (Entry<Integer, Integer> entry : containerSubOrderMxBindingMap.entrySet()) {
			totalBindingCount += entry.getValue();
		}

		return this.spCount - totalBindingCount;
	}
}
