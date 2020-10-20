package com.prolog.eis.location.service.impl;

import com.prolog.eis.location.dao.PointLocationMapper;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.model.PointLocation;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restriction;
import com.prolog.framework.core.restriction.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-19 9:57
 */
@Service
public class IPointLocationServiceImpl implements IPointLocationService {

    @Autowired
    private PointLocationMapper mapper;

    /**
     * 通过类型找点位
     * @param type 类型
     * @return
     */
    @Override
    public List<PointLocation> getPointByType(int type) {
        Criteria criteria = Criteria.forClass(PointLocation.class);
        criteria.setRestriction(Restrictions.eq("pointType",type));
        return mapper.findByCriteria(criteria);
    }
}
