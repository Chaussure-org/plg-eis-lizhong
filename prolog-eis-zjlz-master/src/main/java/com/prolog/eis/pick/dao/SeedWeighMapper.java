package com.prolog.eis.pick.dao;

import com.prolog.eis.model.order.SeedWeigh;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/21 9:20
 * 称重检测mapper
 */
public interface SeedWeighMapper extends BaseMapper<SeedWeigh> {
    /**
     * 得到订单拖之前重量
     * @param orderBillId
     * @param orderTrayNo
     * @param seedId
     * @return
     */
    @Select("SELECT\n" +
            "\t IFNULL((sum( IF ( w.second_weigh IS NULL, w.first_weigh, w.second_weigh ) ) ),0) \n" +
            "FROM\n" +
            "\tseed_info s\n" +
            "\tJOIN seed_weigh w ON w.seed_info_id = s.id \n" +
            "WHERE\n" +
            "\ts.order_bill_id = #{orderBillId} \n" +
            "\tAND s.order_tray_no = #{orderTrayNo} \n" +
            "\tAND s.id <> #{seedId}")
    BigDecimal getBeforeOrderTrayWeight(@Param("orderBillId") int orderBillId,@Param("orderTrayNo") String orderTrayNo,@Param("seedId") int seedId);
}
