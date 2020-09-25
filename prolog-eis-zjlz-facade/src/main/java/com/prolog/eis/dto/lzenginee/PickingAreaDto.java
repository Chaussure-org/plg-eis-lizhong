package com.prolog.eis.dto.lzenginee;

import lombok.Data;

import java.util.List;

@Data
public class PickingAreaDto {
    //站台集合
    private List<StationDto> stationDtoList;

}
