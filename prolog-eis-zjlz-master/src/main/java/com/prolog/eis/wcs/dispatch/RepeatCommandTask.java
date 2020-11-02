package com.prolog.eis.wcs.dispatch;

import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.model.wcs.WcsCommandRepeat;
import com.prolog.eis.wcs.service.IWcsCommandRepeatService;
import com.prolog.eis.wcs.service.IWcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangkang
 * @Description 重发命令
 * @CreateTime 2020-11-02 10:13
 */
@Component
public class RepeatCommandTask implements CommandLineRunner {
    
    @Autowired
    private IWcsCommandRepeatService wcsCommandRepeatService;

    @Autowired
    private IWcsService wcsService;

    /**
     * 开启则执行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        doCommandRepeat();
    }

    /**
     * 创建线程
     */
    private void doCommandRepeat() {
        int threadCount = 3;
        for (int i = 0; i < threadCount; i++) {
            CmdThread cmdThread = new CmdThread();
            cmdThread.setName("cmd-repeat-send-"+i);
            cmdThread.start();
        }
    }

    /**
     * 线程类
     */
    class CmdThread extends Thread{

        @Override
        public void run() {
            while (true) {
                doRepeatTask();
                try{
                    Thread.sleep(2000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 重发任务
     */
    private synchronized void doRepeatTask() {
        List<WcsCommandRepeat> allCommandByCreatTime = wcsCommandRepeatService.findAllCommandByCreatTime();
        if (allCommandByCreatTime==null || allCommandByCreatTime.size() == 0) {
            return;
        }
        for (WcsCommandRepeat wcsCommandRepeat : allCommandByCreatTime) {
            try {
                sendWcsCommand(wcsCommandRepeat);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除并发送指令
     * @param wcsCommandRepeat 指令
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendWcsCommand(WcsCommandRepeat wcsCommandRepeat) throws Exception {
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(wcsCommandRepeat.getTaskId(),wcsCommandRepeat.getAddress()
                ,wcsCommandRepeat.getTarget(),wcsCommandRepeat.getContainerNo(),wcsCommandRepeat.getType());
        wcsService.lineMove(wcsLineMoveDto, 1);
        wcsCommandRepeatService.deleteCommandByTaskId(wcsCommandRepeat.getTaskId());
    }
}
