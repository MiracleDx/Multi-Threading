package com.multi.threading.scychronized.finelock.parallel;

/**
 * Account 并行
 * 细粒度锁，防止锁定class影响效率，有死锁风险
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:15
 * Modified by:
 */
public class Account {
	
	private int balance;
	
	// 转账
	void transfer(Account target, int amt) {
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
	}
}
