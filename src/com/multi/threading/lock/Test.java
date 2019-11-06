package com.multi.threading.lock;

/**
 * Test
 *
 * @author Dongx
 * Description:
 * Created in: 2019-11-04 20:43
 * Modified by:
 */
public class Test {
	
	private long count = 0;
	
	private void add10K() {
		int idx = 0;
		while(idx ++ < 10000) {
			count += 1;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final Test test = new Test();
		// 创建两个线程执行add操作
		Thread th1 = new Thread(() -> {
			test.add10K();
		});
		Thread th2 = new Thread(() -> {
			test.add10K();
		});

		// 启动两个线程
		th1.start();
		th2.start();
		
		// 等待两个线程结束
		th1.join();
		th2.join();
		
		System.out.println(test.count);
	}
}
