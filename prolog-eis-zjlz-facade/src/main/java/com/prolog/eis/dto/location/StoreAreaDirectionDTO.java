package com.prolog.eis.dto.location;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:18
 * @Version: V1.0
 */
@Data
public class StoreAreaDirectionDTO implements Serializable {
  private static final long serialVersionUID = 8835037106981554603L;

  private Integer id;

  /**
   * 起点区域
   */
  private String sourceAreaNo;

  /**
   * 起点点位
   */
  private String sourceLocationNo;

  /**
   * 终点区域
   */
  private String targetAreaNo;

  /**
   * 终点点位
   */
  private String targetLocationNo;

  /**
   * 高度限制
   */
  private Integer maxHeight;

  /**
   * 路线长度
   */
  private Integer pathStep;

  /**
   * 路线权值
   */
  private Integer pathPower;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;

  /**
   * 终点区域最大容器数量
   */
  private Integer maxCount;

  /**
   * 终点区域实际容器数量
   */
  private Integer realCount;

  /**
   * 终点区域到位容器数量
   */
  private Integer placeCount;

  /**
   * 终点区域预占容器数量
   */
  private Integer preemptCount;

  /**
   * 10区域;20接驳点
   */
  private Integer areaType;

  /**
   * 接驳点实际数量
   */
  private Integer jointPointCount;

  /**
   * 起点区域是否允许暂存：0不允许暂存 1允许暂存
   */
  private Integer sourceTemporaryArea;


  /**
   * 终点区域是否允许暂存：0不允许暂存 1允许暂存
   */
  private Integer targetTemporaryArea;

}
