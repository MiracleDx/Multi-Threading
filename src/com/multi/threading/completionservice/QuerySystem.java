package com.multi.threading.completionservice;

import java.util.concurrent.*;

/**
 * QuerySystem
 * 使用CompletionService实现询价系统
 * @author Dongx
 * Description:
 * Created in: 2019-11-19 下午3:39
 * Modified by:
 */
public class QuerySystem {
	
	public void done() {
		// 创建线程池
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		// 创建completionservice
		CompletionService<Integer> cs = new ExecutorCompletionService<>(executorService);
		
		// 异步向电商1询价
		cs.submit(() -> getPriceByS1());
		// 异步向电商2询价
		cs.submit(() -> getPriceByS2());
		// 异步向电商3询价
		cs.submit(() -> getPriceByS3());
		
		// 将询价结果异步保存到数据库
		for (int i = 0; i < 3; i++) {
			try {
				Integer r = cs.take().get();
				executorService.execute(() -> save(r));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		}
	}
	
	Integer getPriceByS1() {
		return 1;
	}

	Integer getPriceByS2() {
		return 2;
	}
	
	Integer getPriceByS3() {
		return 3;
	}
	
	void save(Integer price) {
		
	}
}
