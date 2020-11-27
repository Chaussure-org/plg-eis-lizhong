package com.prolog.eis.dto.store;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/20 17:28
 * 站台任务拖dto
 */
public class StationTrayDTO {
    @ApiModelProperty("站台id")
    private int stationId;
    @ApiModelProperty("空闲数")
    private  int emptyCount;
    @ApiModelProperty("已有托盘数")
    private int useCount;
    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getEmptyCount() {
        return emptyCount;
    }

    public void setEmptyCount(int emptyCount) {
        this.emptyCount = emptyCount;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }
}
