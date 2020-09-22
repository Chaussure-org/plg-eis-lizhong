package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.model.store.SxStoreLocationGroup;
import com.prolog.eis.store.service.IStoreLocationGroupService;
import com.prolog.eis.store.service.IStoreLocationService;
import com.prolog.eis.store.service.IStoreService;
import com.prolog.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:53
 */
@Service
public class StoreServiceImpl implements IStoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    private IStoreLocationService storeLocationService;

    @Autowired
    private IStoreLocationGroupService storeLocationGroupService;

    @Override
    public void initStore(int layerCount, int xCount, int yCount, int ascent, int factor, List<Integer> exList) throws Exception {
        int group = 0;
        boolean flag = true;
        for(int i=1;i<layerCount;i++){
            for(int x =0;x<xCount;x++){
                for(int y=0;y<yCount;y++){
                    if (y%factor==0||!flag){
                        group++;
                    }
                    if(exList.contains(y)){
                        continue;
                    }
                    String layer = StringUtils.padLeft(i+"",2,"0");
                    String xStr = StringUtils.padLeft(x+"",4,"0");
                    String yStr = StringUtils.padLeft(y+"",4,"0");
                    switch (ascent) {
                        case 1:
                            String point1 = String.format("%s%s%s%s",layer,xStr,yStr,"02");
                            String point2 = String.format("%s%s%s%s",layer,xStr,yStr,"03");
                            System.out.println(point1+"++"+group);
                            System.out.println(point2+"++"+group);
                            if (!flag){
                                flag = true;
                            }
                            addLocation(group,i,x,y,point1);
                            addLocation(group,i,x,y,point2);
                            break;
                        default:
                            for (int j = 1; j <=2*ascent ; j++) {
                                String point = String.format("%s%s%s0%s",layer,xStr,yStr,j);
                                System.out.println(point+"++"+group);
                                if (!flag){
                                    flag = true;
                                }
                                addLocation(group,i,x,y,point);
                            }
                    }
                }

            }
            flag = false;
        }
    }

    private void addLocation(int groupNo,int layer,int x,int y,String point){
        SxStoreLocationGroup group = new SxStoreLocationGroup();
        group.setGroupNo(groupNo+"");
        group.setEntrance(1);
        group.setInOutNum(1);
        group.setIsLock(0);
        group.setAscentLockState(0);
        group.setReadyOutLock(0);
        group.setLayer(layer);
        group.setX(x);
        group.setY(y);
        group.setLocationNum(1);
        group.setCreateTime(new Date());
        storeLocationGroupService.saveStoreLocationGroup(group);

        SxStoreLocation location = new SxStoreLocation();
        location.setStoreNo(point);
        location.setStoreLocationGroupId(group.getId());
        location.setLayer(layer);
        location.setX(x);
        location.setY(y);
        location.setAscentLockState(0);
        location.setLocationIndex(1);
        location.setDepth(1);
        location.setDeptNum(1);
        location.setIsInBoundLocation(1);
        location.setCreateTime(new Date());

        storeLocationService.saveStoreLocation(location);
        logger.info("add location "+point);



    }
}
