package com.prolog.eis.engin.dispatch;

import com.prolog.eis.engin.service.AgvLineOutEnginService;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName:OutDispatch
 * Package:com.prolog.eis.engin.dispatch
 * Description:
 *
 * @date:2020/10/14 11:10
 * @author:SunPP
 */
@Component
public class OutDispatch {

    @Autowired
    private TrayOutEnginService trayOutEnginService;

    @Autowired
    private BoxOutEnginService boxOutEnginService;

    @Autowired
    private AgvLineOutEnginService agvLineOutEnginService;

    @Autowired
    private FinishedProdOutEnginService finishedProdOutEnginService;

    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void trayOutDispatch() {
        try {
           trayOutEnginService.initOrder();
           trayOutEnginService.trayOutByOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void finishProdOutDispatch() {
        try {
            finishedProdOutEnginService.finishProdOutByOrder();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void boxOutDispatch() {
        try {
            //boxOutEnginService.BoxOutByOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void lineAndAgvOutDispatch() {
        try {
            //agvLineOutEnginService.computerPickOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
