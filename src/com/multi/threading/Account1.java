package com.multi.threading;

/**
 * Account1
 * 传入共享Lock保护资源
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account1 {
	
	private Object lock;
	
	private int balance;
	
	private Account1() {
		
	}
	
	public Account1(Object lock) {
		this.lock = lock;
	}
	
	// 转账
	void transfer(Account1 target, int amt) {
		// 此处检查所有对象共享的锁
		synchronized (lock)  {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}
