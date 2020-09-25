package com.prolog.eis.dto.lzenginee;

import lombok.Data;

import java.util.List;

@Data
public class PickOrderDto {
    private int pickOrderId;

    private int isAllarrive;
    private List<OrderDto> orderDtoList;

}
