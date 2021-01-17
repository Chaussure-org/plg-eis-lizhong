package com.prolog.eis.rcs.service;

import com.prolog.eis.dto.rcs.RcsCallbackDto;

public interface IRcsCallbackService {

	/**
	 * rcs回告逻辑
	 * @param rcsCallbackDto
	 * @throws Exception
	 */
	void rcsCallback(RcsCallbackDto rcsCallbackDto) throws Exception;
}
