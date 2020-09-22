
package com.prolog.eis.util;

import java.util.Date;

public class TimeUtils {
    /**
     * 判断两个商品有效期是否相同，是则返回true
     * @param date1 可为nul
     * @param date2 可为nul
     * @return
     */
    public static boolean compareTime(Date date1,Date date2){
        if (date1 != null && date2 != null){
            long time = date1.getTime() - date2.getTime();
            if (time == 0){
                return true;
            }else {
                return false;
            }
        }else if (date1 != null && date2 == null){
            return false;
        }else if (date2 != null && date1 == null){
            return false;
        }
        return true;
    }

    /**
     * 判断两个Date 的大小 返回最大的时间
     * @param date1 可为null
     * @param date2 可为nul
     * @return
     */
    public static Date getMinTime(Date date1,Date date2){
        Date minTime = null;
        if (date1 != null && date2 != null){

            minTime = date1.getTime() - date2.getTime() <= 0 ? date1 : date2;
            
        }else if (date1 != null && date2 == null){
            minTime = date1;
        }else if (date2 != null && date1 == null){
            minTime = date2;
        }
        return minTime;
    }


}
