package com.prolog.eis.enums;

public enum PointChangeEnum {

    RTM0201("RTM0201", "090001000101", "MTR0201"),
    RTM0202("RTM0202", "090001000102", "MTR0202"),
    RTM0203("RTM0203", "090002000101", "MTR0203"),
    RTM0204("RTM0204", "090002000102", "MTR0204"),
    RTM0205("RTM0205", "090003000101", "MTR0205"),
    RTM0206("RTM0206", "090003000102", "MTR0206"),
    RTM0207("RTM0207", "090004000101", "MTR0207"),
    RTM0208("RTM0208", "090004000102", "MTR0208"),
    LXJZ01("LXJZ01", "150000000001", "C0201"),
    LXHK02("LXHK02", "150000000001", "R0201"),


    BCR0101("BCR0101", "020000000001", "R0101"),

    BCR0209("BCR0209", "050000000001", "R0202"),
    BCR0210("BCR0210", "050000000002", "R0203"),
    C0102("C0102", "020000000002", "C0102"),


    MCS01("MCS01", "020001000101", "MCS01"),
    MCS02("MCS02", "020002000101", "MCS02"),
    MCS03("MCS03", "020003000101", "MCS03"),
    MCS04("MCS04", "020004000101", "MCS04");





    /**
     * 点位
     */
    private String point;

    /**
     * 库位
     */
    private String target;

    /**
     * 对应点
     */
    private String corr;

    PointChangeEnum(String point, String target, String corr) {
        this.point = point;
        this.target = target;
        this.corr = corr;
    }


    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCorr() {
        return corr;
    }

    public void setCorr(String corr) {
        this.corr = corr;
    }

    public static String getPoint(String target) {
        for (PointChangeEnum pointChangeEnum : PointChangeEnum.values()) {
            if (pointChangeEnum.target.equals(target)) {
                return pointChangeEnum.point;
            }
        }
        return null;
    }

    public static String getTarget(String point) {
        for (PointChangeEnum pointChangeEnum : PointChangeEnum.values()) {
            if (pointChangeEnum.point.equals(point)) {
                return pointChangeEnum.target;
            }
        }
        return null;
    }

    public static String getCorr(String point) {
        for (PointChangeEnum pointChangeEnum : PointChangeEnum.values()) {
            if (pointChangeEnum.point.equals(point)) {
                return pointChangeEnum.corr;
            }
        }
        return null;
    }
}
