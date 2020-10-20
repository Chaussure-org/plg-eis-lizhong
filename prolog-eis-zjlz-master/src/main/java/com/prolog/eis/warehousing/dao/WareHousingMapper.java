package com.prolog.eis.warehousing.dao;

import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author wangkang
 * @Description 入库任务数据处理层
 * @CreateTime 2020-10-19 10:10
 */
@Repository
public interface WareHousingMapper extends BaseMapper<WmsInboundTask> {
}
