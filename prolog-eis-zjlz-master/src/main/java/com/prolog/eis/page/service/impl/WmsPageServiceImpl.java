package com.prolog.eis.page.service.impl;

import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;
import com.prolog.eis.dto.inventory.InventoryShowDto;
import com.prolog.eis.inventory.service.IInventoryJobService;
import com.prolog.eis.page.service.IWmsPageService;
import com.prolog.eis.pick.service.IStationFinishSeedService;
import com.prolog.eis.store.service.ITrayStoreReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/19 18:10
 *
 */
@Service
public class WmsPageServiceImpl implements IWmsPageService {
    @Autowired
    private ITrayStoreReleaseService trayStoreReleaseService;
    @Autowired
    private IInventoryJobService iInventoryJobService;
    @Autowired
    private IStationFinishSeedService finishSeedService;
    @Override
    public void emptyTrayPutaway(String containerNo, String feederNo) throws Exception {
        trayStoreReleaseService.emptyTrayPull(containerNo,feederNo);
    }

    @Override
    public void storeRelease(String containerNo, String feederNo) throws Exception {
        trayStoreReleaseService.storeRelease(containerNo,feederNo);
    }

    @Override
    public InventoryShowDto findInventoryDetail(String containerNo) throws Exception {
        return iInventoryJobService.findInventoryDetail(containerNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doInventoryTask(String containerNo, int qty) throws Exception {
        iInventoryJobService.doInventoryTask(containerNo,qty);
    }

    @Override
    public FinishNotSeedDTO getNotSeedCount() throws Exception {
        return finishSeedService.getNotSeedCount();
    }

    @Override
    public void changeStationIsLock(int isLock) throws Exception {
          finishSeedService.changeStationIsLock(isLock);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmSeed(String containerNo, int num) throws Exception {
           finishSeedService.confirmSeed(containerNo,num);
    }

    @Override
    public FinishTrayDTO getFinishSeed(String containerNo) throws Exception {
        return finishSeedService.getFinishSeed(containerNo);
    }
}
