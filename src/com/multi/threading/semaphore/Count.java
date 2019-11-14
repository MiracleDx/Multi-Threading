package com.multi.threading.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Count
 * 信号量
 * @author Dongx
 * Description:
 * Created in: 2019-11-14 上午9:43
 * Modified by:
 */
public class Count {
	
	static int count;
	
	// 初始化信号量
	static final Semaphore s = new Semaphore(1);
	
	// 用信号量保证互斥
	static void addOne() throws InterruptedException {
		s.acquire();
		try {
			count += 1;
		} finally {
			s.release();
		}
		
	}
}
