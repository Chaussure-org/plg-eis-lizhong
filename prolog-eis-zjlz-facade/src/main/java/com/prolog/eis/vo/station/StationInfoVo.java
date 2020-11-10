package com.prolog.eis.vo.station;

import com.prolog.framework.core.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 11:57
 * 站台信息页面展示
 */
@Data
public class StationInfoVo {

    @ApiModelProperty("站台ID")
    private Integer id;

    @ApiModelProperty("当前拣选单ID")
    private Integer currentStationPickId;

    @ApiModelProperty("是否锁定 0不锁定  1锁定")
    private Integer isLock;

    @ApiModelProperty("拣选人员名称")
    private String pickingUserName;

    @ApiModelProperty("站台作业类型 0 - 空闲  10- 盘点 20 - 播种")
    private Integer stationTaskType;

    @ApiModelProperty("修改时间")
    private java.util.Date updateTime;

    @ApiModelProperty("料箱编号（拣选站有料箱有值）")
    private String containerNo;

    @ApiModelProperty("站台类型（1-半成品站台，2-成品站台）")
    private Integer stationType;

    @ApiModelProperty("站台ip")
    private String stationIp;
}
