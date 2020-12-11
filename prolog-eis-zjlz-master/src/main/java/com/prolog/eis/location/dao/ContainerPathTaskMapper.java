package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.ContainerPathTaskDTO;
import com.prolog.eis.dto.location.StoreAreaContainerCountDTO;
import com.prolog.eis.dto.location.TaskCountDto;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:18
 * @Version: V1.0
 */
public interface ContainerPathTaskMapper extends EisBaseMapper<ContainerPathTask> {

    /**
     * 查询可生成路径任务的数据
     *
     * @param palletNo    载具号
     * @param containerNo 容器号
     * @param taskState   路径状态
     * @return
     */
    @Select({"<script>" +
            "select cpt.* from container_path_task cpt \r\n" +
            "where (cpt.source_area != cpt.target_area or cpt.target_area is null or cpt.source_location != cpt.target_location or cpt.target_location is null) \r\n" +
            "<if test='palletNo!=null and palletNo!=\"\"'> \r\n" +
            "   and cpt.pallet_no = #{palletNo} \r\n" +
            "</if> \r\n" +
            "<if test='containerNo!=null and containerNo!=\"\"'> \r\n" +
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
    List<ContainerPathTask> listContainerPathTasks(@Param("palletNo") String palletNo, @Param("containerNo") String containerNo, @Param("taskState") Integer taskState);
    
    @Update("")
    void updateContainerPathTaskByContainer(@Param("containerNo") String containerNo);

    /**
     * 找载具
     * @param palletNo
     * @return
     */
    @Select({"<script>" +
            "select t.* from container_path_task t \r\n" +
            "where t.container_no is null \r\n" +
            "and t.task_type = 0 and task_state = 0 \r\n" +
            "<if test='palletNo!=null'> \r\n" +
            "   and t.pallet_no = #{palletNo} \r\n" +
            "</if> \r\n" +
            "and rownum = 1" +
            "</script>"})
    @ResultMap(value="containerPathTaskMap")
    ContainerPathTask getRequestPallet(@Param("palletNo") String palletNo);

    @Select("select t.target_area areaNo,count(*) containerCount from container_path_task t\n" +
            "where t.container_no is not null\n" +
            "group by t.target_area")
    List<StoreAreaContainerCountDTO> getStoreAreaContainerCount();

    @Select("select count(*) from container_path_task t\n" +
            "where t.container_no is not null and t.target_area = #{areaNo}")
    int getStoreAreaContainerCountByArea(@Param("areaNo") String areaNo);

    /**
     * 根据商品id找托盘
     * @param goodsId
     * @return                                                                                     Co
     */
    @Select({"select cpt.*,agvs.x,agvs.y from container_path_task cpt \r\n" +
            "right join container_store c on c.container_no = cpt.container_no \r\n" +
            "left join agv_storagelocation agvs on cpt.source_location = agvs.location_no \r\n" +
            "where cpt.task_type = 0 and cpt.task_state = 0 \r\n" +
            "and c.goods_id = #{goodsId}"})
    @Results(id="containerPathTaskDTOMap", value={
            @Result(property = "id",  column = "id"),@Result(property = "palletNo",  column = "pallet_no"),
            @Result(property = "containerNo",  column = "container_no"),@Result(property = "sourceArea",  column = "source_area"),
            @Result(property = "sourceLocation",  column = "source_location"),@Result(property = "targetArea",  column = "target_area"),
            @Result(property = "targetLocation",  column = "target_location"),@Result(property = "actualHeight",  column = "actual_height"),
            @Result(property = "callBack",  column = "call_back"),@Result(property = "taskType",  column = "task_type"),
            @Result(property = "taskState",  column = "task_state"),@Result(property = "createTime",  column = "create_time"),
            @Result(property = "updateTime",  column = "update_time")
    })
    List<ContainerPathTaskDTO> listContainerPathTaskByGoodsId(@Param("goodsId") int goodsId);

    /**
     * 查询每个入库口任务
     * @return
     */
    @Select("select c.target_area as areaNo,COUNT(c.target_area) as taskCount\n" +
            "from container_path_task c where c.source_location in (\"BCR0102\",\"BCR0103\") group by c.target_area;")
    List<TaskCountDto> findInTaskCount();
}
