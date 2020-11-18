package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 18:38
 */
@Data
public class WmsInboundInfoDto {

    @ApiModelProperty("仓库类型")
    private String branchType;

    @ApiModelProperty("任务类型(任务类型1 入库上架，2 移库上架，3 空托盘上架)")
    private String billType;
    
    @ApiModelProperty("任务行号")
    private String lineId;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("单据行号")
    private String seqNo;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品中文名称")
    private String goodsName;

    @ApiModelProperty("数量")
    private Integer qty;

    @ApiModelProperty("任务进度 0创建 10开始（进入库内） 90完成")
    private String taskState;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;

}
