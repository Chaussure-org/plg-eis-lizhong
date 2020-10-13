package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenbo
 * @date 2020/9/28 16:46
 */
@Data
@Table("area_reserve")
public class AreaReserve {

    @Id
    @Column("id")
    @ApiModelProperty("id")
    private Integer id;

    @Column("area_no")
    @ApiModelProperty("区域")
    private String areaNo;

    @Column("task_type")
    @ApiModelProperty("任务类型 -1 空托 1任务托")
    private int taskType;

    @Column("reserve_type")
    @ApiModelProperty("预留类型 1 数量  2百分比")
    private int reserveType;

    @Column("reserve_count")
    @ApiModelProperty("预留类型为数量时 该值为货位数量  预留类型为百分比时，该值为货位百分比")
    private int reserveCount;
}
