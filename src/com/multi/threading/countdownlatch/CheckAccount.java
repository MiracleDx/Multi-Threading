package com.multi.threading.countdownlatch;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CheckAccount
 *
 * @author Dongx
 * Description:
 * Created in: 2019-11-14 下午2:34
 * Modified by:
 */
public class CheckAccount {
	
	// 对账订单标志位
	volatile boolean flag;
	
	List<Object> pos, dos, diff;
	
	public void check() throws InterruptedException {
		// 创建两个线程的线程池
		Executor executors = Executors.newFixedThreadPool(2);
		
		// 存在未对账订单
		while (!flag) {
			// 计数器初始化为2
			CountDownLatch latch = new CountDownLatch(2);
			// 查询未对账订单
			executors.execute(() -> {
				pos = getPOrders();
				latch.countDown();
			});
			// 查询派送单
			executors.execute(() -> {
				dos = getDOrders();
				latch.countDown();
			});
			
			// 等待两个查询操作结束
			latch.await();
			
			// 执行对账操作
			diff = checkAccount(pos, dos);
			// 差异写入差异库
			save(diff);
		}
	}

	

	List<Object> getPOrders(){
		return new Vector<>();
	}

	List<Object> getDOrders(){
		return new Vector<>();
	}

	List<Object> checkAccount(List<Object> pos, List<Object> dos) {
		return new Vector<>();
	}
	
	void save(List<Object> diff){};
}
