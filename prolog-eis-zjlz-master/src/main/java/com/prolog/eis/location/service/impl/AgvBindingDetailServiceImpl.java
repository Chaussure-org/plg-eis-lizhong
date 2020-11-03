package com.prolog.eis.location.service.impl;

import com.prolog.eis.location.dao.AgvBindingDetaileMapper;
import com.prolog.eis.location.service.IAgvBindingDetailService;
import com.prolog.eis.model.agv.AgvBindingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/20 11:16
 */
@Service
public class AgvBindingDetailServiceImpl implements IAgvBindingDetailService {

    @Autowired
    private AgvBindingDetaileMapper agvBindingDetaileMapper;
    @Override
    public void deleteBindingDetailByMap(Map map) {
        agvBindingDetaileMapper.deleteByMap(map,AgvBindingDetail.class);
    }
}
