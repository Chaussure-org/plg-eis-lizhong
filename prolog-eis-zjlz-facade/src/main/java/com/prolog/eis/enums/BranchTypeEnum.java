package com.prolog.eis.enums;

/**
 * @Author wangkang
 * @Description 库区枚举
 * @CreateTime 2020-10-14 14:57
 */
public enum BranchTypeEnum {
    XSK("XSK","A"),
    LTK("LTK","B");

    /**
     * wms库区
     */
    private String wmsBranchType;

    /**
     * eis库区
     */
    private String eisBranchType;

    BranchTypeEnum(String wmsBranchType,String eisBranchType){
        this.wmsBranchType = wmsBranchType;
        this.eisBranchType = eisBranchType;
    }

    public String getWmsBranchType() {
        return wmsBranchType;
    }

    public void setWmsBranchType(String wmsBranchType) {
        this.wmsBranchType = wmsBranchType;
    }

    public String getEisBranchType() {
        return eisBranchType;
    }

    public void setEisBranchType(String eisBranchType) {
        this.eisBranchType = eisBranchType;
    }

    public static String getEisBranchType(String wmsBranchType){
        for (BranchTypeEnum branchTypeEnum : BranchTypeEnum.values()) {
            if (branchTypeEnum.wmsBranchType.equals(wmsBranchType)) {
                return branchTypeEnum.eisBranchType;
            }
        }
        return null;
    }

    public static String getWmsBranchType(String eisBranchType){
        for (BranchTypeEnum branchTypeEnum : BranchTypeEnum.values()) {
            if (branchTypeEnum.eisBranchType.equals(eisBranchType)) {
                return branchTypeEnum.wmsBranchType;
            }
        }
        return null;
    }
}
