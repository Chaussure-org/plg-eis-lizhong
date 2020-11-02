package com.prolog.eis.wcs.service.impl;

import com.prolog.eis.model.wcs.WcsCommandRepeat;
import com.prolog.eis.wcs.dao.WcsCommandRepeatMapper;
import com.prolog.eis.wcs.service.IWcsCommandRepeatService;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Order;
import com.prolog.framework.core.restriction.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-11-02 9:50
 */
@Service
public class WcsCommandRepeatServiceImpl implements IWcsCommandRepeatService {

    @Autowired
    private WcsCommandRepeatMapper wcsCommandRepeatMapper;

    /**
     * 保存需要重发的指令
     * @param wcsCommandRepeat 重发指令
     */
    @Override
    public void saveWcsCommand(WcsCommandRepeat wcsCommandRepeat) {
        wcsCommandRepeatMapper.save(wcsCommandRepeat);
    }

    /**
     * 找到所有需要重发的命令
     * @return
     */
    @Override
    public List<WcsCommandRepeat> findAllCommandByCreatTime() {
        Criteria criteria = Criteria.forClass(WcsCommandRepeat.class);
        criteria.setOrder(Order.newInstance().asc("createTime"));
        return wcsCommandRepeatMapper.findByCriteria(criteria);
    }

    /**
     * 通过任务id删除命令
     * @param taskId 任务id
     */
    @Override
    public void deleteCommandByTaskId(String taskId) {
        Criteria criteria = Criteria.forClass(WcsCommandRepeat.class);
        criteria.setRestriction(Restrictions.eq("taskId",taskId));
        wcsCommandRepeatMapper.deleteByCriteria(criteria);
    }
}
