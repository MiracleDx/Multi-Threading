package com.multi.threading.lock.scychronized;

/**
 * SychronizedDemo
 *
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:07
 * Modified by:
 */
public class SychronizedDemo {
	
	// 修饰非静态方法
	synchronized void foo() {
		// 临界区
	}
	
	// 修饰静态方法
	synchronized static void bar() {
		// 临界区
	}
	
	// 修饰代码块
	Object obj = new Object();
	void baz() {
		synchronized (obj) {
			// 临界区
		}
	}
}
