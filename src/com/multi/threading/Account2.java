package com.multi.threading;

/**
 * Account2
 * 用class保护资源
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account2 {
	
	private int balance;
	
	// 转账
	void transfer(Account2 target, int amt) {
		// 此处检查所有对象共享的锁
		synchronized (Account.class)  {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}
