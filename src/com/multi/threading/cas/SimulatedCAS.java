package com.multi.threading.cas;

/**
 * SimulatedCAS
 * CAS指令模拟代码
 * @author Dongx
 * Description:
 * Created in: 2019-11-18 下午4:59
 * Modified by:
 */
public class SimulatedCAS {
	
	volatile int count;
	
	// 实现count+=1
	int addOne(int newValue) {
		do {
			newValue = count + 1;
		} while (count != cas(count, newValue));
		return newValue;
	}
	
	// 模拟实现CAS
	synchronized int cas(int expect, int newValue) {
		// 读目前count的值
		int curValue = count;
		// 比较目前count值是否==期望值
		if (curValue == expect) {
			// 如果是，则更新count的值
			count = newValue;
		}
		// 返回写入前的值
		return curValue;
	}
}
