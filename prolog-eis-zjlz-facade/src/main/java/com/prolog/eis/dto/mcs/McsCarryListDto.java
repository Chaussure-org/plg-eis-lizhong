package com.prolog.eis.dto.mcs;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 15:57
 */
public class McsCarryListDto implements Serializable {

    private List<McsMoveTaskDto> carryList;

    public List<McsMoveTaskDto> getCarryList() {
        return carryList;
    }

    public void setCarryList(List<McsMoveTaskDto> carryList) {
        this.carryList = carryList;
    }
}
