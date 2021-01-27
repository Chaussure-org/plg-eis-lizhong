package com.prolog.eis.warehousing.service.impl;

import com.github.pagehelper.PageHelper;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.page.InboundQueryDto;
import com.prolog.eis.dto.page.WmsInboundInfoDto;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.model.wms.WmsInboundTaskHistory;
import com.prolog.eis.util.EisStringUtils;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.warehousing.dao.WareHousingMapper;
import com.prolog.eis.warehousing.dao.WmsInboundTaskHistoryMapper;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-19 10:09
 */
@Service
public class WareHousingServiceImpl implements IWareHousingService {

    @Autowired
    private WareHousingMapper mapper;
    @Autowired
    private IWmsService wmsService;
    @Autowired
    private WmsInboundTaskHistoryMapper wmsInboundTaskHistoryMapper;

    /**
     * 通过容器号找wms下发的入库任务
     *
     * @param containerNo 容器号
     * @return
     */
    @Override
    public List<WmsInboundTask> getWareHousingByContainer(String containerNo) {
        Criteria criteria = Criteria.forClass(WmsInboundTask.class);
        criteria.setRestriction(Restrictions.eq("containerNo", containerNo));
        return mapper.findByCriteria(criteria);
    }

    @Override
    public void deleteInboundTask(String containerNo) throws Exception {
        //删除入库任务前回告wms
        inboundReportWms(containerNo);
        mapper.deleteByMap(MapUtils.put("containerNo", containerNo).getMap(), WmsInboundTask.class);
    }

    @Override
    public Page<WmsInboundInfoDto> getInboundPage(InboundQueryDto inboundQueryDto) {
        PageUtils.startPage(inboundQueryDto.getPageNum(), inboundQueryDto.getPageSize());
        List<WmsInboundInfoDto> wmsInboudns = mapper.getInboundInfo(inboundQueryDto);
        Page<WmsInboundInfoDto> page = PageUtils.getPage(wmsInboudns);
        return page;
    }

    @Override
    public void inboundReportWms(String containerNo) throws Exception {
        List<WmsInboundTask> inboundTasks = mapper.findByMap(MapUtils.put("containerNo", containerNo).getMap(), WmsInboundTask.class);
        if (inboundTasks.size() == 0) {
            return;
        }
        WmsInboundTask wmsInboundTask = inboundTasks.get(0);
        WmsInboundCallBackDto wmsInboundCallBackDto = new WmsInboundCallBackDto();
        wmsInboundCallBackDto.setLINEID(wmsInboundTask.getLineId());
        wmsInboundCallBackDto.setBILLNO(wmsInboundTask.getBillNo());
        wmsInboundCallBackDto.setBILLTYPE(wmsInboundTask.getBillType());
        wmsInboundCallBackDto.setCONTAINERNO(wmsInboundTask.getContainerNo());
        wmsInboundCallBackDto.setITEMID(EisStringUtils.getRemouldId(wmsInboundTask.getGoodsId()));
        wmsInboundCallBackDto.setITEMNAME(wmsInboundTask.getGoodsName());
        wmsInboundCallBackDto.setSEQNO(wmsInboundTask.getSeqNo());
        wmsService.inboundTaskCallBack(wmsInboundCallBackDto);
    }

    @Override
    public List<WmsInboundTask> findInboundByMap(Map map) {
        return mapper.findByMap(map,WmsInboundTask.class);
    }

    @Override
    public void inboundToHistory(WmsInboundTask wmsInboundTask) {
        WmsInboundTaskHistory wmsInboundTaskHistory = new WmsInboundTaskHistory();
        BeanUtils.copyProperties(wmsInboundTask,wmsInboundTaskHistory);
        wmsInboundTaskHistory.setCompleteTime(new Date());
        wmsInboundTaskHistory.setCallState(WmsInboundTask.CALL_STATE_FINISH);
        wmsInboundTaskHistoryMapper.save(wmsInboundTaskHistory);
        mapper.deleteById(wmsInboundTask.getId(),WmsInboundTask.class);
    }

    @Override
    public void updateInboundCallState(String containerNo,int callState) {
        Criteria cri = Criteria.forClass(WmsInboundTask.class);
        cri.setRestriction(Restrictions.eq("containerNo",containerNo));
        mapper.updateMapByCriteria(MapUtils.put("callState",callState).getMap(),cri);
    }
}
