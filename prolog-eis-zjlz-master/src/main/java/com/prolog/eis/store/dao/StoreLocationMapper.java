package com.prolog.eis.store.dao;


import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StoreLocationMapper extends BaseMapper<SxStoreLocation> {

    @Select("select value from temp")
    List<String> find();

    @Insert("INSERT INTO `agv_storagelocation` ( `area_no`, `location_no`, `ceng`, `x`, `y`, `location_type`, `tally_code`, `task_lock`, `storage_lock`, `device_no`) " +
            "VALUES ( 'RCS01', #{location}, 1, #{x},#{y}, 3, NULL, 0, 0, '8');")
    void testSave(@Param("location")String location,@Param("x")String x,@Param("y")String y);

}
