package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存区域表(StoreArea)实体类
 *
 * @author wuxl
 * @since 2020-08-28 14:58:58
 */
@Data
@Table("store_area")
public class StoreArea implements Serializable {
    private static final long serialVersionUID = 746423221831281311L;

    @Id
    @Column("area_no")
    @ApiModelProperty("区域编号")
    private String areaNo;

    @Column("area_type")
    @ApiModelProperty("10区域;20接驳点")
    private Integer areaType;

    @Column("device_system")
    @ApiModelProperty("下游wcs系统")
    private String deviceSystem;
    
    @Column("location_no")
    @ApiModelProperty("area_type=20时有值  点位编号")
    private String locationNo;

    @Column("layer")
    @ApiModelProperty("area_type=20时有值  层")
    private Integer layer;

    @Column("x")
    @ApiModelProperty("area_type=2时有值  X")
    private Integer x;

    @Column("y")
    @ApiModelProperty("area_type=2时有值  Y")
    private Integer y;

    @Column("max_height")
    @ApiModelProperty("高度限制")
    private Integer maxHeight;

    @Column("temporary_area")
    @ApiModelProperty("0 不允许暂存 1允许暂存")
    private Integer temporaryArea;

    @Column("max_count")
    @ApiModelProperty("最大容器数")
    private Integer maxCount;

    @Column("remark")
    @ApiModelProperty("备注")
    private String remark;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column("update_time")
    @ApiModelProperty("修改时间")
    private Date updateTime;

}