package com.prolog.eis.location.dispatch;

import com.prolog.eis.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: wuxl
 * @create: 2020-09-23 17:24
 * @Version: V1.0
 */
@Component
public class LocationTimeTask {

    @Autowired
    private LocationService locationService;


    /**
     * 生成并执行路径任务
     * @throws Exception
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 15000)
    public void doContainerPathTask() throws Exception {
        locationService.doContainerPathTaskAndExecutionByContainer(null, null);
    }

}

