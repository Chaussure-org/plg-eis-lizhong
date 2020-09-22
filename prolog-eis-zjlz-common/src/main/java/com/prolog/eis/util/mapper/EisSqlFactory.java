package com.prolog.eis.util.mapper;

import com.google.common.collect.Lists;
import com.prolog.eis.util.StringPool;
import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.util.ClassUtils;
import com.prolog.framework.core.util.TableUtils;
import com.prolog.framework.dao.helper.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * @author panteng
 * @description:
 * @date 2020/4/17 18:23
 */
public class EisSqlFactory{
    private Logger logger = LoggerFactory.getLogger(EisSqlFactory.class);

    public String findByEisQuery(Query query) throws Exception {
        Class<?> c = query.getCls();
        List<String> conditions = query.conditions;
        String tableName = TableUtils.getTableName(c);
        String tableAlias = "t";
        String asstr = getSelectColumns(c, tableAlias);
        String mainTableSql = tableName.toLowerCase().equals(tableAlias) ? tableAlias : String.format("%s %s", tableName, tableAlias);
        String oneSql = StringPool.EMPTY;
        String pstr = String.join(StringPool.SPACE,conditions);
        String orderStr = StringPool.EMPTY;
        pstr = pstr.trim().equals("") ? "1=1" : "1=1" + pstr;
        SqlHelper sqlHelper = SqlHelper.SELECT_FIELDS_BY_CRITERIA;
        String sql = String.format(sqlHelper.getSql(), asstr, mainTableSql, oneSql, pstr + StringPool.SPACE + orderStr);
        logger.debug(sql);
        //System.out.println(sql);
        return sql;
    }

    private String getSelectColumns(Class<?> c,String tableAlias) throws Exception {
        List<String> newArrayList = Lists.newArrayList();
        Iterator fieldIterator = ClassUtils.getFields(c).iterator();
        String format = tableAlias + ".%s as %s";
        while (fieldIterator.hasNext()){
            Field next = (Field)fieldIterator.next();
            if(next.isAnnotationPresent(Column.class)){
                String name = next.getName();
                String cloumn = next.getAnnotation(Column.class).value();
                newArrayList.add(String.format(format, cloumn, name));
            }
        }
        if(newArrayList==null || newArrayList.size()==0){
            throw new Exception("要查询的字段为空,请在查询类加Column注解");
        }
        return String.join(",",newArrayList);
    }
}
