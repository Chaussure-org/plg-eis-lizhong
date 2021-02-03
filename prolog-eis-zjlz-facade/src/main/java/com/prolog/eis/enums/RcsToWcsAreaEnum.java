package com.prolog.eis.enums;

/**
 * @Author dengj
 * @Date 2021/2/2 0:41
 * @Version 1.0
 */
public enum RcsToWcsAreaEnum {

    /**
     *立库回库1巷道
     */
    LKHK1("RCS031","WCS012"),
    /**
     * 立库回库2巷道
     */
    LKHK2("RCS021","WCS022"),
    /**
     * 立库回库3巷道
     */
    LKHK3("RCS031","WCS032"),
    /**
     * 立库回库4巷道
     */
    LKHK4("RCS041","WCS041");

    /**
     * agv对应wcs区域
     */
    private String rcsArea;
    /**
     * wcs
     *
     */
    private String wcsArea;


    RcsToWcsAreaEnum(String ecsArea, String wcsArea) {
        this.rcsArea = ecsArea;
        this.wcsArea = wcsArea;
    }


    public String getrcsArea() {
        return rcsArea;
    }

    public void setrcsArea(String rcsArea) {
        this.rcsArea = rcsArea;
    }

    public String getwcsArea() {
        return wcsArea;
    }

    public void setwcsArea(String wcsArea) {
        this.wcsArea = wcsArea;
    }


    public static String getWcsArea(String rcsArea){
        for (RcsToWcsAreaEnum rcsToWcsAreaEnum : RcsToWcsAreaEnum.values()) {
            if (rcsToWcsAreaEnum.rcsArea.equals(rcsArea)) {
                return rcsToWcsAreaEnum.wcsArea;
            }
        }
        return null;
    }

    public static String getRcsArea(String wcsArea){
        for (RcsToWcsAreaEnum rcsToWcsAreaEnum : RcsToWcsAreaEnum.values()) {
            if (rcsToWcsAreaEnum.wcsArea.equals(wcsArea)) {
                return rcsToWcsAreaEnum.rcsArea;
            }
        }
        return null;
    }
}
