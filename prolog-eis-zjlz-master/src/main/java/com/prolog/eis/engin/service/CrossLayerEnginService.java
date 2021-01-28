package com.prolog.eis.engin.service;

import com.prolog.eis.model.location.ContainerPathTaskDetail;

import java.util.List;
import java.util.Map;

/**
 * @Author SunPP
 * Description:三十功名尘与土，八千里路云和月
 * @return
 * @date:2020/10/26 15:14
 */
public interface CrossLayerEnginService {
    /**
     * 小车跨层任务
     */
    void findCrossLayerTask() throws Exception;

    /**
     * 发送小车跨层任务
     */
    void sendCrossLayerTask(int sourceLayer,int targetLayer,String rgvId) throws Exception;
    /**
     * save小车跨层任务
     */
    void saveCrossLayerTask(String id,int sourceLayer, int targetLayer, String rgvId) throws Exception;
}
