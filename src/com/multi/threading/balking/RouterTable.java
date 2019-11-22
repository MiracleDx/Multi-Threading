package com.multi.threading.balking;

import java.util.Set;
import java.util.concurrent.*;

/**
 * RouterTable
 * 路由表信息 多线程版本的if, volatile的实现 
 * @author Dongx
 * Description:
 * Created in: 2019-11-22 下午3:55
 * Modified by:
 */
public class RouterTable {
	
	// key 接口名 Value 路由集合
	ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> rt = new ConcurrentHashMap<>();
	
	// 路由表是否发生变化
	volatile boolean changed;
	
	// 将路由表写入本地文件的线程池
	ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	
	// 启动定时任务 将变更后的路由表信息写入本地文件
	public void startLovalSave() {
		ses.scheduleWithFixedDelay(() -> autoSave(), 1, 1, TimeUnit.MINUTES);
	}
	
	// 保存路由表到本地文件
	void autoSave() {
		if (!changed) {
			return;
		}
		changed = false;
		// 将路由表写入本地文件
		this.save2Local();
	}
	
	// 删除路由
	public void remove(Router router) {
		Set<Router> set = rt.get(router.getIface());
		if (set != null) {
			set.remove(router);
			// 路由表已发生变化
			changed = true;
		}
	}
	
	// 增加路由
	public void add(Router router) {
		Set<Router> set = rt.computeIfAbsent(router.getIface(), r -> new CopyOnWriteArraySet<>());
		set.add(router);
		// 路由表已发生变化
		changed = true;
	}
	
	private void save2Local() {
		
	}
	
}
