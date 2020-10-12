package com.prolog.eis.log.service;

import com.prolog.eis.dto.log.LogDto;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 21:42
 */
public interface ILogService {
    void save(LogDto log);
}
