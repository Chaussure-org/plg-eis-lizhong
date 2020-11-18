package com.prolog.eis.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.page.LogInfoDto;
import com.prolog.eis.dto.page.LogQueryDto;
import com.prolog.eis.log.dao.*;
import com.prolog.eis.log.service.ILogService;
import com.prolog.eis.model.log.*;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 21:44
 */

@Service
public class LogServiceImpl implements ILogService {

    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private WmsLogMapper wmsLogMapper;

    @Autowired
    private WcsLogMapper wcsLogMapper;

    @Autowired
    private RcsLogMapper rcsLogMapper;

    @Autowired
    private McsLogMapper mcsLogMapper;

    @Autowired
    private SasLogMapper sasLogMapper;

    /**
     * 保存日志
     * @param log 日志
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Exception.class)
    public void save(LogDto log) {
        switch (log.getSystemType()){
            case LogDto.WMS:
                WmsLog wmsLog = new WmsLog();
                BeanUtils.copyProperties(log,wmsLog);
                wmsLogMapper.save(wmsLog);
                break;
            case LogDto.WCS:
                WcsLog wcsLog = new WcsLog();
                BeanUtils.copyProperties(log,wcsLog);
                wcsLogMapper.save(wcsLog);
                break;
            case LogDto.SAS:
                SasLog sasLog = new SasLog();
                BeanUtils.copyProperties(log,sasLog);
                sasLogMapper.save(sasLog);
                break;
            case LogDto.MCS:
                McsLog mcsLog = new McsLog();
                BeanUtils.copyProperties(log,mcsLog);
                mcsLogMapper.save(mcsLog);
                break;
            case LogDto.RCS:
                RcsLog rcsLog = new RcsLog();
                BeanUtils.copyProperties(log,rcsLog);
                rcsLogMapper.save(rcsLog);
                break;
            default:
                logger.error("没有找到正确的日志类型");
        }
    }

    @Override
    public List<LogInfoDto> getLogPage(LogQueryDto logQueryDto,String tableName,String systemType) throws Exception {
         return wmsLogMapper.getLogPage(logQueryDto,tableName,systemType);
    }
}
