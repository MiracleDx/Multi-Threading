package com.multi.threading.guardedsuspension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * GuardedObject
 *  等待一个条件满足 -> 等待异步消息返回（异步调用转同步）
 * @author Dongx
 * Description:
 * Created in: 2019-11-21 下午2:59
 * Modified by:
 */
public class GuardedObject<T> {
	
	// 受保护的对象
	T obj;
	
	final Lock lock = new ReentrantLock();
	
	final Condition done = lock.newCondition();
	
	final int timeout = 2;
	
	// 保存所有的GuardedObject
	final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();
	
	// 静态方法创建GuardedObject
	static <K> GuardedObject create(K key) {
		GuardedObject go = new GuardedObject();
		gos.put(key, go);
		return go;
	}
	
	// 释放资源
	static <K, T> void fireEvent(K key, T obj) {
		GuardedObject go = gos.remove(obj);
		if (go != null) {
			go.onChanged(obj);
		}
	}
	
	// 获取受保护的账号
	T get(Predicate<T> p) {
		lock.lock();
		try {
			// MESA管程推荐写法
			while (!p.test(obj)) {
				try {
					done.await(timeout, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			lock.unlock();
		}
		// 返回非空的受保护对象
		return obj;
	}
	
	// 事件通知
	void onChanged(T obj) {
		lock.lock();
		try {
			this.obj = obj;
			done.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
