package com.prolog.eis.dto.lzenginee;

import lombok.Data;

@Data
public class StationDto {
    private int stationId;
    private int isLock;
    private PickOrderDto pickOrderDto;
}
