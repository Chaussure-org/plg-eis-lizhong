package com.prolog.eis.util;

import java.util.HashMap;
import java.util.Map;

public class PrologContainerLockHelper {

	private Map<String, Object> LockMap;

	private static PrologContainerLockHelper instance;
	public PrologContainerLockHelper() {
		this.LockMap = new HashMap<String, Object>();
	}

	public static synchronized PrologContainerLockHelper getInstance() {
		if (instance == null) {
			instance = new PrologContainerLockHelper();
		}
		return instance;
	}
	public synchronized Object GetInBoundCancelLockObj(String containerNo) {
		if (this.LockMap.containsKey(containerNo)) {
            return this.LockMap.get(containerNo);
        } else {
			Object lockObj = new Object();
			this.LockMap.put(containerNo, lockObj);
			return lockObj;
		}
	}
}
