package com.prolog.eis.wcs.service;

import com.prolog.eis.model.wcs.OpenDisk;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/18 14:19
 * 拆盘机
 */
public interface IOpenDiskService {

    /**
     * 根据map查看
     * @param map
     * @return
     */
    List<OpenDisk> findOpenDiskByMap(Map map);

    /**
     * 修改
     */
    void updateOpenDisk(OpenDisk openDisk);
}
