package com.prolog.eis.util.location;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prolog.eis.dto.location.PathDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author: wuxl
 * @create: 2020-08-26 11:04
 * @Version: V1.0
 */
public class LocationUtils {
    //存放点的集合
    public static Map<String, PathDTO> vertexList = Maps.newHashMap();

    //代表某节点是否在stack中,避免产生回路
    public static Map<String,Boolean> states = new HashMap();

    //存放放入stack中的节点
    private static Stack<String> stack = new Stack();

    //针对x节点添加边节点y
    public static void addPathDTO(String x, String y){
        PathDTO pathDTO = new PathDTO();
        pathDTO.setVertexId(y);
        //第一个边节点
        if (null == vertexList.get(x)) {
            pathDTO.setNext(null);
        } else {//不是第一个边节点,则采用头插法
            pathDTO.setNext(vertexList.get(x));
        }
        vertexList.put(x, pathDTO);
    }

    //输出2个节点之间的输出路径
    public static List<String> visit(String x, String y){
        List<String> locations = Lists.newArrayList();
        //stack top元素
        String top_node;
        //存放当前top元素已经访问过的邻接点,若不存在则置null,此时代表访问该top元素的第一个邻接点
        String adjvex_node = null;
        String next_node;
        stack.add(x);
        states.put(x, true);
        while (!stack.isEmpty()) {
            top_node = stack.peek();
            //找到需要访问的节点
            if (top_node.equals(y)) {
                //打印该路径
                locations.add(printPath());
                adjvex_node = stack.pop();
                states.put(adjvex_node, false);
            } else {
                //访问top_node的第advex_node个邻接点
                next_node = getNextNode(top_node, adjvex_node);
                if (next_node != null) {
                    stack.push(next_node);
                    //置当前节点访问状态为已在stack中
                    states.put(next_node, true);
                    //临接点重置
                    adjvex_node = null;
                } else {//不存在临接点，将stack top元素退出
                    //当前已经访问过了top_node的第adjvex_node邻接点
                    adjvex_node = stack.pop();
                    //不在stack中
                    states.put(adjvex_node, false);
                }
            }
        }
        return locations;
    }

    //得到x的邻接点为y的后一个邻接点位置,为null说明没有找到
    private static String getNextNode(String x, String y){
        String next_node = null;
        List<String> nList = Lists.newArrayList();
        PathDTO pathDTO = vertexList.get(x);
        if (null != pathDTO && y == null) {
            nList.add(pathDTO.getVertexId());
            if (null != pathDTO.getNext()) {
                nList.add(pathDTO.getNext().getVertexId());
            }
            for (String n : nList) {
                //元素还不在stack中
                if (!states.get(n)) {
                    return n;
                }
            }
            return null;
        }

        while (null != pathDTO) {
            //节点未访问
            if (pathDTO.getVertexId().equals(y)) {
                if (null != pathDTO.getNext()) {
                    next_node = pathDTO.getNext().getVertexId();
                    if (!states.get(next_node)) {
                        return next_node;
                    }
                } else {
                    return null;
                }
            }
            pathDTO = pathDTO.getNext();
        }
        return null;
    }

    //打印stack中信息,即路径信息
    private static String printPath(){
        StringBuilder sb = new StringBuilder();
        stack.stream().map(i -> i + "->").forEach(sb::append);
        sb.delete(sb.length()-2,sb.length());
        return sb.toString();
    }
}
