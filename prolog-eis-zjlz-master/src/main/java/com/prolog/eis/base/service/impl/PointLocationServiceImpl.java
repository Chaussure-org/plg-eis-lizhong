package com.prolog.eis.base.service.impl;

import com.prolog.eis.base.dao.PointLocationMapper;
import com.prolog.eis.base.service.IPointLocationService;
import com.prolog.eis.model.PointLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/19 15:34
 */
@Service
public class PointLocationServiceImpl implements IPointLocationService {
    @Autowired
    private PointLocationMapper pointLocationMapper;
    @Override
    public List<PointLocation> findByMap(Map map) {
        return pointLocationMapper.findByMap(map,PointLocation.class);
    }
}
