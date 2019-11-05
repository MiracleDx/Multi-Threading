package com.multi.threading;

import java.util.ArrayList;
import java.util.List;

/**
 * Account4
 * 
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account4 {
	
	// Allocator 应该为单例
	private Allocator actr = Allocator.getInstance();
	
	private int balance;
	
	// 转账
	void transfer(Account4 target, int amt) {
		// 一次性申请转入和转出账户，直到成功
		while (!actr.apply(this, target)) {
		}
		
		try {
			// 锁定转出账户
			synchronized (this) {
				// 锁定转入账户
				synchronized (target) {
					if (this.balance > amt) {
						this.balance -= amt;
						target.balance += amt;
					}
				}
			}
		} finally {
			actr.free(this, target);
		}
	}
	
	// 通过资源排序破坏循环等待条件
	private int id;
	void transfer2(Account4 target, int amt) {
		Account4 left = this;
		Account4 right = target;
		if (this.id > target.id) {
			left = target;
			right = this;
		}
		
		// 锁定序号小的账户
		synchronized (left) {
			// 锁定序号大的账户
			synchronized (right) {
				if (this.balance > amt) {
					this.balance -= amt;
					target.balance += amt;
				}
			}
		}
			
	}
}

class Allocator {
	
	private static volatile Allocator instance;
	
	private Allocator() {
	}

	private List<Object> als = new ArrayList<>();
	
	public static Allocator getInstance() {
		// 双重锁检查可能返回一个空的instance对象，volatile只保证可见性，不保证原子性
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
	
	private static class AllocatorHandler {
		private static Allocator instance = new Allocator();
	}
}
