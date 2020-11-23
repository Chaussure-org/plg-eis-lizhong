package com.prolog.eis.store.service.impl;

import com.prolog.eis.dto.page.StoreInfoDto;
import com.prolog.eis.dto.page.StoreInfoQueryDto;
import com.prolog.eis.location.dao.SxStoreLocationGroupMapper;
import com.prolog.eis.location.dao.SxStoreLocationMapper;
import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.model.store.SxStoreLocationGroup;
import com.prolog.eis.store.dao.StoreLocationMapper;
import com.prolog.eis.store.service.IStoreLocationService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:00
 */
@Service
public class StoreLocationServiceImpl implements IStoreLocationService {

    @Autowired
    private SxStoreLocationMapper storeLocationMapper;

    @Autowired
    private SxStoreLocationGroupMapper sxStoreLocationGroupMapper;

    @Override
    public void saveBatchStoreLocation(List<SxStoreLocation> sxStoreLocations) {
        storeLocationMapper.saveBatch(sxStoreLocations);
    }

    @Override
    public int countTotal() {
        return (int) storeLocationMapper.findCountByMap(null, SxStoreLocation.class);
    }

    @Override
    public Page<StoreInfoDto> getBoxStorePage(StoreInfoQueryDto storeQueryDto) {
        String areaNo = "SAS01";
        PageUtils.startPage(storeQueryDto.getPageNum(),storeQueryDto.getPageSize());
        List<StoreInfoDto> list = storeLocationMapper.getStoreInfo(storeQueryDto,areaNo);
        Page<StoreInfoDto> page = PageUtils.getPage(list);
        return page;
    }

    @Override
    public void updateGroupLock(String groupNo,int isLock) throws Exception {
        if (isLock != 0 && isLock != 1){
            throw new Exception("锁参数异常");
        }
        List<SxStoreLocationGroup> sxStoreLocationGroups = sxStoreLocationGroupMapper.findByMap(MapUtils.put("groupNo", groupNo).getMap(), SxStoreLocationGroup.class);
        if (sxStoreLocationGroups.size() == 0){
            throw new Exception("【"+groupNo+"】组不存在");
        }
        SxStoreLocationGroup sxStoreLocationGroup = sxStoreLocationGroups.get(0);
        sxStoreLocationGroup.setIsLock(isLock);
        sxStoreLocationGroupMapper.update(sxStoreLocationGroup);
    }

    @Override
    public Page<StoreInfoDto> getTrayStorePage(StoreInfoQueryDto storeQueryDto) {
        String area = "MCS01,MCS02,MCS03,MCS04,MCS05";
        PageUtils.startPage(storeQueryDto.getPageNum(),storeQueryDto.getPageSize());
        List<StoreInfoDto> list = storeLocationMapper.getStoreInfo(storeQueryDto,area);
        Page<StoreInfoDto> page = PageUtils.getPage(list);
        return page;
    }
}
