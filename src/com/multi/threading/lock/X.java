package com.multi.threading.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * X
 * reentrantlock
 * @author Dongx
 * Description:
 * Created in: 2019-11-07 下午3:57
 * Modified by:
 */
public class X {
	
	// 传入true代表构造一个公平锁，传入false代表构造一个非公平锁，默认构造非公平锁
	private final Lock rtl = new ReentrantLock();
	
	int value;
	
	public int get() {
		// 获取锁
		rtl.lock();
		try {
			return value;
		} finally {
			// 保证锁能释放
			rtl.unlock();
		}
	}
	
	public void addOne() {
		// 获取锁
		rtl.lock();
		try {
			value += 1;
		} finally {
			// 保证锁能释放
			rtl.unlock();
		}
	}
}
