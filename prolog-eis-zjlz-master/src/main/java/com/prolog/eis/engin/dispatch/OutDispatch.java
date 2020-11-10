package com.prolog.eis.engin.dispatch;

import com.prolog.eis.engin.service.*;
import com.prolog.eis.model.wcs.CrossLayerTask;
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
    private CrossLayerEnginService crossLayerEnginService;
    @Autowired
    private IInventoryBoxOutService inventoryBoxOutService;
    @Autowired

    @Autowired
    private AgvInBoundEnginService agvInBoundEnginService;
    /**
     * 托盘库 出库
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 4000)
    public void trayOutDispatch() {
        try {
           trayOutEnginService.initOrder();
           trayOutEnginService.trayOutByOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 成品库出库
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void finishProdOutDispatch() {
        try {
            //finishedProdOutEnginService.finishProdOutByOrder();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 箱库出库
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void boxOutDispatch() {
        try {
            boxOutEnginService.BoxOutByOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 站台调度
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void lineAndAgvOutDispatch() {
        try {
            agvLineOutEnginService.computerPickOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 小车跨层调度
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 30000)
    public void crossLayer(){
        try{
            crossLayerEnginService.findCrossLayerTask();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * agv区域 定时回库
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 4000)
    public void rcsInBound(){
        try{
            agvInBoundEnginService.AgvInBound();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *箱库盘点出库调度
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 6000)
    public void inventoryBoxOut(){
        try {
            System.out.println("盘点调度");
            inventoryBoxOutService.inventoryBoxOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
