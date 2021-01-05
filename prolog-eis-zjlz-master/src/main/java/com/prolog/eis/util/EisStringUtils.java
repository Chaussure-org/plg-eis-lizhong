package com.prolog.eis.util;

import com.alibaba.druid.sql.visitor.functions.Substring;
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
    public static String getRemouldId(int i) {
        String format = String.format("%010d", i);
        return format;
    }

    /**
     * MCS-->EIS 由于坐标不同 MCS左边转换为 EIS坐标
     *  030005001004
     * @param point 0100010001 z代表层，两位; x代表排，四位 ; y代表列,四位,升位  与巷道 平行的是 y轴
     * @return 010002000101
     * 010002000101
     */
    public static String getMcsPoint(String point) {
        /**
         * 1.层 不变  020005000203   0200380021
         */
        String layer = point.substring(0, 2);
        String x = "";

        int eisX = Integer.valueOf(point.substring(2, 6));

        int eisY = Integer.valueOf(point.substring(6, 10)) + 19;

        int eisAss = Integer.valueOf(point.substring(10, 12));

        // 每个巷道 的起始值
        int base = (eisX - 2) * 5 + 20;

        if (eisAss < 3) {
            x = String.format("%04d", base + eisAss - 1);
        } else {
            x = String.format("%04d", base + eisAss);
        }

        String y = String.format("%04d", eisY);

        return layer + x + y;
    }
}
