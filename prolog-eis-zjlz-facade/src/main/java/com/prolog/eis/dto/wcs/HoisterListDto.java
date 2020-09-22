package com.prolog.eis.dto.wcs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.List;

/**
 * @author LuoLi
 * @version 1.0
 * @date 2020/7/13 20:05
 */
public class HoisterListDto implements Serializable {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<HoisterInfoDto> hoisterInfoDtos;

    public List<HoisterInfoDto> getHoisterInfoDtos() {
        return hoisterInfoDtos;
    }

    public void setHoisterInfoDtos(List<HoisterInfoDto> hoisterInfoDtos) {
        this.hoisterInfoDtos = hoisterInfoDtos;
    }
}
