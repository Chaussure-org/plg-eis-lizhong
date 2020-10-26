package com.prolog.eis.location.service.impl;

import com.prolog.eis.location.dao.PointLocationMapper;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.model.PointLocation;
import com.prolog.framework.core.restriction.Criteria;
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

    /**
     * 通过id找点位
     * @param address 原位子
     * @return
     */
    @Override
    public PointLocation getPointByPointId(String address) {
        Criteria criteria = Criteria.forClass(PointLocation.class);
        criteria.setRestriction(Restrictions.eq("pointId",address));
        List<PointLocation> pointLocations = mapper.findByCriteria(criteria);
        if (pointLocations.size()>0) {
            return pointLocations.get(0);
        }
        return null;
    }

    /**
     * 根据站台号获取点位
     * @param stationId 站台号
     * @return
     */
    @Override
    public PointLocation getPointByStationId(Integer stationId) {
        Criteria criteria = Criteria.forClass(PointLocation.class);
        criteria.setRestriction(Restrictions.eq("stationId",stationId));
        List<PointLocation> points = mapper.findByCriteria(criteria);
        if (points.size()>0){
            return points.get(0);
        }
        return null;
    }
}
