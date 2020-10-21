package com.prolog.eis.order.dao;

import com.prolog.eis.model.order.SeedInfo;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/13 17:22
 */
public interface SeedInfoMapper extends BaseMapper<SeedInfo> {

    /**
     * 查询最近一条播种记录
     * @param orderDetailId
     * @return
     */
    @Select("select * from seed_info s where s.order_detail_id = #{orderDetailId} order by create_time desc limit 1")
    SeedInfo findSeedInfoByOrderDetail(@Param("orderDetailId") int orderDetailId);
}
