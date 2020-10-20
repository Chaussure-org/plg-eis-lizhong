package com.prolog.eis.engin.service.impl;

import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.service.IAgvBindingDetailService;
import com.prolog.eis.model.agv.AgvBindingDetail;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/20 11:16
 */
public class AgvBindingDetailServiceImpl implements IAgvBindingDetailService {

    @Autowired
    private AgvBindingDetaileMapper agvBindingDetaileMapper;
    @Override
    public void deleteBindingDetailByMap(Map map) {
        agvBindingDetaileMapper.deleteByMap(map,AgvBindingDetail.class);
    }
}
