package com.prolog.eis.dto.location.sxk;

import lombok.Data;

@Data
public class StoreLocationDistanceDto {

	private int storeLocationId;

	private int distance;
	
	private int flag;		//标识  1:靠index为1的，2:靠index为最大的
}
