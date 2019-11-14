package com.multi.threading.readwritelock;

import javax.xml.crypto.Data;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * CacheData
 * 读写锁，读锁不可以升级为写锁，但是写锁可以降级为读锁
 * @author Dongx
 * Description:
 * Created in: 2019-11-14 上午10:50
 * Modified by:
 */
public class CacheData {
	
	Object data;
	
	volatile boolean cacheValid;
	
	final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	final Lock readLock = readWriteLock.readLock();
	
	final Lock writeLock = readWriteLock.writeLock();
	
	void processCachedData() {
		// 获取读锁
		readLock.lock();
		
		if (!cacheValid) {
			// 释放读锁，以为不支持锁的升级
			readLock.unlock();
			// 获取写锁
			writeLock.lock();
			try {
				// 再次检查状态
				if (!cacheValid) {
					data = new Object();
					cacheValid = true;
				}
				
				// 释放写锁前，降级为读锁
				readLock.lock();
			} finally {
				writeLock.unlock();
			}
		}
		
		// 持有读锁
		try {
			use(data);
		} finally {
			readLock.unlock();
		}
	}
	
	Object use(Object data) {
		return data;
	}
}
