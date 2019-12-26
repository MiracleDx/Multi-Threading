package com.multi.threading.producerconsumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * ProducerCOnsumer
 * 生产者消费者模式
 * @author Dongx
 * Description:
 * Created in: 2019-12-17 下午3:10
 * Modified by:
 */
public class ProducerConsumer {
	
	// 任务队列
	BlockingDeque<Task> bq = new LinkedBlockingDeque<>(2000);
	
	// 启动5个消费线程
	// 执行批量任务
	void start() {
		ExecutorService es = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			es.execute(() -> {
				try {
					while (true) {
						// 获取批量任务
						List<Task> ts = pollTasks();
						// 执行批量任务
						execTasks(ts);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	// 从任务队列中批量获取任务
	List<Task> pollTasks() throws InterruptedException {
		List<Task> ts = new LinkedList<>();
		// 阻塞式获取一条任务
		Task t = bq.take();
		while (t != null) {
			ts.add(t);
			// 非阻塞式获取一条任务
			t = bq.poll();
		}
		return ts;
	}
	
	// 批量执行任务
	void execTasks(List<Task> ts) {
		
	}
}
