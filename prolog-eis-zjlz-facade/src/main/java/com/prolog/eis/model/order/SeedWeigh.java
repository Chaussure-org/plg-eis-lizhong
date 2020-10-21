package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-10-21 
 */
@ApiModel ("播种称重检测表")
@Table ("seed_weigh")
@Data
public class SeedWeigh {

  @Column("id")
  @Id
  private Integer id;

  @Column("seed_info_id")
  @ApiModelProperty("播种信息id")
  private Integer seedInfoId;

  @Column("first_weigh")
  @ApiModelProperty("第一次称重重量")
  private BigDecimal firstWeigh;

  @Column("first_weigh_check")
  @ApiModelProperty("第一次称重检测(0合格，1不合格)")
  private Boolean firstWeighCheck;

  @Column("second_weigh")
  @ApiModelProperty("第二次称重重量")
  private BigDecimal secondWeigh;

  @Column("second_weigh_check")
  @ApiModelProperty("第一次称重检测(0合格，1不合格)")
  private Boolean secondWeighCheck;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  @Column("authority_leave")
  @ApiModelProperty("是否权限放行（0否1是）")
  private Boolean authorityLeave;


}
