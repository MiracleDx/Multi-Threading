package com.multi.threading.lock.scychronized.avoiddeadlock.sortresource;

/**
 * Account 避免死锁
 * 通过资源排序破坏循环等待条件
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account {
	
	// Allocator 应该为单例
	private Allocator actr = Allocator.getInstance();

	private int id;
	
	private int balance;

	// 通过资源排序破坏循环等待条件
	void transfer(Account target, int amt) {
		Account left = this;
		Account right = target;
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


