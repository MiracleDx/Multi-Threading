package com.multi.threading.balking;

/**
 * InitTest
 * 单次初始化
 * @author Dongx
 * Description:
 * Created in: 2019-11-22 下午4:15
 * Modified by:
 */
public class InitTest {
	
	boolean inited = false;
	
	synchronized void init() {
		if (inited) {
			return;
		}
		
		// 初始化
		doInit();
		inited = true;
	}
	
	void doInit() {
		
	}
}
