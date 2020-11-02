package com.prolog.eis.wcs.dispatch;

import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.model.wcs.WcsCommandRepeat;
import com.prolog.eis.wcs.service.IWcsCommandRepeatService;
import com.prolog.eis.wcs.service.IWcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangkang
 * @Description 重发命令
 * @CreateTime 2020-11-02 10:13
 */
public class RepeatCommandTask implements CommandLineRunner {
    
    @Autowired
    private IWcsCommandRepeatService wcsCommandRepeatService;

    @Autowired
    private IWcsService wcsService;

    @Override
    public void run(String... args) throws Exception {
        doCommandRepeat();
    }

    private void doCommandRepeat() {
        int threadCount = 3;
        for (int i = 0; i < threadCount; i++) {

        }
    }

    class CmdThread extends Thread{

        @Override
        public void run() {
            while (true) {
                doRepeatTask();
            }
        }
    }

    private void doRepeatTask() {
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


    private void sendWcsCommand(WcsCommandRepeat wcsCommandRepeat) throws Exception {
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(wcsCommandRepeat.getTaskId(),wcsCommandRepeat.getAddress()
                ,wcsCommandRepeat.getTarget(),wcsCommandRepeat.getContainerNo(),wcsCommandRepeat.getType());
        wcsService.lineMove(wcsLineMoveDto,0);
    }
}
