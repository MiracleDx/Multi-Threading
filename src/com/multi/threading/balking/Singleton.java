package com.multi.threading.balking;

/**
 * Singleton
 * balking下的单利
 * @author Dongx
 * Description:
 * Created in: 2019-11-22 下午3:24
 * Modified by:
 */
public class Singleton {
	
	private static volatile Singleton singleton;
	
	private Singleton(){
		
	}
	
	public static Singleton getInstance() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}
