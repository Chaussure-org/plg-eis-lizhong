package com.prolog.eis.dto.sas;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 16:03
 */
public class SasCarryListDto implements Serializable {

    private List<SasMoveTaskDto> carryList;

    public List<SasMoveTaskDto> getCarryList() {
        return carryList;
    }

    public void setCarryList(List<SasMoveTaskDto> carryList) {
        this.carryList = carryList;
    }
}
