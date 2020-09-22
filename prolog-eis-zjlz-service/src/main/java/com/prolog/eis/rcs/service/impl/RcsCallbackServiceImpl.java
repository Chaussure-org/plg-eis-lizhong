package com.prolog.eis.rcs.service.impl;

import com.prolog.eis.rcs.service.RcsCallbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RcsCallbackServiceImpl implements RcsCallbackService {

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void rcsCallback(String taskCode, String method) throws Exception {

	}


}
