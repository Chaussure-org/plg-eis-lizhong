package com.prolog.eis.log.dao;

import com.prolog.eis.dto.TestDto;
import com.prolog.eis.model.log.McsLog;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 21:40
 */
public interface McsLogMapper extends BaseMapper<McsLog> {

    @Select("SELECT DISTINCT\n" +
            "\tSUBSTR( params, 70, 8 ) as containerNO,\n" +
            "\tSUBSTR( params, 91, 10 ) as address,\n" +
            "\tSUBSTR( params, 113, 12 ) as location\n" +
            "FROM\n" +
            "\tmcs_log \n" +
            "WHERE\n" +
            "\ttype = '2' \n" +
            "\tAND SUBSTR( params, 70, 8 ) IN ( SELECT container_no FROM container_store WHERE owner_id IS NULL and create_time < '2021-01-08 14:07:25')")
    List<TestDto> frindLocation();


    @Insert("\tINSERT INTO container_path_task_detail ( pallet_no, container_no, source_area, source_location, next_area, next_location, task_state, task_id, device_no, sort_index, create_time, arrive_time, apply_time, apply_start_time, pallet_arrive_time, binding_pallet_time, binding_start_time, update_time, send_time, move_time) VALUES " +
            " ( #{containerNo}, #{containerNo}, 'MCS04', #{location}, 'MCS04', #{location}, 0, NULL, NULL, 1, '2021-01-08 14:07:25', '2021-01-08 15:45:01', NULL, NULL, NULL, NULL, NULL, '2021-01-08 14:52:30', NULL, NULL);")
    void saveDetail(@Param("containerNo") String containerNo, @Param("location") String location);
}
