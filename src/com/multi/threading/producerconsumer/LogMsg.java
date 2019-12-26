package com.multi.threading.producerconsumer;

/**
 * LogMsg
 *
 * @author Dongx
 * Description:
 * Created in: 2019-12-26 上午8:56
 * Modified by:
 */
public class LogMsg {
	
	Level level;
	
	String msg;
	
	LogMsg(Level level, String msg) {
		this.level = level;
		this.msg = msg;
	}
}
