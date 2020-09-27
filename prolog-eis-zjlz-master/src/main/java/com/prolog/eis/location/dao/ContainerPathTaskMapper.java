package com.prolog.eis.location.dao;

import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:18
 * @Version: V1.0
 */
@Repository
public interface ContainerPathTaskMapper extends EisBaseMapper<ContainerPathTask> {

    @Select({"<script>" +
            "select cpt.* from container_path_task cpt \r\n" +
            "where (cpt.source_location != cpt.target_area or cpt.target_area is null)\r\n" +
            "and (cpt.source_area != cpt.target_location or cpt.target_location is null)\r\n" +
            "<if test='palletNo!=null'> \r\n" +
            "   and cpt.pallet_no = #{palletNo} \r\n" +
            "</if> \r\n" +
            "<if test='containerNo!=null'> \r\n" +
            "   and cpt.container_no = #{containerNo} \r\n" +
            "</if> \r\n" +
            "<if test='taskState!=null'> \r\n" +
            "   and cpt.task_state = #{taskState} \r\n" +
            "</if> \r\n" +
            "order by cpt.create_time asc \r\n" +
            "</script>"})
    @Results(id="containerPathTaskMap", value={
            @Result(property = "id",  column = "id"),@Result(property = "palletNo",  column = "pallet_no"),
            @Result(property = "containerNo",  column = "container_no"),@Result(property = "sourceArea",  column = "source_area"),
            @Result(property = "sourceLocation",  column = "source_location"),@Result(property = "targetArea",  column = "target_area"),
            @Result(property = "targetLocation",  column = "target_location"),@Result(property = "actualHeight",  column = "actual_height"),
            @Result(property = "callBack",  column = "call_back"),@Result(property = "taskType",  column = "task_type"),
            @Result(property = "taskState",  column = "task_state"),@Result(property = "createTime",  column = "create_time"),
            @Result(property = "updateTime",  column = "update_time")
    })
    List<ContainerPathTask> listContainerPathTasks(@Param("palletNo") String palletNo,
                                                   @Param("containerNo") String containerNo, @Param("taskState") Integer taskState);
    
    @Update("")
    void updateContainerPathTaskByContainer(@Param("containerNo") String containerNo);
}