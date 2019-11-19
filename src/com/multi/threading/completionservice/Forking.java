package com.multi.threading.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Forking
 * 并行调用多个服务，只要有一个查询成功，整个服务就返回
 * @author Dongx
 * Description:
 * Created in: 2019-11-19 下午3:49
 * Modified by:
 */
public class Forking {
	
	public Integer done() {
		// 创建线程池
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		// 创建completionservice
		CompletionService<Integer> cs = new ExecutorCompletionService<>(executorService);
		
		// 用于保存Future对象
		List<Future<Integer>> futures = new ArrayList<>(3);
		
		// 提交异步任务，并保存future到futures
		futures.add(cs.submit(() -> getPriceByS1()));
		futures.add(cs.submit(() -> getPriceByS2()));
		futures.add(cs.submit(() -> getPriceByS3()));
		
		// 获取最快的返回结果
		Integer r = 0;
		try {
			// 只要有一个成功返回就break
			for (int i = 0; i < 3; i++) {
				try {
					r = cs.take().get();
					// 简单通过判断空检查是否返回成功
					if (r != null) {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} finally {
			// 取消所有任务
			for (Future<Integer> f : futures) {
				f.cancel(true);
			}
		}
		return r;
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
}
