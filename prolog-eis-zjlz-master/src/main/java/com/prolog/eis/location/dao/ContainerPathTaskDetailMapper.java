package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:18
 * @Version: V1.0
 */
public interface ContainerPathTaskDetailMapper extends EisBaseMapper<ContainerPathTaskDetail> {

    /**
     * 查询容器任务明细信息，包括设备厂商等
     * @param palletNo
     * @param containerNo
     * @param taskState
     * @return
     */
    @Select({"<script>" +
            "select cptd.*,ssa.device_system source_device_system,nsa.device_system next_device_system \r\n" +
            "from container_path_task_detail cptd \r\n" +
            "left join store_area ssa on cptd.source_area = ssa.area_no \r\n" +
            "left join store_area nsa on cptd.next_area = nsa.area_no \r\n" +
            "where cptd.source_area != cptd.next_area \r\n" +
            "<if test='palletNo!=null'> \r\n" +
            "   and cptd.pallet_no = #{palletNo} \r\n" +
            "</if> \r\n" +
            "<if test='containerNo!=null'> \r\n" +
            "   and cptd.container_no = #{containerNo} \r\n" +
            "</if> \r\n" +
            "<if test='taskState!=null'> \r\n" +
            "   and cptd.task_state = #{taskState} \r\n" +
            "</if> \r\n" +
            "order by cptd.id asc \r\n" +
            "</script>"})
    @Results(id="containerPathTaskDetailMap", value={
            @Result(property = "id",  column = "id"),@Result(property = "palletNo",  column = "pallet_no"),
            @Result(property = "containerNo",  column = "container_no"),@Result(property = "sourceArea",  column = "source_area"),
            @Result(property = "sourceLocation",  column = "source_location"),@Result(property = "nextArea",  column = "next_area"),
            @Result(property = "nextLocation",  column = "next_location"),@Result(property = "taskState",  column = "task_state"),
            @Result(property = "taskId",  column = "task_id"),@Result(property = "deviceNo",  column = "device_no"),
            @Result(property = "sortIndex",  column = "sort_index"),@Result(property = "createTime",  column = "create_time"),
            @Result(property = "arriveTime",  column = "arrive_time"),@Result(property = "applyTime",  column = "apply_time"),
            @Result(property = "applyStartTime",  column = "apply_start_time"),@Result(property = "palletArriveTime",  column = "pallet_arrive_time"),
            @Result(property = "updateTime",  column = "update_time"),@Result(property = "sourceDeviceSystem",  column = "source_device_system"),
            @Result(property = "nextDeviceSystem",  column = "next_device_system"),@Result(property = "sendTime",  column = "send_Time"),
            @Result(property = "moveTime",  column = "move_time"),
    })
    List<ContainerPathTaskDetailDTO> listContainerPathTaskDetais(@Param("palletNo") String palletNo, @Param("containerNo") String containerNo, @Param("taskState") Integer taskState);

    /**
     * 找载具
     * @param taskState
     * @return
     */
    @Select({"select t.* from container_path_task_detail t where t.container_no is null and t.task_state = #{taskState}"})
    @ResultMap(value="containerPathTaskDetailMap")
    List<ContainerPathTaskDetailDTO> listRequestPallet(@Param("taskState") Integer taskState);

    /**
     * 查找所有容器
     * @return
     */
    @Select({"select t.* from container_path_task_detail t where t.container_no is not null"})
    @Results(value={
            @Result(property = "id",  column = "id"),@Result(property = "palletNo",  column = "pallet_no"),
            @Result(property = "containerNo",  column = "container_no"),@Result(property = "sourceArea",  column = "source_area"),
            @Result(property = "sourceLocation",  column = "source_location"),@Result(property = "nextArea",  column = "next_area"),
            @Result(property = "nextLocation",  column = "next_location"),@Result(property = "taskState",  column = "task_state"),
            @Result(property = "taskId",  column = "task_id"),@Result(property = "deviceNo",  column = "device_no"),
            @Result(property = "sortIndex",  column = "sort_index"),@Result(property = "createTime",  column = "create_time"),
            @Result(property = "arriveTime",  column = "arrive_time"),@Result(property = "applyTime",  column = "apply_time"),
            @Result(property = "applyStartTime",  column = "apply_start_time"),@Result(property = "palletArriveTime",  column = "pallet_arrive_time"),
            @Result(property = "sendTime",  column = "send_time"),@Result(property = "moveTime",  column = "move_time"),
            @Result(property = "updateTime",  column = "update_time")
    })
    List<ContainerPathTaskDetail> listRequestContainer();


    /**
     * 查路径执行任务数立库到出库口
     * @param locationNo
     * @return
     */
    @Select("SELECT\n" +
            "\tCOUNT(*)\n" +
            "FROM\n" +
            "\tcontainer_path_task_detail \n" +
            "WHERE\n" +
            "\t( task_state IN ( 50, 60 ) AND next_location = #{locationNo} ) \n" +
            "\tOR ( source_location = #{locationNo} AND task_state IN ( 0, 50, 60 ) )")
    int countPathTaskDetail(@Param("locationNo")String locationNo);


    /**
     * 查路径执行任务数每层任务数到出库口
     * @param layer
     * @return
     */
    @Select("SELECT\n" +
            "\tCOUNT(*)\n" +
            "FROM\n" +
            "\tcontainer_path_task_detail \n" +
            "WHERE\n" +
            "\tsource_location != next_location \n" +
            "\tAND SUBSTR( source_location, 1, 2 ) = #{layer} \n" +
            "\tAND source_area = 'SAS01'\n" +
            "\tand task_state = 50")
    int computeXkPathDetail(@Param("layer") String layer);

    /**
     * 计算rcs到回库输送线任务数
     * @param areaNo
     * @return
     */
    @Select("SELECT\n" +
            "\tCOUNT( * ) \n" +
            "FROM\n" +
            "\tcontainer_path_task_detail \n" +
            "WHERE\n" +
            "\t( task_state IN ( 50, 60 ) AND next_area = 'RCS041' ) \n" +
            "\tOR ( task_state = 0 AND source_area = 'RCS041' )")
    int countRcsToWcsPath(@Param("areaNo")String areaNo);

    /**
     * 回库输送线上的托盘
     * @param areaNo
     * @return
     */
    @Select("SELECT\n" +
            "\tCOUNT( * ) \n" +
            "FROM\n" +
            "\tcontainer_path_task_detail \n" +
            "WHERE\n" +
            "\t( task_state IN ( 0,50, 60 ) AND source_area = 'RCS041' ) ")
    int countHkWcsPath(@Param("areaNo")String areaNo);



}
