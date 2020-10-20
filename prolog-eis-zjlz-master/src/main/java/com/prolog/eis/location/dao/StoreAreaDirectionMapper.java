package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.StoreAreaDirectionDTO;
import com.prolog.eis.model.location.StoreAreaDirection;
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
public interface StoreAreaDirectionMapper extends EisBaseMapper<StoreAreaDirection> {

    /**
     * 查询某个区域的相关信息
     * @param sourceAreaNo
     * @param targetAreaNo
     * @param containerNo
     * @return
     */
    @Select("select sad.*,sat.max_count,sas.temporary_area source_temporary_area,sat.temporary_area target_temporary_area,sat.area_type, \r\n" +
            "   sas.location_no source_location_no,sat.location_no target_location_no, \r\n" +
            "    ( select count(*) from container_path_task_detail \r\n" +
            "       where ((source_area = sad.target_area_no and task_state in (0, 10, 20, 30, 40)) \r\n" +
            "       or (next_area = sad.target_area_no and task_state in ( 50, 60 ))) \r\n" +
            "       and container_no != #{containerNo} ) place_count, \r\n" +
            "    ( select count(*) from container_path_task_detail \r\n" +
            "       where next_area = sad.target_area_no and task_state = -1 and container_no != #{containerNo} ) preempt_count, \r\n" +
            "    ( select count(*) from container_path_task_detail \r\n" +
            "       where next_area = sad.target_area_no and container_no != #{containerNo} and sat.area_type = 20) joint_point_count \r\n" +
            "from store_area_direction sad \r\n" +
            "left join store_area sas on sad.source_area_no = sas.area_no \r\n" +
            "left join store_area sat on sad.target_area_no = sat.area_no \r\n" +
            "where sad.source_area_no = #{sourceAreaNo} \r\n" +
            "and sad.target_area_no = #{targetAreaNo} \r\n" +
            "order by sad.id asc")
    @Results(id="storeAreaDirectionMap", value={
            @Result(property = "id",  column = "id"),@Result(property = "sourceAreaNo",  column = "source_area_no"),
            @Result(property = "targetAreaNo",  column = "target_area_no"),@Result(property = "maxHeight",  column = "max_height"),
            @Result(property = "pathStep",  column = "path_step"),@Result(property = "pathPower",  column = "path_power"),
            @Result(property = "createTime",  column = "create_time"),@Result(property = "updateTime",  column = "update_time"),
            @Result(property = "maxCount",  column = "max_count"),@Result(property = "realCount",  column = "real_count"),
            @Result(property = "placeCount",  column = "place_count"),@Result(property = "preemptCount",  column = "preempt_count"),
            @Result(property = "temporaryArea",  column = "temporary_area"),@Result(property = "areaType",  column = "area_type"),
            @Result(property = "jointPointCount",  column = "joint_point_count"),@Result(property = "sourceTemporaryArea",  column = "source_temporary_area"),
            @Result(property = "targetTemporaryArea",  column = "target_temporary_area"),@Result(property = "sourceLocationNo",  column = "source_location_no"),
            @Result(property = "targetLocationNo",  column = "target_location_no")
    })
    List<StoreAreaDirectionDTO> listStoreAreaDirectionsByParam(@Param("sourceAreaNo") String sourceAreaNo, @Param("targetAreaNo") String targetAreaNo, @Param("containerNo") String containerNo);
}
