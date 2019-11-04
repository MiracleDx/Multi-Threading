package com.multi.threading;

/**
 * SafeCalc
 * 用synchronized解决count += 1问题
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 21:11
 * Modified by:
 */
public class SafeCalc {
	
	long value = 0L;
	
	synchronized long get() {
		return value;
	}
	
	synchronized void addOne() {
		value += 1;
	}
	
}
