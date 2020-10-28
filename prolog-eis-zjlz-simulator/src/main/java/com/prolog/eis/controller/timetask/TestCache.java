package com.prolog.eis.controller.timetask;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.enums.PointChangeEnum;
import com.prolog.eis.service.McsService;
import com.prolog.eis.service.RcsService;
import com.prolog.eis.service.SasService;
import com.prolog.eis.service.WcsService;
import com.prolog.eis.utils.CacheListUtils;
import com.prolog.eis.utils.PointUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 11:04
 */
@Component
public class TestCache {

    @Autowired
    private McsService mcsService;

    @Autowired
    private SasService sasService;

    @Autowired
    private WcsService wcsService;

    @Autowired
    private RcsService rcsService;

    @Scheduled(initialDelay = 3000,fixedDelay = 3000)
    @Async
    public void testMcs(){
        if (CacheListUtils.getMcslist().size()>0) {
            pWait();
            System.out.println(CacheListUtils.getMcslist().get(0));
            McsMoveTaskDto mcsMoveTaskDto = CacheListUtils.getMcslist().get(0);
            mcsService.doCallBack(mcsMoveTaskDto);
            if (PointUtils.isContain(mcsMoveTaskDto.getTarget())==3){
                WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto();
                wcsLineMoveDto.setAddress(PointChangeEnum.getPoint(mcsMoveTaskDto.getTarget()));
                wcsLineMoveDto.setTaskId(null);
                wcsLineMoveDto.setContainerNo(mcsMoveTaskDto.getContainerNo());
                wcsLineMoveDto.setType(1);
                if (PointUtils.isContain(wcsLineMoveDto.getTarget())==1){
                    wcsService.doBcrRequest(wcsLineMoveDto,1);
                }else if (PointUtils.isContain(wcsLineMoveDto.getTarget())==2) {
                    wcsService.doBcrRequest(wcsLineMoveDto,2);
                }else {
                    wcsService.doBcrRequest(wcsLineMoveDto,2);
                }
            }
            CacheListUtils.getMcslist().remove(CacheListUtils.getMcslist().get(0));
        }
    }

    @Scheduled(initialDelay = 3000,fixedDelay = 3000)
    @Async
    public void testSas(){
        if (CacheListUtils.getSaslist().size()>0) {
            pWait();
            System.out.println(CacheListUtils.getSaslist().get(0));
            SasMoveTaskDto sasMoveTaskDto = CacheListUtils.getSaslist().get(0);
            sasService.doCallBack(sasMoveTaskDto);
            CacheListUtils.getSaslist().remove(CacheListUtils.getSaslist().get(0));
        }
    }

    @Scheduled(initialDelay = 3000,fixedDelay = 3000)
    @Async
    public void testWcs(){
        if (CacheListUtils.getWcslist().size()>0) {
            pWait();
            System.out.println(CacheListUtils.getWcslist().get(0));
            WcsLineMoveDto wcsLineMoveDto = CacheListUtils.getWcslist().get(0);
            wcsService.doCallBack(wcsLineMoveDto);
            if (PointUtils.isContain(wcsLineMoveDto.getTarget())==1){
                wcsService.doBcrRequest(wcsLineMoveDto,1);
            }else if (PointUtils.isContain(wcsLineMoveDto.getTarget())==2) {
                wcsService.doBcrRequest(wcsLineMoveDto,2);
            }
            CacheListUtils.getWcslist().remove(CacheListUtils.getWcslist().get(0));
        }
    }

    @Scheduled(initialDelay = 3000,fixedDelay = 3000)
    @Async
    public void testRcs() {
        if (CacheListUtils.getRcslist().size()>0) {
            pWait();
            System.out.println(CacheListUtils.getRcslist().get(0));
            RcsTaskDto rcsTaskDto = CacheListUtils.getRcslist().get(0);
            rcsService.doCallBack(rcsTaskDto);
            CacheListUtils.getRcslist().remove(CacheListUtils.getRcslist().get(0));
        }
    }

    private void pWait(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
