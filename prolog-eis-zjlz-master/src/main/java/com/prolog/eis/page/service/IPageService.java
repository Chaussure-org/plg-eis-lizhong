package com.prolog.eis.page.service;

import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.dto.store.ContainerQueryDto;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.framework.core.pojo.Page;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 11:02
 * page前端页面访问层
 */
public interface IPageService {


    /**
     * 查询所有站台信息
     * @return
     */
    List<StationInfoVo> findStation();

    /**
     * 修改站台信息
     * @param stationInfoDto
     * @throws Exception
     */
    void updateStationInfo(StationInfoDto stationInfoDto) throws Exception;

    /**
     * 根据id查看站台信息
     * @param stationId
     * @throws Exception
     * @return
     */
    StationInfoVo findStationById(int stationId) throws Exception;

    Page<ContainerInfoDto> getContainerPage(ContainerQueryDto containerQueryDto);
}
