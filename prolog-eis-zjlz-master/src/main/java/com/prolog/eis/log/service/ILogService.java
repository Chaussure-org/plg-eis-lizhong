package com.prolog.eis.log.service;

import com.prolog.eis.model.log.Log;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 21:42
 */
public interface ILogService {
    void save(Log log);
    void update(Log log);
}
