package com.prolog.eis.util;

import com.prolog.eis.configuration.EisProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/20 11:18
 */
public class EisStringUtils {
    @Autowired
    private EisProperties eisProperties;

    /**
     * 回传wms拼接商品id十位
     */
    public static String getRemouldId(int i){
        String format = String.format("%010d", i);
        return format;
    }

    /**
     * MCS-->EIS 由于坐标不同 MCS左边转换为 EIS坐标
     * @param point
     * @return
     */
    public static String getMcsPoint(String point){
        return "";
    }
}
