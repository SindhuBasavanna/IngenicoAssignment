package com.ingenico.moneytransfer.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;
/**
 * 
 * @author sindhu
 *
 */
@Component
public class LockUtil {

	private Map<Integer, Lock> lockmap = new ConcurrentHashMap<Integer, Lock>();

	public Map<Integer, Lock> getLockmap() {
		return lockmap;
	}

	public void setLockmap(Map<Integer, Lock> lockmap) {
		this.lockmap = lockmap;
	}

	public void putLockEntry(Integer accountId) {
		if (lockmap.get(accountId) == null)
			lockmap.put(accountId, new ReentrantLock());
	}

	public Lock getLockEntry(Integer accountId) {
		if (lockmap.get(accountId) == null)
			lockmap.put(accountId, new ReentrantLock());
		return lockmap.get(accountId);
	}

}
