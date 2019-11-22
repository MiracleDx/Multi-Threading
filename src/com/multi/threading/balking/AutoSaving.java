package com.multi.threading.balking;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * AutoSaving
 * 多线程版本的if, 互斥锁的实现 
 * @author Dongx
 * Description:
 * Created in: 2019-11-22 下午3:15
 * Modified by:
 */
public class AutoSaving {
	
	// 文件是否被修改过
	boolean changed = false;
	
	// 定时任务线程池
	ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

	//　定时执行自动保存
	void startAutoSave() {
		ses.scheduleWithFixedDelay(() -> {
			this.autoSave();
		}, 5, 5, TimeUnit.SECONDS);
	}
	
	// 自动存盘操作
	void autoSave() {
		synchronized (this) {
			if (!changed) {
				return;
			}
			changed = true;
		}
	// 执行存盘操作
	this.execSave();
	}
	
	// 编辑操作
	void edit() {
		// 省略编辑逻辑
		change();
	}
	
	// 改变状态
	void change() {
		synchronized (this) {
			changed = true;
		}
	}
	
	void execSave() {
		
	}
}
