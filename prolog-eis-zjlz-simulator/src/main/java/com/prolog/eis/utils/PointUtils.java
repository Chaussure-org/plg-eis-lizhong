package com.prolog.eis.utils;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 13:17
 */
public class PointUtils {
    /**
     * bcr点位
     */
    public static final String[] BCR_POINTS_IN= {
            "BCR0201","BCR0202",
            "BCR0203","BCR0204",
            "BCR0209","BCR0210",
            "LXHK02"};
    public static final String[] BCR_POINTS_OUT= {
            "BCR0101","BCR0102",
            "BCR0103","BCR0104",
            "BCR0205","BCR0206",
            "BCR0207","BCR0208",
            "LXJZ01",};

    public static final String[] OUT_POINT = {
            "090001000102",
            "090002000102",
            "090003000102",
            "090004000102"
    };

    public static int isContain(String str){
        for (String bcrPoint : PointUtils.BCR_POINTS_IN) {
            if (bcrPoint.equals(str)){
                return 1;
            }
        }
        for (String bcrPoint : PointUtils.BCR_POINTS_OUT) {
            if (bcrPoint.equals(str)){
                return 2;
            }
        }
        for (String bcrPoint : PointUtils.OUT_POINT) {
            if (bcrPoint.equals(str)){
                return 3;
            }
        }
        return 0;
    }
}
