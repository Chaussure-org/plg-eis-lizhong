package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 库存区域方向表(StoreAreaDirection)实体类
 *
 * @author wuxl
 * @since 2020-08-28 14:59:23
 */
@Data
@Table("store_area_direction")
public class StoreAreaDirection implements Serializable {
    private static final long serialVersionUID = -14867566963976422L;

    @Id
    @Column("id")
    @ApiModelProperty("ID")
    private Integer id;

    @Column("source_area_no")
    @ApiModelProperty("起点区域")
    private String sourceAreaNo;

    @Column("target_area_no")
    @ApiModelProperty("终点区域")
    private String targetAreaNo;

    @Column("max_height")
    @ApiModelProperty("高度限制")
    private Integer maxHeight;

    @Column("path_step")
    @ApiModelProperty("路线长度")
    private Integer pathStep;

    @Column("path_power")
    @ApiModelProperty("路线权值")
    private Integer pathPower;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column("update_time")
    @ApiModelProperty("修改时间")
    private Date updateTime;

}