package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 11:19
 * 货位信息dto
 */

@Data
public class StoreInfoDto {

    @ApiModelProperty("货位组编号")
    private String groupNo;

    @ApiModelProperty("货位编号")
    private String storeNo;

    @ApiModelProperty("层")
    private int layer;

    @ApiModelProperty("巷道(x)")
    private int x;

    @ApiModelProperty("排（y）")
    private int y;

    @ApiModelProperty("区域")
    private String areaNo;
    @ApiModelProperty("货位深度")
    private int depth;
    @ApiModelProperty("容器编号")
    private String containerNo;

    @ApiModelProperty("货位锁")
    private int storeLockState;
    @ApiModelProperty("货位组锁")
    private int groupLockState;
    @ApiModelProperty("是否锁定 0 未锁定 1锁定")
    private int isLock;
}
