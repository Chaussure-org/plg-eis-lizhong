package com.prolog.eis.store.service.impl;

import com.prolog.eis.store.service.ITrayStoreReleaseService;
import org.springframework.stereotype.Service;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/3 11:00
 */
@Service
public class TrayStoreReleaseServiceImpl implements ITrayStoreReleaseService {
    /**
     * 接驳口下架
     * 1、agv区域空托盘下架
     * 2、贴标区下架去暂存区
     * 3、暂存区下架
     * @param containerNo
     */
    @Override
    public void storeRelease(String containerNo) {

    }
}
