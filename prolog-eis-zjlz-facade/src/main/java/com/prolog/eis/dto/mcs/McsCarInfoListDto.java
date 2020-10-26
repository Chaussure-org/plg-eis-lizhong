package com.prolog.eis.dto.mcs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prolog.eis.dto.wcs.HoisterInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author LuoLi
 * @version 1.0
 * @date 2020/7/13 20:05
 */
public class McsCarInfoListDto implements Serializable {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<McsCarInfoDto> mcsCarInfoDtos;

    public List<McsCarInfoDto> getHoisterInfoDtos() {
        return mcsCarInfoDtos;
    }

    public void setHoisterInfoDtos(List<McsCarInfoDto> mcsCarInfoDtos) {
        this.mcsCarInfoDtos = mcsCarInfoDtos;
    }
}
