package com.prolog.eis.enums;

public enum BcrPointEnum {
    /**
     * 二楼入库 BCR
     */
    BCR0201("BCR0201", "RTM0202", "RTM0201", "", ""),
    BCR0202("BCR0202", "RTM0204", "RTM0203", "", ""),
    BCR0203("BCR0203", "RTM0206", "RTM0205", "", ""),
    BCR0204("BCR0204", "RTM0208", "RTM0207", "080005000001", "052508XY061250"),

    /**
     * 二楼出库 BCR
     */
    BCR0205("BCR0205", "MTR0202", "MTR0201", "", ""),
    BCR0206("BCR0206", "MTR0204", "MTR0203", "", ""),
    BCR0207("BCR0207", "MTR0206", "MTR0205", "", ""),
    BCR0208("BCR0208", "MTR0208", "MTR0207", "080005000003", "056316XY061250");

    /**
     * bcr 编号
     */
    private String bcrNo;
    /**
     * rcs point
     */
    private String rcsPoint;

    /**
     * mcs point
     */
    private String mcsPoint;

    /**
     * mcs 坐标
     */
    private String mcsLocation;

    /**
     * rcs 坐标
     */
    private String rcsLocation;

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

    public static String findMcsLocation(String mcsPoint) {
        for (BcrPointEnum bcrPointEnum : BcrPointEnum.values()) {
            if (bcrPointEnum.mcsPoint.equals(mcsPoint)) {
                return bcrPointEnum.mcsLocation;
            }
        }
        return null;
    }

    public static String findRcsLocation(String rcsPoint) {
        for (BcrPointEnum bcrPointEnum : BcrPointEnum.values()) {
            if (bcrPointEnum.rcsPoint.equals(rcsPoint)) {
                return bcrPointEnum.rcsLocation;
            }
        }
        return null;
    }


    BcrPointEnum(String bcrNo, String rcsPoint, String mcsPoint, String mcsLocation, String rcsLocation) {
        this.bcrNo = bcrNo;
        this.rcsPoint = rcsPoint;
        this.mcsPoint = mcsPoint;
        this.mcsLocation = mcsLocation;
        this.rcsLocation = rcsLocation;
    }

    public String getMcsLocation() {
        return mcsLocation;
    }

    public void setMcsLocation(String mcsLocation) {
        this.mcsLocation = mcsLocation;
    }

    public String getRcsLocation() {
        return rcsLocation;
    }

    public void setRcsLocation(String rcsLocation) {
        this.rcsLocation = rcsLocation;
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
