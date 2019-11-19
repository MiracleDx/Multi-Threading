package com.multi.threading.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * MyThreadPool
 * 简化线程池，仅用来说明工作原理
 * @author Dongx
 * Description:
 * Created in: 2019-11-19 上午9:25
 * Modified by:
 */
public class MyThreadPool {

	// 利用阻塞队列实现生产者消费者模式
	BlockingDeque<Runnable> workQueue;
	
	// 保存内部工作线程
	List<WorkerThread> threads = new ArrayList<>();
	
	// 构造方法
	MyThreadPool(int poolSize, BlockingDeque<Runnable> workQueue) {
		this.workQueue = workQueue;
		for (int i = 0; i < poolSize; i++) {
			WorkerThread work = new WorkerThread();
			work.start();
			threads.add(work);
		}
	}
	
	// 提交任务
	void execute(Runnable command) {
		try {
			workQueue.put(command);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 工作线程负责消费任务， 并执行任务
	class WorkerThread extends Thread {
		
		public void run() {
			// 循环取任务并执行
			while (true) {
				Runnable task = null;
				try {
					task = workQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				task.run();
			}
		}
	}

	// 使用示例
	public static void main(String[] args) {
		// 创建有界阻塞队列
		BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(2);
		// 创建线程池
		MyThreadPool pool = new MyThreadPool(10, workQueue);
		// 提交任务
		pool.execute(() -> {
			System.out.println("Hello World");
		});
	}
}