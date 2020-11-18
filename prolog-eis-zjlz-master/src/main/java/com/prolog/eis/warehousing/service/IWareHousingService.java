package com.prolog.eis.warehousing.service;

import com.prolog.eis.dto.page.InboundQueryDto;
import com.prolog.eis.dto.page.WmsInboundInfoDto;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.framework.core.pojo.Page;

import java.util.List;

/**
 * @Author wangkang
 * @Description 入库任务处理
 * @CreateTime 2020-10-19 10:07
 */
public interface IWareHousingService {

    /**
     * 通过容器号找wms下发的入库任务
     *
     * @param containerNo 容器号
     * @return
     */
    List<WmsInboundTask> getWareHousingByContainer(String containerNo);

    void deleteInboundTask(String containerNo) throws Exception;

    /**
     * 分页查入库任务
     * @param inboundQueryDto
     * @return
     */
    Page<WmsInboundInfoDto> getInboundPage(InboundQueryDto inboundQueryDto);
}
