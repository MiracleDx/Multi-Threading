package com.multi.threading.scychronized.classlock;

/**
 * Account
 * 用class保护资源
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account {
	
	private int balance;
	
	// 转账
	void transfer(Account target, int amt) {
		// 此处检查所有对象共享的锁
		synchronized (Account.class)  {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}
