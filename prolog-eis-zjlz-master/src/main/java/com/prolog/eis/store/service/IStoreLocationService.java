package com.prolog.eis.store.service;

import com.prolog.eis.dto.page.StoreInfoDto;
import com.prolog.eis.dto.page.StoreInfoQueryDto;
import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.framework.core.pojo.Page;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:59
 */
public interface IStoreLocationService {

    void saveBatchStoreLocation(List<SxStoreLocation> sxStoreLocations);

    int countTotal();

    /**
     * 箱库货位分页程序
     * @param storeQueryDto
     * @return
     */
    Page<StoreInfoDto> getBoxStorePage(StoreInfoQueryDto storeQueryDto);

    /**
     * 解锁货位组
     * @param groupNo
     */
    void updateGroupLock(String groupNo,int isLock) throws Exception;

    /**
     * 立库货位分页
     * @param storeQueryDto
     * @return
     */
    Page<StoreInfoDto> getTrayStorePage(StoreInfoQueryDto storeQueryDto);
}
