package com.prolog.eis.page.service.impl;

import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.dto.store.ContainerQueryDto;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.page.service.IPageService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.framework.core.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 11:02
 */
@Service
public class PageService implements IPageService {
    @Autowired
    private IStationService stationService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Override
    public List<StationInfoVo> findStation() {
        return stationService.queryAll();
    }

    @Override
    public void updateStationInfo(StationInfoDto stationInfoDto) throws Exception {
        stationService.updateStationTaskType(stationInfoDto);
    }

    @Override
    public StationInfoVo findStationById(int stationId) throws Exception {
        return stationService.queryById(stationId);
    }

    @Override
    public Page<ContainerInfoDto> getContainerPage(ContainerQueryDto containerQueryDto) {
        return containerStoreService.queryContainersPage(containerQueryDto);
    }
}
