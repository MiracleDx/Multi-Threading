package com.multi.threading.workerthread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

/**
 * EchoServer
 * Worker-Thread 服务端原封不动的将客户端的请求发送回客户端
 * @author Dongx
 * Description:
 * Created in: 2019-11-26 下午2:50
 * Modified by:
 */
public class EchoServer {
	
	void server() {
		ExecutorService es = new ThreadPoolExecutor(50, 500,
				60L, TimeUnit.MINUTES,
				// 创建有界队列
				new LinkedBlockingDeque<Runnable>(2000),
				// 根据业务需求实现ThreadFactory
				r -> new Thread(r, "echo-" + r.hashCode()),
				// 根据业务需求实现RejectedExecutionHandler
				new ThreadPoolExecutor.CallerRunsPolicy());
				
		ServerSocketChannel ssc = null;
		try {
			ssc = ServerSocketChannel.open().bind(new InetSocketAddress(8080));
			// 处理请求
			while (true) {
				SocketChannel sc = ssc.accept();
				// 将请求处理任务提交给线程池
				es.execute(() -> {
					// 读Socket
					ByteBuffer rb = ByteBuffer.allocateDirect(1024);
					try {
						sc.read(rb);
						// 模拟处理请求
						Thread.sleep(2000);
						// 写Socket
						ByteBuffer wb = rb.flip();
						sc.write(wb);
						// 关闭Socket
						sc.close();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ssc != null) {
				try {
					ssc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			es.shutdown();
		}
	}
}
