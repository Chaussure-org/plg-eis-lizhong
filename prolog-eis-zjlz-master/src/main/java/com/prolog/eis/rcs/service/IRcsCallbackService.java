package com.prolog.eis.rcs.service;

public interface IRcsCallbackService {

	/**
	 * rcs回告逻辑
	 * @param taskCode
	 * @param method
	 * @throws Exception
	 */
	void rcsCallback(String taskCode, String method) throws Exception;
}
