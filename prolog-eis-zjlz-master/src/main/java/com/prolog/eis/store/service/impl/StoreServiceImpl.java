package com.prolog.eis.store.service.impl;

import com.prolog.eis.dto.store.InitStoreDto;
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

import java.util.ArrayList;
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
    public void initStore(InitStoreDto initStoreDto) throws Exception {
        int layerCount = initStoreDto.getLayerCount();
        int xStart = initStoreDto.getxStart();
        int xCount = initStoreDto.getxCount();
        int yCount = initStoreDto.getyCount();
        int ascent = initStoreDto.getAscent();
        List<Integer> exList = initStoreDto.getExList();
        String areaNo = initStoreDto.getAreaNo();
        String group = null;
        int a = 0;
        for(int i=1;i<layerCount;i++){
            for(int x =xStart;x<xCount;x++){
                for(int y=0;y<yCount;y++){
                    if (exList !=null && exList.size() > 0) {
                        if (exList.contains(y)) {
                            continue;
                        }
                    }
                    String layer = StringUtils.padLeft(i+"",2,"0");
                    String xStr = StringUtils.padLeft(x+1+"",4,"0");
                    String yStr = StringUtils.padLeft(y+1+"",4,"0");
                    List<String> pointList1 = new ArrayList<>();
                    List<String> pointList2 = new ArrayList<>();
                    switch (ascent) {
                        case 1:
                            String point1 = String.format("%s%s%s%s",layer,xStr,yStr,"01");
                            String point2 = String.format("%s%s%s%s",layer,xStr,yStr,"02");
                            a++;
                            pointList1.add(point1);
                            pointList2.add(point2);
                            group = String.format("%s%s%s%s",layer,xStr,yStr,"0101");
                            System.out.println(point1+"++"+group+"++"+a);
                            addLocation(group,i,x,y,pointList1,areaNo,ascent);
                            a++;
                            group = String.format("%s%s%s%s",layer,xStr,yStr,"0202");
                            System.out.println(point2+"++"+group+"++"+a);
                            addLocation(group,i,x,y,pointList2,areaNo,ascent);
                            break;
                        default:
                            String str1 = "01";
                            String str2 = StringUtils.padLeft(ascent+"",2,"0");
                            String str3 = StringUtils.padLeft((ascent+1)+"",2,"0");
                            String str4 = StringUtils.padLeft((2*ascent)+"",2,"0");
                            for (int j = 1; j <=2*ascent ; j++) {
                                String point = String.format("%s%s%s0%s",layer,xStr,yStr,j);
                                a++;
                                if (j<=ascent){
                                    pointList1.add(point);
                                }else {
                                    pointList2.add(point);
                                }

                            }
                            group = String.format("%s%s%s%s",layer,xStr,yStr,str1+str2);
                            System.out.println(pointList1.toString()+"++"+group+"++"+a);
                            addLocation(group,i,x,y,pointList1,areaNo,ascent);
                            group = String.format("%s%s%s%s",layer,xStr,yStr,str3+str4);
                            System.out.println(pointList2.toString()+"++"+group+"++"+a);
                            addLocation(group,i,x,y,pointList2,areaNo,ascent);
                    }
                }
            }
        }
    }



    private void addLocation(String groupNo,int layer,int x,int y,List<String> pointList,String areaNo,Integer ascent){

        SxStoreLocationGroup group = new SxStoreLocationGroup();
        group.setGroupNo(groupNo+"");
        group.setEntrance(1);
        group.setInOutNum(1);
        group.setIsLock(0);
        group.setAscentLockState(0);
        group.setReadyOutLock(0);
        group.setLayer(layer);
        group.setX(x+1);
        group.setY(y+1);
        group.setLocationNum(1);
        group.setCreateTime(new Date());
        storeLocationGroupService.saveStoreLocationGroup(group);
        List<SxStoreLocation> listSxStoreLocations = new ArrayList<>();
        for (String point : pointList) {
            SxStoreLocation location = new SxStoreLocation();
            location.setStoreNo(point);
            location.setStoreLocationGroupId(group.getId());
            location.setAreaNo(areaNo);
            location.setLayer(layer);
            location.setX(x+1);
            location.setY(y+1);
            location.setAscentLockState(0);
            int depth = 0;
            int index = Integer.parseInt(point.substring(10,12));
            if (index>ascent){
                depth = (index-1) - ascent;
            }else {
                depth = ascent-index;
            }
            location.setLocationIndex(index);
            location.setDepth(depth);
            location.setDeptNum(0);
            location.setIsInBoundLocation(1);
            location.setCreateTime(new Date());
            listSxStoreLocations.add(location);
            logger.info("add location "+point);
        }
        storeLocationService.saveBatchStoreLocation(listSxStoreLocations);
    }
}
