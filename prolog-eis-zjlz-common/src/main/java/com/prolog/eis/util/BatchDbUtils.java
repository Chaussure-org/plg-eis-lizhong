package com.prolog.eis.util;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panteng
 * @description: 批量DB操作utils
 * @date 2019/7/2 18:26
 */
public class BatchDbUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(BatchDbUtils.class);

    //更新
    public static final int TYPE_ONE = 1;
    //插入
    public static final int TYPE_TWO = 2;
    public static final int FIND_IDS = 800;

    /**
     * @param namespace + statement
     * @param list        操作集合
     * @param type        操作类型 1更新 2插入
     * @param batchNumber 每批数量
     */
	public static void batchOperationDb(String namespace, List<?> list, int type, int batchNumber) {
        //获取开始时间
        long startTime = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.info("list is empty");
            return;
        }
        SqlSessionFactory sessionFactory = SpringContextUtils.getBean(SqlSessionFactory.class);
        SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH,false);

        //拆分集合
        List<List<Object>> subList = getSubList(list, batchNumber);
        try {
            subList.stream().forEach(sl -> {
                int number = 0;
                if (type == TYPE_ONE) {
                    //更新
                    number = sqlSession.update(namespace, sl);
                } else if (type == TYPE_TWO) {
                    //插入
                    number = sqlSession.insert(namespace, sl);
                }
                sqlSession.commit();
            });
        } catch (Exception e) {
            throw e;
        } finally {
            sqlSession.close();
        }
        //获取结束时间
        long endTime = System.currentTimeMillis();
        LOGGER.info("批量操作方法:{},操作数量:{},操作耗时:{}ms", namespace, list.size(), (endTime - startTime));
    }


    /**
     * 集合分割
     *
     * @param list 集合
     * @param len  子集合大小
     * @param <T>
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> List<List<T>> getSubList(List list, int len) {
        List<List<T>> listGroup = new ArrayList<List<T>>();
        if (list.size() < len) {
            listGroup.add(list);
            return listGroup;
        }
        int listSize = list.size();
        //子集合的长度
        int toIndex = len;
        for (int i = 0; i < list.size(); i += len) {
            if (i + len > listSize) {
                toIndex = listSize - i;
            }
            List<T> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }


    /**
     * 集合分割 ids
     *
     * @param <?>
     * @param list 集合
     * @param len  子集合大小
     * @return
     */
    public static List<List<Integer>> getSubListIds(List<Integer> list, int len) {
        List<List<Integer>> listGroup = new ArrayList<>();
        if (list.size() < len) {
            listGroup.add(list);
            return listGroup;
        }
        int listSize = list.size();
        //子集合的长度
        int toIndex = len;
        for (int i = 0; i < list.size(); i += len) {
            if (i + len > listSize) {
                toIndex = listSize - i;
            }
            List<Integer> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }
}
