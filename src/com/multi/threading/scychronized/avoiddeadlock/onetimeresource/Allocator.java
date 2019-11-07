package com.multi.threading.scychronized.avoiddeadlock.onetimeresource;

import java.util.ArrayList;
import java.util.List;

/**
 * Allocator
 * 共享资源调度器
 * @author Dongx
 * Description:
 * Created in: 2019-11-06 上午10:42
 * Modified by:
 */
public class Allocator {

	// volatile只保证可见性，不保证原子性
	private static volatile Allocator instance;

	private Allocator() {
	}

	private List<Object> als = new ArrayList<>();

	// 双重检查锁单例
	public static Allocator getInstance() {
		// 双重锁检查可能返回一个空的instance对象，因为volatile只保证可见性，不保证原子性
		//if (instance == null) {
		//	synchronized (Allocator.class) {
		//		if (instance == null) {
		//			instance = new Allocator();
		//		}
		//	}
		//}
		//return instance;
		return AllocatorHandler.instance;
	}

	// 一次性申请所有资源
	synchronized boolean apply(Object from, Object to) {
		if (als.contains(from) || als.contains(to)) {
			return false;
		} else {
			als.add(from);
			als.add(to);
		}
		return true;
	}

	// 归还资源
	synchronized void free(Object from, Object to) {
		als.remove(from);
		als.remove(to);
	}

	// 静态内部类，实现单例
	private static class AllocatorHandler {
		private static Allocator instance = new Allocator();
	}
}
