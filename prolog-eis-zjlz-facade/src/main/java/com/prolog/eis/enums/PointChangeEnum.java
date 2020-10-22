package com.prolog.eis.enums;

public enum PointChangeEnum {

    RTM0201("RTM0201","090001000101"),
    RTM0202("RTM0201","090001000102"),
    RTM0203("RTM0201","090002000101"),
    RTM0204("RTM0201","090002000102"),
    RTM0205("RTM0201","090003000101"),
    RTM0206("RTM0201","090003000102"),
    RTM0207("RTM0201","090004000101"),
    RTM0208("RTM0201","090004000102"),
    LXJZ01("LXJZ01","150000000001"),
    LXHK02("LXHK02","150000000001"),


    BCR0101("BCR0101","020000000001"),
    C0101("C0101","020000000001"),


    MCS01("MCS01","020001000101"),
    MCS02("MCS02","020002000101"),
    MCS03("MCS03","020003000101"),
    MCS04("MCS04","020004000101");


    /**
     * 点位
     */
    private String point;

    /**
     * 库位
     */
    private String target;


    PointChangeEnum(String point,String target){
        this.point = point;
        this.target = target;
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



    public static String getPoint(String target){
        for (PointChangeEnum pointChangeEnum : PointChangeEnum.values()) {
            if (pointChangeEnum.target.equals(target)) {
                return pointChangeEnum.point;
            }
        }
        return null;
    }

    public static String getTarget(String point){
        for (PointChangeEnum pointChangeEnum : PointChangeEnum.values()) {
            if (pointChangeEnum.point.equals(point)) {
                return pointChangeEnum.target;
            }
        }
        return null;
    }
}
