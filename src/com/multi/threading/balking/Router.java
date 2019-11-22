package com.multi.threading.balking;

/**
 * Router
 * 路由信息
 * @author Dongx
 * Description:
 * Created in: 2019-11-22 下午3:51
 * Modified by:
 */
public final class Router {
	
	private final String ip;
	
	private final Integer port;
	
	private final String iface;
	
	public Router(String ip, Integer port, String iface) {
		this.ip = ip;
		this.port = port;
		this.iface = iface;
	}
	
	// 重写equals方法
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Router) {
			Router r = (Router) obj;
			return iface.equals(r.iface) &&
					iface.equals(r.ip) &&
					port.equals(r.port);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}

	public String getIp() {
		return ip;
	}

	public Integer getPort() {
		return port;
	}

	public String getIface() {
		return iface;
	}
}
