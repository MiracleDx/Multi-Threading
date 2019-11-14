package com.multi.threading.cyclicbarrier;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CheckAccount
 *
 * @author Dongx
 * Description:
 * Created in: 2019-11-14 下午3:15
 * Modified by:
 */
public class CheckAccount {

	// 对账订单标志位
	volatile boolean flag;
	
	// 订单队列
	Vector<Object> pos;
	
	// 派送单队列
	Vector<Object> dos;
	
	// 执行回调的线程
	Executor executor =Executors.newFixedThreadPool(1);
	final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
		executor.execute(() -> {
			Check();
		});
	});
	
	void Check() {
		Object p = pos.remove(0);
		Object d = dos.remove(0);
		// 执行对账操作
		Object diff = checkAccount(p, d);
		// 差异写入差异库
		save(diff);
	}
	
	void checkAll() {
		// 循环查询订单库
		Thread t1 = new Thread(() -> {
			while (!flag) {
				// 查询订单库
				pos.add(getPOrders());
				// 等待
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
		
		// 循环查询运单库
		Thread t2 = new Thread(() -> {
			while (!flag) {
				// 查询运单库
				dos.add(getDOrders());
				// 等待
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		});
		t2.start();
	}

	Object checkAccount(Object p, Object d) {
		return new Object();
	}
	
	void save(Object diff) {
		
	}

	List<Object> getPOrders(){
		return new Vector<>();
	}

	List<Object> getDOrders(){
		return new Vector<>();
	}
	
	
}
