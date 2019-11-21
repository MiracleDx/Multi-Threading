package com.multi.threading.guardedsuspension;

import java.net.IDN;
import java.net.http.HttpResponse;

/**
 * Test
 *
 * @author Dongx
 * Description:
 * Created in: 2019-11-21 下午3:10
 * Modified by:
 */
public class Test {
	
	// 发送消息
	void send(Message msg) {
		
	}
	
	// 消息返回调用的方法
	void onMessage(Message msg) {
		// 唤醒等待线程
		GuardedObject.fireEvent(msg.id, msg);
	}
	
	//
	HttpResponse handWebReq() {
		
		int id = 123123123;
		// 创建一条消息
		Message msg1 = new Message();
		// 创建GuardedObject实例
		GuardedObject<Message> go = GuardedObject.create(id);
		// 发送消息
		send(msg1);
		// 等待MQ消息
		Message r = go.get(t -> t != null);
		return null;
	}
}
