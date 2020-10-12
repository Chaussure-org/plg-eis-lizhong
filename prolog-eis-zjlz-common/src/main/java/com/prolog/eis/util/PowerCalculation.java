package com.prolog.eis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenbo
 * @date 2020/9/27 11:35
 */
public class PowerCalculation {

    public static List<String> minPower(Map<String,Integer> mapCount,Map<String,Integer> mapPower){

        List<String> result = new ArrayList<>();
        double temValue = Double.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : mapPower.entrySet()) {
            if(mapCount.containsKey(entry.getKey())){
                Integer count = mapCount.get(entry.getKey());
                Integer power = entry.getValue();
                double value = (double)count/(double)power;
                if(value < temValue){
                    temValue = value;
                    result.clear();
                    result.add(entry.getKey());
                }else if(value == temValue){
                    result.add(entry.getKey());
                }
            }else{
                double value = 0D;
                if(value < temValue){
                    temValue = value;
                    result.clear();
                    result.add(entry.getKey());
                }else if(value == temValue){
                    result.add(entry.getKey());
                }
            }
        }

        return result;
    }
}
