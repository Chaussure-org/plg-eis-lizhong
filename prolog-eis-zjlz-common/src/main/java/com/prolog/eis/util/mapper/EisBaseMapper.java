package com.prolog.eis.util.mapper;

import com.prolog.framework.dao.helper.SqlFactory;
import com.prolog.framework.dao.helper.SqlMethod;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author panteng
 * @description: eis扩展BaseMapper
 * @date 2020/4/17 18:21
 */
public interface EisBaseMapper<T> extends BaseMapper<T> {

    @SelectProvider(
            method = "findByEisQuery",
            type = EisSqlFactory.class
    )
    List<T> findByEisQuery(@Param(Query.QUERY) Query qry);
    
    /**
	 * 
	 *根据map查询单条数据
	 *
	 * @Title: findByMap   
	 * @param c
	 * @param andMap 可为null
	 * @return List<T>      
	 * @date 2018年4月13日 下午3:25:36
	 */
	@SelectProvider(method=SqlMethod.SELECT_BY_MAP,type=SqlFactory.class)
	T findFirstByMap(@Param("params") Map<String, Object> andMap, @Param("c") Class<T> c);
}
