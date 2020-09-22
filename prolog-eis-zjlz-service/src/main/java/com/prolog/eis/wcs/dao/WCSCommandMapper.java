package com.prolog.eis.wcs.dao;

import com.prolog.eis.wcs.model.WCSCommand;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WCSCommandMapper extends BaseMapper<WCSCommand> {

    @Select("select * from wcs_command where id % #{denominator} = #{remainder}")
    List<WCSCommand> getByMod(@Param("denominator") int denominator, @Param("remainder") int remainder);
}
