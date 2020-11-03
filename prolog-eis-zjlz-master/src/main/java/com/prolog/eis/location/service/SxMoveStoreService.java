package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;

import java.sql.Timestamp;

public interface SxMoveStoreService {

	/**
	 * 四向库内移动方法
	 * @param containerPathTask
	 * @param containerPathTaskDetailDTO
	 * @return
	 */
	void mcsContainerMove(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO);

	/**
	 * 四向库内回告开始方法
	 * @param containerPathTaskDetail
	 * @param time
	 */
	void mcsCallBackStart(ContainerPathTaskDetail containerPathTaskDetail, Timestamp time);

	/**
	 * 四向库内回告开始方法
	 * @param containerPathTaskDetail
	 * @param time
	 */
	void mcsCallBackComplete(ContainerPathTaskDetail containerPathTaskDetail, Timestamp time);

    void updateContainerPathTaskComplete(ContainerPathTask containerPathTask,
                                         ContainerPathTaskDetail containerPathTaskDetail, Timestamp time) throws Exception;

    void unlockCompletekSxStoreLocation(ContainerPathTaskDetail containerPathTaskDetail) throws Exception;
}
