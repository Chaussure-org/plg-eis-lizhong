package com.prolog.eis.util;

/**
 * Description:可编辑距离算法
 * 由俄罗斯的科学家  1965 年提出
 * @date:2020/10/9 15:43
 * @author:SunPP
 */
public class CompareStrSimUtil {
    private static int compare(int[] str, int[] target, boolean isIgnore) {

        int[] s1 = str;
        int[] s2 = target;

        int d[][]; // 矩阵

        int n = s1.length;
        int m = s2.length;
        int i; // 遍历str的
        int j; // 遍历target的

        // str的
        int sch1;

        // target的
        int sch2;

        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1

        d = new int[n + 1][m + 1];
        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) {
            sch1 = s1[i - 1];
            //去匹配target
            for (j = 1; j <= m; j++) {
                sch2 = s2[j - 1];
                //goodsId 不考虑大小写
                // if (isIgnore) {
                //   if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) { }
                if (sch1==sch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        // 遍历str

        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * @param isIgnore
     * @return s1 与 s2 的重合度
     */
    public static float getSimilarityRatio(int[] s1, int[] s2, boolean isIgnore) {
        float ret = 0;
        ret = compare(s1, s2, isIgnore);
        return ret;
    }

/*    char ch1;
    char ch2;
    // 遍历str
        for (i = 1; i <= n; i++) {
        ch1 = str.charAt(i - 1);
        // 去匹配target
        for (j = 1; j <= m; j++) {
            ch2 = target.charAt(j - 1);
            if (isIgnore) {
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
            } else {
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
            }

            // 左边+1,上边+1, 左上角+temp取最小
            d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
        }
    }*/

}
