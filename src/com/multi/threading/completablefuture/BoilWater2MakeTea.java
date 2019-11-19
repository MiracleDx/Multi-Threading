package com.multi.threading.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * BoilWater2MakeTea
 * 烧水泡茶
 * @author Dongx
 * Description:
 * Created in: 2019-11-19 下午1:43
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
		// 任务1 洗水壶 -> 烧开水 -> 泡茶
		CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
			System.out.println("T1：洗水壶...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("T1：烧开水...");
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		// 任务2 需要执行的任务 洗茶壶 -> 洗茶杯 -> 拿茶叶 
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("T2:洗茶壶...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("T2:洗茶杯...");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("T2:拿茶叶...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "龙井";
		});
		
		// 任务3 任务1和任务2执行完成后：泡茶
		CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
			System.out.println("T1：拿到茶叶：" + tf);
			System.out.println("T1:泡茶..."); return "上茶:" + tf;
		});
		
		// 等待任务3执行结果
		try {
			System.out.println(f3.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 描述串行关系 3依赖2依赖1
		CompletableFuture<String> f0 = CompletableFuture
				.supplyAsync(() -> "Hello World") // 1
				.thenApply(s -> s + " QQ") // 2
				.thenApply(String::toUpperCase); //3
		System.out.println(f0.join());
		
		// 描述AND汇聚关系
		BoilWater2MakeTea bm = new BoilWater2MakeTea();
		bm.done();
		
		// 描述OR汇聚关系
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			int t = new Random().nextInt(10);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return String.valueOf(t);
		});
		
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
			int t = 10;
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return String.valueOf(t);
		});
		
		CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);
		System.out.println(f3.join());
		
		// 异常处理
		CompletableFuture<Integer> f4 = CompletableFuture
				.supplyAsync(() -> 7 / 0)
				.thenApply(r -> r * 10)
				.exceptionally(e -> 0);
		System.out.println(f4.join());
		
	}
}
