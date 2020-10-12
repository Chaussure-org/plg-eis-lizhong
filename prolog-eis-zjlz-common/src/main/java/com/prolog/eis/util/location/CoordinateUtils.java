package com.prolog.eis.util.location;

import org.springframework.util.StringUtils;

/**
 * 贝泰妮解析坐标
 * @author: wuxl
 * @create: 2020-09-11 17:09
 * @Version: V1.0
 */
public class CoordinateUtils {

    /**
     * mcs坐标转点位
     * @param x
     * @param y
     * @return
     */
    public static String xyToLocationForMcs(int layer, int x, int y){
        return String.format("%s%s%s", String.format("%02d", layer), String.format("%04d", x), String.format("%04d", y));
    }

    /**
     * mcs点位转坐标
     * @param location
     * @return
     * @throws Exception
     */
    public static int[] locationToXyForMcs(String location) throws Exception {
        if(StringUtils.isEmpty(location) || location.length() != 10){
            throw new Exception("解析有误");
        }
        int layer = Integer.parseInt(location.substring(0, 2));
        int x = Integer.parseInt(location.substring(2, 6));
        int y = Integer.parseInt(location.substring(location.length() - 4));
        return new int[]{layer, x, y};
    }

    /**
     * rcs坐标转点位
     * @param x
     * @param y
     * @return
     */
    public static String xyToLocationForRcs(int x, int y){
        return String.format("%06d", x) + "XY" + String.format("%06d", y);
    }

    /**
     * rcs点位转坐标
     * @param location
     * @return
     * @throws Exception
     */
    public static int[] locationToXyForRcs(String location) throws Exception {
        if(StringUtils.isEmpty(location) || location.length() != 14){
            throw new Exception("解析有误");
        }
        int x = Integer.parseInt(location.substring(0, 6));
        int y = Integer.parseInt(location.substring(location.length() - 6));
        return new int[]{x, y};
    }

    /**
     * sas坐标转点位
     * 随便定义一个，后期调整
     * @param x
     * @param y
     * @return
     */
    public static String xyToLocationForSas(int layer, int x, int y){
        return String.format("%s%s%s", String.format("%02d", layer), String.format("%03d", x), String.format("%03d", y));
    }

    /**
     * sas点位转坐标
     * 随便定义一个，后期调整
     * @param location
     * @return
     * @throws Exception
     */
    public static int[] locationToXyForSas(String location) throws Exception {
        if(StringUtils.isEmpty(location) || location.length() != 10){
            throw new Exception("解析有误");
        }
        int layer = Integer.parseInt(location.substring(0, 2));
        int x = Integer.parseInt(location.substring(2, 5));
        int y = Integer.parseInt(location.substring(location.length() - 3));
        return new int[]{layer, x, y};
    }
}
