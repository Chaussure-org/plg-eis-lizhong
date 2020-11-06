package com.prolog.eis.store.service.impl;

import com.prolog.eis.store.service.ITrayStoreReleaseService;
import com.prolog.framework.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void storeRelease(String containerNo,String feederNo) throws Exception {
        if (StringUtils.isBlank(containerNo) || StringUtils.isBlank(feederNo)){
            throw new Exception("参数不能为空");

        }
        
    }
}
