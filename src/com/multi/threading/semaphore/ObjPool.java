package com.multi.threading.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * ObjPool
 *
 * @author Dongx
 * Description:
 * Created in: 2019-11-14 上午9:56
 * Modified by:
 */
public class ObjPool<T, R> {
	
	final List<T> pool;
	
	// 用信号量实现限流器
	final Semaphore semaphore;
	
	// 构造函数
	ObjPool(int size, T t) {
		pool = new Vector<T>(){};
		for (int i = 0; i < size; i++) {
			pool.add(t);
		}
		semaphore = new Semaphore(size);
	}
	
	// 利用对象池的对象调用func
	R exec(Function<T, R> func) throws InterruptedException {
		T t = null;
		semaphore.acquire();
		try {
			t = pool.remove(0);
			return func.apply(t);
		} finally {
			pool.add(t);
			semaphore.release();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// 创建对象池
		ObjPool<Long, String> pool = new ObjPool<Long, String>(10, 2L);
		// 通过对象池获取t，之后执行
		pool.exec(t -> {
			System.out.println(t);
			return t.toString();
		});
		
	}
}
