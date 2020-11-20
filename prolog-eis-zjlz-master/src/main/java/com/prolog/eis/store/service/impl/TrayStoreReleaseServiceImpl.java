package com.prolog.eis.store.service.impl;

import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.store.service.ITrayStoreReleaseService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/3 11:00
 */
@Service
public class TrayStoreReleaseServiceImpl implements ITrayStoreReleaseService {
    @Autowired
    private ContainerPathTaskService pathTaskService;
    @Autowired
    private PathSchedulingService pathSchedulingService;

    /**
     * 接驳口下架
     * 1、agv区域空托盘下架
     * 2、贴标区下架去暂存区
     * 3、暂存区下架
     *
     * @param containerNo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storeRelease(String containerNo, String feederNo) throws Exception {
        if (StringUtils.isBlank(containerNo) || StringUtils.isBlank(feederNo)) {
            throw new Exception("参数不能为空");

        }
        List<ContainerPathTask> containerPathTasks = pathTaskService.findByMap(MapUtils.put("containerNo", containerNo)
                .put("targetLocation", feederNo).getMap());

        if (containerPathTasks.size() == 0) {
            throw new Exception("容器【" + containerNo + "】不在接驳口【" + feederNo + "】或正在离开");
        }
        ContainerPathTask containerPathTask = containerPathTasks.get(0);
        if (containerPathTask.getTaskState() != 0) {
            throw new Exception("容器【" + containerNo + "】未到位");
        }
        switch (containerPathTask.getSourceArea()) {
            //暂存区
            case StoreArea.CH01:
                //释放
                break;
            //贴标区
            case StoreArea.LB01:
                break;
            //空托区
            case StoreArea.RCS02:
                break;

            default:
                throw new Exception("区域类型有误");
        }


    }
}
