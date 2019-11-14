package com.multi.threading.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Cache
 *　读写锁
 * @author Dongx
 * Description:
 * Created in: 2019-11-14 上午10:21
 * Modified by:
 */
public class Cache<K, V> {

	final Map<K, V> m = new HashMap<>();
	
	final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	// 读锁
	final Lock readLock = readWriteLock.readLock();
	
	// 写锁
	final Lock writeLock = readWriteLock.writeLock();
	
	// 读缓存
	V get(K key) {
		V v = null;
		
		// 读
		readLock.lock();
		try {
			v = m.get(key);
		} finally {
			readLock.unlock();
		}
		
		// 缓存中存在，返回
		if (v != null) {
			return v;
		}
		
		// 缓存中不存在，查询数据库
		writeLock.lock();
		try {
			// 再次验证
			// 其他线程可能已经查询过数据库
			v = m.get(key);
			if (v == null) {
				// 业务代码
				v = (V) new Object();
				m.put(key, v);
			}
		} finally {
			writeLock.unlock();
		}
		return v;
	}
	
	// 写缓存
	V put(K key, V value) {
		writeLock.lock();
		try {
			return m.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

}

