package com.prolog.eis.location.service.impl;

import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.service.IContainerPathTaskDetailService;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-21 18:34
 */
@Service
public class ContainerPathTaskDetailServiceImpl implements IContainerPathTaskDetailService {

    @Autowired
    private ContainerPathTaskDetailMapper mapper;

    @Override
    public List<ContainerPathTaskDetail> getTaskDetailByTaskId(String taskId) {
        return mapper.findByMap(MapUtils.put("taskId", taskId).getMap(), ContainerPathTaskDetail.class);
    }

    /**
     * 更新路径任务详情
     * @param containerPathTaskDetail 路径任务详情
     */
    @Override
    public void updateTaskDetail(ContainerPathTaskDetail containerPathTaskDetail) {
        mapper.update(containerPathTaskDetail);
    }

    @Override
    public void savePathDetail(ContainerPathTaskDetail containerPathTaskDetail) {
        mapper.save(containerPathTaskDetail);
    }
}
