package com.prolog.eis.aspect;

import java.util.Map;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020/11/12 10:38
 */
public class EisSqlFactory {

    public String getInventoryGoods(Map<String,Object> map) {
        String sql="SELECT\\n\" +\n" +
                "            \"\\tcs.container_no AS containerNo,\\n\" +\n" +
                "            \"\\tg.goods_no AS goodsNo,\\n\" +\n" +
                "            \"\\tg.goods_name AS goodsName,\\n\" +\n" +
                "            \"\\tcs.qty AS originalCount,\\n\" +\n" +
                "            \"\\tg.id as goodsId\\n\" +\n" +
                "            \"FROM\\n\" +\n" +
                "            \"\\tcontainer_store cs\\n\" +\n" +
                "            \"\\tJOIN container_path_task cpt ON cpt.container_no = cs.container_no\\n\" +\n" +
                "            \"\\tJOIN sx_store_location s ON s.store_no = cpt.source_location\\n\" +\n" +
                "            \"\\tJOIN goods g ON g.id = cs.goods_id \\n\" +\n" +
                "            \"WHERE\\n\" +\n" +
                "            \"\\tcpt.task_state = 0 \\n";
        for (String str:map.keySet()){
            sql+=str+map.get(str).toString();
        }
        return sql;
    }
}
