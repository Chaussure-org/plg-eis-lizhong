package com.prolog.eis.order.dao;

import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 14:59
 */
@Service
public interface ContainerBindingDetailMapper extends BaseMapper<ContainerBindingDetail> {
    /**
     * 获取绑定明细站台
     * @param containerNo
     * @return
     */
    @Select("SELECT\n" +
            "\tpo.station_id \n" +
            "FROM\n" +
            "\tcontainer_binding_detail bd\n" +
            "\tJOIN order_bill ob ON ob.id = bd.order_bill_id\n" +
            "\tJOIN picking_order po ON po.id = ob.id \n" +
            "WHERE\n" +
            "\tbd.container_no = #{containerNo} order by po.station_id desc")
    List<Integer> getContainerBindingToStation(String containerNo);
}
