package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:18
 * @Version: V1.0
 */
@Repository
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
            @Result(property = "nextDeviceSystem",  column = "next_device_system")
    })
    List<ContainerPathTaskDetailDTO> listContainerPathTaskDetais(@Param("palletNo") String palletNo, @Param(
            "containerNo") String containerNo, @Param("taskState") Integer taskState);

}
