package com.multi.threading.lock.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AsyncToSync
 * 异步API调用转同步API调用
 * @author Dongx
 * Description:
 * Created in: 2019-11-07 下午4:20
 * Modified by:
 */
public class AsyncToSync {
	
	// 创建锁
	private final Lock lock = new ReentrantLock();
	
	// 创建条件变量
	private final Condition done = lock.newCondition();
	
	// 响应结果
	private Response response;
	
	// 请求方法
	Object doInvoke(int timeout) throws Exception {
		// 省略业务代码
		return request().get(timeout);
	}
	
	AsyncToSync request() {
		// 请求结果
		Response res = null;
		doReceived(res);
		// 返回当前调用实例，这里只做展示
		return new AsyncToSync();
	}
	
	// 调用方通过该方法等待结果
	Object get(int timeout) throws Exception {
		long start = System.nanoTime();
		lock.lock();
		try {
			while (!isDone()) {
				done.await(timeout, TimeUnit.NANOSECONDS);
				long cur = System.nanoTime();
				// 如果已经完成或者超时就跳出循环
				if (isDone() || cur - start > timeout) {
					break;
				}
			}
		} finally {
			lock.unlock();
		}
		
		if (!isDone()) {
			throw new TimeoutException();
		}
		
		return returnFromResponse();
	}
	
	// RPC结果是否已经返回
	boolean isDone() {
		return response != null;
	}
	
	// RPC结果返回时调用
	private void doReceived(Response res) {
		lock.lock();
		try {
			response = res;
			if (done != null) {
				done.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}
	
	// 从响应中返回结果
	Object returnFromResponse() {
		// 省略业务代码
		return response;
	}
	
	class Response {
		
	}
}
