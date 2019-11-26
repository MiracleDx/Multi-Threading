package com.multi.threading.threadpermessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * EchoServer
 * Thread-Per-Message 服务端原封不动的将客户端的请求发送回客户端 OpenJDK轻量级线程->Fiber
 * @author Dongx
 * Description:
 * Created in: 2019-11-26 下午2:17
 * Modified by:
 */
public class EchoServer {
		
	void server() {
		ServerSocketChannel ssc = null;
		try {
			ssc = ServerSocketChannel.open().bind(new InetSocketAddress(8080));
			// 处理请求
			while (true) {
				// 接收请求
				SocketChannel sc = ssc.accept();
				// 每个请求都创建一个线程
				new Thread(() -> {
					// 读socket
					ByteBuffer rb = ByteBuffer.allocate(1024);
					try {
						sc.read(rb);
						// 模拟处理请求
						Thread.sleep(2000);
						// 写socket
						ByteBuffer wb = (ByteBuffer) rb.flip();
						sc.write(wb);
						sc.close();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ssc != null) {
					ssc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
