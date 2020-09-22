package com.prolog.eis.util;

/**
 * 货位四周关系
 */
public class SxStoreRelationUtil {
    /**
     * y轴减1
     */
    public static String yMinusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x);
        String strY = String.format("%04d", y-1);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * y轴加1
     */
    public static String yPlusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x);
        String strY = String.format("%04d", y+1);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴加1
     */
    public static String xPlusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x+1);
        String strY = String.format("%04d", y);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴加1y轴减1
     */
    public static String xPlusOneYJMinusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x+1);
        String strY = String.format("%04d", y-1);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴加1y轴加1
     */
    public static String xPlusOneYPlusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x+1);
        String strY = String.format("%04d", y+1);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴减1
     */
    public static String xMinusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x-1);
        String strY = String.format("%04d", y);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴减1y轴减1
     */
    public static String xMinusOneYMinusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x-1);
        String strY = String.format("%04d", y-1);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴减1y轴加1
     */
    public static String xMinusOneYPlusOne(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x-1);
        String strY = String.format("%04d", y+1);
        strLay = strLay+strX+strY+z;
        return strLay;
    }
    /**
     * x轴y轴不变拼接字符串
     */
    public static String notMinusPlus(Integer lay,Integer x,Integer y,String z){
        String strLay = String.format("%02d", lay);
        String strX = String.format("%04d", x);
        String strY = String.format("%04d", y);
        strLay = strLay+strX+strY+z;
        return strLay;
    }

}
