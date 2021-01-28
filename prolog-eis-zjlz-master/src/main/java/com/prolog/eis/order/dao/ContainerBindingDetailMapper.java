package com.prolog.eis.order.dao;

import com.prolog.eis.dto.lzenginee.boxoutdto.LayerTaskDto;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 14:59
 */
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
            "\tJOIN picking_order po ON po.id = ob.picking_order_id \n" +
            "WHERE\n" +
            "\tbd.container_no = #{containerNo} order by po.station_id asc")
    List<Integer> getContainerBindingToStation(String containerNo);

    /**
     * 查找料箱绑定明细
     * @param pickOrderId
     * @return
     */
    @Select("SELECT\n" +
            "\tbd.container_no AS containerNo,\n" +
            "\tbd.order_bill_id AS orderBillId,\n" +
            "\tbd.order_detail_id AS orderDetailId,\n" +
            "\tbd.container_store_id AS containerStoreId,\n" +
            "\tbd.binding_num AS bindingNum,\n" +
            "\tbd.seed_num AS seedNum \n" +
            "FROM\n" +
            "\torder_bill b\n" +
            "\tJOIN container_binding_detail bd ON bd.order_bill_id = b.id \n" +
            "WHERE\n" +
            "\tb.picking_order_id = #{pickOrderId} \n" +
            "\tAND bd.container_no = #{containerNo}")
    List<ContainerBindingDetail> getBindingDetail(@Param("pickOrderId") int pickOrderId,@Param("containerNo") String containerNo);

    /**
     * 找到所有正在执行得箱库入库任务
     * @return
     */
    @Select("SELECT\n" +
            "\tcptd.ID AS id,\n" +
            "\tcptd.pallet_no AS palletNo,\n" +
            "\tcptd.container_no AS containerNo,\n" +
            "\tcptd.source_area AS sourceArea,\n" +
            "\tcptd.source_location AS sourceLocation,\n" +
            "\tcptd.next_area AS nextArea,\n" +
            "\tcptd.next_location AS nextLocation,\n" +
            "\tcptd.task_state AS taskState \n" +
            "FROM\n" +
            "\tcontainer_path_task_detail cptd \n" +
            "WHERE\n" +
            "\tcptd.source_area IN ( 'RS1', 'RS2' ) \n" +
            "\tAND cptd.next_area = 'SAS01' \n" +
            "\tAND cptd.task_state = 50")
    List<ContainerPathTaskDetail> findInStore();

    /**
     * 找到所有正在执行得箱库出库任务
     * @return
     */
    @Select("SELECT\n" +
            "\tcptd.ID AS id,\n" +
            "\tcptd.pallet_no AS palletNo,\n" +
            "\tcptd.container_no AS containerNo,\n" +
            "\tcptd.source_area AS sourceArea,\n" +
            "\tcptd.source_location AS sourceLocation,\n" +
            "\tcptd.next_area AS nextArea,\n" +
            "\tcptd.next_location AS nextLocation,\n" +
            "\tcptd.task_state AS taskState \n" +
            "FROM\n" +
            "\tcontainer_path_task_detail cptd \n" +
            "WHERE\n" +
            "\tcptd.source_area = 'SAS01' \n" +
            "\tAND cptd.next_area IN ( 'CS1', 'CS2' ) \n" +
            "\tAND cptd.task_state = 50\n" +
            "\t")
    List<ContainerPathTaskDetail> findOutStore();


    /**
     *查箱库层入库任务数
     */
    @Select("SELECT\n" +
            "\tlayer,COUNT(1) \n" +
            "FROM\n" +
            "\tsx_store_location cs\n" +
            "\tLEFT JOIN container_path_task_detail ct ON cs.store_no = ct.next_location \n" +
            "WHERE\n" +
            "\tct.source_area IN ( 'RS1', 'RS2' ) \n" +
            "\tAND next_area = 'SAS01' \n" +
            "\tAND ct.task_state = 50\n" +
            "\tGROUP BY layer")
    List<LayerTaskDto> findXkTaskByLayer();
}
