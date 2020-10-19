package com.prolog.eis.rcs.service;

import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;

public interface IRCSService {

	/**
	 * 給Rcs发送搬运任务
	 * @param rcsTaskDto rcs任务实体
	 * @return
	 */
	RcsRequestResultDto sendTask(RcsTaskDto rcsTaskDto);
}
