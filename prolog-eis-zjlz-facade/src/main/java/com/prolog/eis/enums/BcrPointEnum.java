package com.prolog.eis.enums;

public enum BcrPointEnum {
    /**
     * 二楼入库 BCR
     */
    BCR0205("BCR0205", "RTM0202", "RTM0201"),
    BCR0206("BCR0206", "RTM0204", "RTM0203"),
    BCR0207("BCR0207", "RTM0206", "RTM0205"),
    BCR0208("BCR0208", "RTM0208", "RTM0207"),

    /**
     * 二楼出库 BCR
     */
    BCR0201("BCR0201", "MTR0202", "MTR0201"),
    BCR0202("BCR0202", "MTR0204", "MTR0203"),
    BCR0203("BCR0203", "MTR0206", "RTM0205"),
    BCR0204("BCR0204", "RTM0208", "RTM0207");

    private String bcrNo;

    private String rcsPoint;

    private String mcsPoint;


    public static String findRcsPoint(String bcrNo) {
        for (BcrPointEnum bcrPointEnum : BcrPointEnum.values()) {
            if (bcrPointEnum.bcrNo.equals(bcrNo)) {
                return bcrPointEnum.rcsPoint;
            }
        }
        return null;
    }

    public static String findMcsPoint(String bcrNo) {
        for (BcrPointEnum bcrPointEnum : BcrPointEnum.values()) {
            if (bcrPointEnum.bcrNo.equals(bcrNo)) {
                return bcrPointEnum.mcsPoint;
            }
        }
        return null;
    }


    BcrPointEnum(String bcrNo, String rcsPoint, String mcsPoint) {
        this.bcrNo = bcrNo;
        this.rcsPoint = rcsPoint;
        this.mcsPoint = mcsPoint;
    }

    public String getBcrNo() {
        return bcrNo;
    }

    public void setBcrNo(String bcrNo) {
        this.bcrNo = bcrNo;
    }

    public String getRcsPoint() {
        return rcsPoint;
    }

    public void setRcsPoint(String rcsPoint) {
        this.rcsPoint = rcsPoint;
    }

    public String getMcsPoint() {
        return mcsPoint;
    }

    public void setMcsPoint(String mcsPoint) {
        this.mcsPoint = mcsPoint;
    }


}
