package com.multi.threading.scychronized.avoiddeadlock.onetimeresource;

/**
 * Account 避免死锁
 * 一次性申请资源破坏循环等待条件
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account {
	
	// Allocator 应该为单例
	private Allocator actr = Allocator.getInstance();
	
	private int balance;
	
	// 转账
	void transfer(Account target, int amt) {
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
}
