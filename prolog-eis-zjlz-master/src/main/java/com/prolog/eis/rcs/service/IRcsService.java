package com.prolog.eis.rcs.service;

import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;

/**
* @Author  wangkang
* @Description  rcs服务
* @CreateTime  2020-11-02 9:12
*/
public interface IRcsService {

	/**
	 * 給Rcs发送搬运任务
	 * @param rcsTaskDto rcs任务实体
	 * @return
	 */
	RcsRequestResultDto sendTask(RcsTaskDto rcsTaskDto);
}
