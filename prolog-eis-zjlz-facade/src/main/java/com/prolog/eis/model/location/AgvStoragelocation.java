package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * agv区域货位表(AgvStoragelocation)实体类
 *
 * @author wuxl
 * @since 2020-09-08 16:23:49
 */
@Data
@Table("agv_storagelocation")
public class AgvStoragelocation implements Serializable {
    private static final long serialVersionUID = -41236769428637108L;

    public static final int TASK_LOCK=1;
    public static final int TASK_EMPTY=0;


    @Id
    @Column("id")
    @ApiModelProperty("id")
    private Integer id;

    @Column("area_no")
    @ApiModelProperty("区域编号")
    private String areaNo;
    
    @Column("location_no")
    @ApiModelProperty("点位编号")
    private String locationNo;

    @Column("ceng")
    @ApiModelProperty("入库楼层")
    private Integer ceng;

    @Column("x")
    @ApiModelProperty("坐标x")
    private Integer x;

    @Column("y")
    @ApiModelProperty("坐标y")
    private Integer y;

    @Column("location_type")
    @ApiModelProperty("位置类型 1存储位 2 输送线 3托盘作业位")
    private Integer locationType;

    @Column("tally_code")
    @ApiModelProperty("wms货位 可空")
    private String tallyCode;

    @Column("task_lock")
    @ApiModelProperty("任务锁  0:无任务 1：任务中")
    private Integer taskLock;

    @Column("storage_lock")
    @ApiModelProperty("1锁定  0不锁定")
    private Integer storageLock;

    @Column("device_no")
    @ApiModelProperty("设备编号")
    private String deviceNo;

}
