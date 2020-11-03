package com.prolog.eis.wcs.service;

import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.model.wcs.WcsCommandRepeat;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @Author wangkang
 * @Description wcs指令重发服务
 * @CreateTime 2020-11-02 9:49
 */
public interface IWcsCommandRepeatService {

    /**
     * 保存需重发的指令
     * @param wcsCommandRepeat 重发指令
     */
    void saveWcsCommand(WcsCommandRepeat wcsCommandRepeat);

    /**
     * 根据时间找到所有需命令
     * @return
     */
    List<WcsCommandRepeat> findAllCommandByCreatTime();

    /**
     * 通过taskId删除命令
     * @param taskId 任务id
     */
    void deleteCommandByTaskId(String taskId);

    /**
     * 删除并发送指令
     * @param wcsCommandRepeat 指令
     * @throws Exception
     */
    void sendWcsCommand(WcsCommandRepeat wcsCommandRepeat) throws Exception;
}
