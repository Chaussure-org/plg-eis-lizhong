package com.prolog.eis.wcs.service.impl;

import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.wcs.dao.OpenDiskMapper;
import com.prolog.eis.wcs.service.IOpenDiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/18 14:21
 */

@Service
public class OpenDiskServiceImpl implements IOpenDiskService {
    @Autowired
    private OpenDiskMapper openDiskMapper;
    @Override
    public List<OpenDisk> findOpenDiskByMap(Map map) {
       return openDiskMapper.findByMap(map,OpenDisk.class);
    }

    @Override
    public void updateOpenDisk(OpenDisk openDisk) {
        openDiskMapper.update(openDisk);
    }
}
