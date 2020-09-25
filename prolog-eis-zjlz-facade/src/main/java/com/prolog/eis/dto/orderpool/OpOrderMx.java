package com.prolog.eis.dto.orderpool;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author panteng
 * @description: 订单明细 查询所映射的表  非PO
 * @date 2020/4/19 16:00
 */
@Table("order_mx")
public class OpOrderMx{
    @Column("id")
    @ApiModelProperty("id")
    private Integer id;

    @Column("order_hz_id")
    @ApiModelProperty("出库单汇总id")
    private Integer orderHzId;

    @Column("goods_id")
    @ApiModelProperty("商品id")
    private Integer goodsId;

    @Column("plan_num")
    @ApiModelProperty("计划数量")
    private Integer planNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderHzId() {
        return orderHzId;
    }

    public void setOrderHzId(Integer orderHzId) {
        this.orderHzId = orderHzId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }
}
