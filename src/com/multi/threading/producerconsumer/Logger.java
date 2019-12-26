package com.multi.threading.producerconsumer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * Logger
 *
 * @author Dongx
 * Description:
 * Created in: 2019-12-17 下午4:47
 * Modified by:
 */
public class Logger {
	// 任务队列
	final BlockingQueue<LogMsg> bq = new ArrayBlockingQueue<>(5000);
	
	// flush批量
	static final int batchSize = 500;
	
	// 一个线程写日志
	ExecutorService es = Executors.newFixedThreadPool(1);
	
	// 启动写日志线程
	void start() throws IOException {
		File file = File.createTempFile("foo", ".log");
		final FileWriter writer = new FileWriter(file);
		this.es.execute(() -> {
			try {
				// 未刷盘日志数量
				int curIndex = 0;
				long preFt = System.currentTimeMillis();
				while (true) {
					LogMsg log = bq.poll(5, TimeUnit.SECONDS);
					// 写日志
					if (log != null) {
						writer.write(log.toString());
						++curIndex;
					}
					
					// 如果不存在未刷盘数据，无需刷盘
					if (curIndex <= 0) {
						continue;
					}
					
					// 根据规则进行刷盘
					if (log != null && log.level == Level.ERROR ||
						curIndex == batchSize || System.currentTimeMillis() - preFt > 5000) {
						writer.flush();
						curIndex = 0;
						preFt = System.currentTimeMillis();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					writer.flush();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
		// 写INFO级别的日志
		
		void info(String msg) {
			try {
				bq.put(new LogMsg(Level.INFO, msg));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		void error(String msg) {
			try {
				bq.put(new LogMsg(Level.ERROR, msg));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
}
