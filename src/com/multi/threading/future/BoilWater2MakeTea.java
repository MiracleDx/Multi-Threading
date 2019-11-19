package com.multi.threading.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * BoilWater2MakeTea
 * 烧水泡茶
 * @author Dongx
 * Description:
 * Created in: 2019-11-19 上午10:23
 * Modified by:
 */
public class BoilWater2MakeTea {

	/**
	 * 
	 * 洗水壶（1分钟） -> 烧开水（15分钟）-> 泡茶
	 * 洗茶壶（1分钟）-> 泡茶
	 * 洗茶杯（2分钟）-> 泡茶
	 * 拿茶叶（1分钟）-> 泡茶
	 * -> 泡茶 
	 * 
	 * 洗水壶（1分钟） -> 烧开水（15分钟）-> 泡茶
	 * 洗茶壶（1分钟）-> 洗茶杯（2分钟）-> 拿茶叶（1分钟） -> 泡茶
	 * -> 泡茶
	 * 
	 */
	
	public void done() {
		// 创建任务2 洗茶壶 洗茶杯 拿茶叶
		FutureTask<String> ft2 = new FutureTask<>(new T2Task());
		
		// 创建任务1 洗水壶 烧开水
		FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));
		
		// 线程1执行任务ft1
		Thread thread1 = new Thread(ft1);
		thread1.start();
		
		// 线程2执行任务ft2
		Thread thread2 = new Thread(ft2);
		thread2.start();
		
		// 等待T1执行结果
		try {
			System.out.println(ft1.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	// T1Task需要执行的任务 洗水壶 -> 烧开水 -> 泡茶
	class T1Task implements Callable<String> {
		
		FutureTask<String> ft2;
		
		// T1任务需要T2任务的FutureTask
		T1Task(FutureTask<String> ft2) {
			this.ft2 = ft2;
		}

		@Override
		public String call() throws Exception {
			System.out.println("T1：洗水壶...");
			TimeUnit.SECONDS.sleep(1);
			
			System.out.println("T1：烧开水...");
			TimeUnit.SECONDS.sleep(15);
			
			// 获取T2线程的茶叶
			String tf = ft2.get();
			System.out.println("T1：拿到茶叶 -> " + tf);

			System.out.println("T1：泡茶...");
			
			return "上茶：" + tf;
		}
	}

	// T2Task 需要执行的任务 洗茶壶 -> 洗茶杯 -> 拿茶叶 
	class T2Task implements Callable<String> {

		@Override
		public String call() throws Exception {
			System.out.println("T2:洗茶壶...");
			TimeUnit.SECONDS.sleep(1);

			System.out.println("T2:洗茶杯...");
			TimeUnit.SECONDS.sleep(2);

			System.out.println("T2:拿茶叶...");
			TimeUnit.SECONDS.sleep(1);
			return "龙井";
		}
	}

	public static void main(String[] args) {
		BoilWater2MakeTea bm = new BoilWater2MakeTea();
		bm.done();
	}
}