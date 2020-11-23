package com.prolog.eis.log.dao;

import com.prolog.eis.dto.page.LogInfoDto;
import com.prolog.eis.dto.page.LogQueryDto;
import com.prolog.eis.model.log.WmsLog;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 21:40
 */
public interface WmsLogMapper extends BaseMapper<WmsLog> {

    /**
     * 分页日志查询
     * @param logQueryDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tw.descri AS descri,\n" +
            "\tw.direct AS direct,\n" +
            "\tw.type AS type,\n" +
            "\tw.params AS params,\n" +
            "\tw.success AS success,\n" +
            "\tw.exception AS exception,\n" +
            "\tw.create_time AS createTime,\n" +
            "\tw.method_name AS methodName,\n" +
            "\tw.host_port AS hostPort,\n" +
            "CASE\n" +
            "\t\tw.system_type \n" +
            "\t\tWHEN 1 THEN 'WMS' " +
            "WHEN 2 THEN 'WCS' " +
            "WHEN 3 THEN 'SAS' " +
            "WHEN 4 THEN 'MCS' " +
            "WHEN 5 THEN 'RCS'" +
            "\tEND as systemType \n" +
            "\tFROM\n" +
            "\t\t${tableName} w\n" +
            "where 1 =1" +
            "\t<if test = 'logQueryDto.descri != null and logQueryDto.descri != \"\"'>\n" +
            "     and w.descri like concat('%',#{logQueryDto.descri},'%')\n" +
            "  </if>\n" +
            "\t<if test = 'logQueryDto.direct != null and logQueryDto.direct != \"\"'>\n" +
            "     and w.direct like concat('%',#{logQueryDto.direct},'%')\n" +
            "  </if>\t\n" +
            "\t<if test = 'logQueryDto.params != null and logQueryDto.params != \"\"'>\n" +
            "     and w.params like concat('%',#{logQueryDto.params},'%')\n" +
            "  </if>\n" +
            "\t\t<if test = 'logQueryDto.methodName != null and logQueryDto.methodName != \"\"'>\n" +
            "     and w.method_name like concat('%',#{logQueryDto.methodName},'%')\n" +
            "  </if>\n" +
            "\t\t\t<if test = 'logQueryDto.methodName != null and logQueryDto.methodName != \"\"'>\n" +
            "     and w.method_name like concat('%',#{logQueryDto.methodName},'%')\n" +
            "  </if>\n" +
            "\t<if test = 'logQueryDto.startTime != null '>\n" +
            "     and w.create_time >= #{logQueryDto.startTime}\n" +
            "</if>\n" +
            "<if test = 'logQueryDto.endTime != null '>\n" +
            "    and w.create_time &lt;= #{logQueryDto.endTime}\n" +
            " </if>\n" +
            " <if test = 'logQueryDto.success != null'>\n" +
            "\tand w.success = #{logQueryDto.success}\n" +
            " </if>\t\n" +
            "ORDER BY\n" +
            "\tw.create_time DESC" +
            "</script>")
    List<LogInfoDto> getLogPage(@Param("logQueryDto")LogQueryDto logQueryDto, @Param("tableName") String tableName,@Param("systemType")String systemType);

}
