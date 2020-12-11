package com.prolog.eis.log.service;

import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.page.LogInfoDto;
import com.prolog.eis.dto.page.LogQueryDto;
import com.prolog.framework.core.pojo.Page;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 21:42
 */
public interface ILogService {

    void save(LogDto log);


    /**
     * 分页查日志
     * @param logQueryDto
     * @param tableName
     * @param systemType
     * @return
     * @throws Exception
     */
    List<LogInfoDto> getLogPage(LogQueryDto logQueryDto, String tableName, String systemType) throws Exception;
}
