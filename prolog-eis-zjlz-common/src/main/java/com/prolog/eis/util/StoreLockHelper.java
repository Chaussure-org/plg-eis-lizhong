package com.prolog.eis.util;

import java.util.HashMap;
import java.util.Map;

public class StoreLockHelper {

	private Map<Integer, Object> LockMap;

	private static StoreLockHelper instance;
	public StoreLockHelper() {
		this.LockMap = new HashMap<Integer, Object>();
	}

	public static synchronized StoreLockHelper getInstance() {
		if (instance == null) {
			instance = new StoreLockHelper();
		}
		return instance;
	}
	public synchronized Object GetXKKunCunLockObj(int layer) {
		if (this.LockMap.containsKey(layer)) {
            return this.LockMap.get(layer);
        } else {
			Object lockObj = new Object();
			this.LockMap.put(layer, lockObj);
			return lockObj;
		}
	}
	
}
