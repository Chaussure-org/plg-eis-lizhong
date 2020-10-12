package com.prolog.eis.dto.location;

import com.prolog.eis.model.location.StoreArea;
import lombok.Data;

/**
 * @author chenbo
 * @date 2020/9/27 11:01
 */
@Data
public class StoreAreaPriorityDTO {

    /**
     * 区域对象
     */
    private StoreArea storeArea;

    /**
     * 区域优先级，优先级越大值越大
     */
    private int priority;

    /**
     * 任务数权值
     */
    private int taskPower;

    /**
     * 库存存放比例。任务权值启用后，任务数一致的情况下才考虑库存容量
     */
    private int power;
}
