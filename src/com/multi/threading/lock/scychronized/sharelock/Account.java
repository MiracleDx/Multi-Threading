package com.multi.threading.lock.scychronized.sharelock;

/**
 * Account
 * 传入共享Lock保护资源
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account {
	
	private Object lock;
	
	private int balance;
	
	private Account() {
		
	}
	
	public Account(Object lock) {
		this.lock = lock;
	}
	
	// 转账
	void transfer(Account target, int amt) {
		// 此处检查所有对象共享的锁
		synchronized (lock)  {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}
