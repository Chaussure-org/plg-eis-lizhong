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


    /**
     * 堆垛机库 1 区
     */
    public static final String MCS01="MCS01";
    /**
     * 堆垛机库 2 区
     */
    public static final String MCS02="MCS02";
    /**
     * 堆垛机库 3 区
     */
    public static final String MCS03="MCS03";
    /**
     * 堆垛机库 4 区
     */
    public static final String MCS04="MCS04";
    /**
     * 堆垛机库 5 区
     */
    public static final String MCS05="MCS05";

    /**
     * agv库区
     */
    public static final String RCS01="RCS01";
    /**
     * 箱库 区
     */
    public static final String SAS01="SAS01";
    /**
     * 输送线 区
     */
    public static final String L01="L01";

    /**
     * 站台agv 区
     */
    public static final String SN01="SN01";

    /**
     * 四向库一楼BCR
     */
    public static final String WCS061="WCS061";
    /**
     * 四向库 二楼出库进站BCR区域
     */
    public static final String WCS081="WCS081";
    /**
     * 四向库 二楼出库BCR点位
     */
    public static final String LXJZ01="LXJZ01";


    /**
     * 暂存区
     */
    public static final String CH01="CH01";

    /**
     * 贴标区
     */
    public static final String LB01="LB01";


    /**
     * 空托区暂存区
     */
    public static final String RCS02="RCS02";

    /**
     * 空托上架区
     */
    public static final String RCS03 = "RCS03";


    /**
     * 铁拖上架区
     */
    public static final String IT01 = "IT01";
    /**
     * 输送线回库bcr
     */
    public static final String R0201 = "RO201";

    /**
     * 循环线bcr
     */
    public static final String LXHK02 = "LXHK02";



}
