package com.prolog.eis.util;

/**
 * Description:比较两个字符串相似度的算法
 * 由俄罗斯的科学家  1965 年提出
 *
 * @date:2020/10/9 15:43
 * @author:SunPP
 */
public class CompareStrSimUtil {
    private static int compare(StringBuffer str, StringBuffer target, boolean isIgnore) {
        String[] s1 = str.toString().split("@");
        String[] s2 = target.toString().split("@");

        int d[][]; // 矩阵

        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的

        // str的
        String sch1;

         // target的
        String sch2;

        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }


        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (i = 1; i <= s1.length; i++) {
            sch1 = s1[i-1];
            //去匹配target
            for (j=1;j<=s2.length;j++){
                sch2=s2[j-1];
                //goodsId 不考虑大小写
                if (sch1.equals(sch2)){
                    temp=0;
                }else {
                    temp=1;
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

    public static float getSimilarityRatio(StringBuffer str, StringBuffer target, boolean isIgnore) {
        float ret = 0;
        if (Math.max(str.length(), target.length()) == 0) {
            ret = 1;
        } else {
            ret = 1 - (float) compare(str, target, isIgnore) / Math.max(str.length(), target.length());
        }
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
