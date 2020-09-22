package com.prolog.eis.util;

import java.util.HashMap;
import java.util.Map;

public class RegionOrderLockHelper {
    //private Map<String, Object> LockMap;
    private Map<String, Object> WareHoseOwnerLockMap;

    private static RegionOrderLockHelper instance;

    public RegionOrderLockHelper() {
        //this.LockMap = new HashMap<>();
        this.WareHoseOwnerLockMap = new HashMap<>();
    }

    public static synchronized RegionOrderLockHelper getInstance() {
        if (instance == null) {
            instance = new RegionOrderLockHelper();
        }
        return instance;
    }

//    public synchronized Object getRegionOrderLockObj(String regionNo) {
//        if (this.LockMap.containsKey(regionNo))
//            return this.LockMap.get(regionNo);
//        else {
//            Object lockObj = new Object();
//            this.LockMap.put(regionNo, lockObj);
//            return lockObj;
//        }
//    }

    public synchronized Object getWareHoseOwnerLockObj(Long wareHouseId, Integer ownerId) {
        String key = wareHouseId + "@@" + ownerId;
        if (this.WareHoseOwnerLockMap.containsKey(key)) {
            return this.WareHoseOwnerLockMap.get(key);
        } else {
            Object lockObj = new Object();
            this.WareHoseOwnerLockMap.put(key, lockObj);
            return lockObj;
        }
    }
}
