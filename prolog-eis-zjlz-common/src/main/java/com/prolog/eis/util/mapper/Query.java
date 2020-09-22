package com.prolog.eis.util.mapper;

import com.prolog.eis.util.StringPool;
import com.prolog.framework.core.util.TableUtils;

import java.text.MessageFormat;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * @author panteng
 * @description: 查询,待完善
 * @date 2020/4/18 11:41
 */
public class Query {
    public static final String QUERY = "arg0";
    private static final String WRAPPER_PARAM_FORMAT = "#{%s.paramNameValuePairs.%s}";

    private Class<?> cls;

    public List<String> conditions = new LinkedList<>();
    protected Map<String, Object> paramNameValuePairs;

    public Query(Class<?> cls) {
        this.cls = cls;
        this.paramNameValuePairs = new HashMap<>(cls.getDeclaredFields().length);
    }

    public Class<?> getCls() {
        return this.cls;
    }

    public void addIn(String cloumn, Collection<?> coll) {
        String columnName = TableUtils.getColumnName(cloumn, this.cls);
        String ins = coll.stream().map(i -> i.toString())
                .collect(joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
        addCondition(MessageFormat.format("and t.{0} {1} {2}", columnName, SqlKeyword.IN.getKeyword(), ins));
    }

    public void addNotIn(String cloumn, Collection<?> coll) {
        String columnName = TableUtils.getColumnName(cloumn, this.cls);
        String ins = coll.stream().map(i -> i.toString())
                .collect(joining(StringPool.COMMA, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
        addCondition(MessageFormat.format("and t.{0} {1} {2} {3}", columnName, SqlKeyword.NOT.getKeyword(), SqlKeyword.IN.getKeyword(), ins));
    }

    public void addEq(String cloumn, Object obj) {
        String columnName = TableUtils.getColumnName(cloumn, this.cls);
        if (obj == null) {
            addCondition(MessageFormat.format("and t.{0} is null", columnName));
        } else {
            String sql = getSql(cloumn, obj);
            addCondition(MessageFormat.format("and t.{0} {1} {2}", columnName, SqlKeyword.EQ.getKeyword(), sql));

        }
    }

    public void addNotEq(String cloumn, Object obj) {
        String columnName = TableUtils.getColumnName(cloumn, this.cls);
        if (obj == null) {
            addCondition(MessageFormat.format("and t.{0} is not null", columnName));
        } else {
            String sql = getSql(cloumn, obj);
            addCondition(MessageFormat.format("and t.{0}  {1}  {2}", columnName, SqlKeyword.NE.getKeyword(), sql));
        }
    }



    public void or() {
        addCondition(SqlKeyword.OR.getKeyword());
    }

    private void addCondition(String sql) {
        conditions.add(StringPool.SPACE + sql + StringPool.SPACE);
    }


    private String getSql(String cloumn, Object obj) {
        String key = cloumn + String.valueOf(conditions.size());
        String sql = String.format(WRAPPER_PARAM_FORMAT, QUERY, key);
        paramNameValuePairs.put(key, obj);
        return sql;
    }
}
