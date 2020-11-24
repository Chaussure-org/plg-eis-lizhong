package com.prolog.eis.page.service.impl;

import com.prolog.eis.page.service.IWmsPageService;
import com.prolog.eis.store.service.ITrayStoreReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public void emptyTrayPutaway(String containerNo, String feederNo) throws Exception {
        trayStoreReleaseService.emptyTrayPull(containerNo,feederNo);
    }

    @Override
    public void storeRelease(String containerNo, String feederNo) throws Exception {
        trayStoreReleaseService.storeRelease(containerNo,feederNo);
    }
}
