package org.jxva.ip;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class IP {
	
	private static final ConcurrentMap<String,String> cache=new ConcurrentHashMap<String,String>();
	private static final IPSeeker ipSeeker=IPSeeker.getInstance();
	
	public static String getAddress(String ip){
		final String _ip=ip.substring(0,ip.lastIndexOf('.')+1)+'1';
		String address=cache.get(_ip);
		if(address==null){
			address=ipSeeker.getAddress(_ip);
			cache.put(_ip,address);
		}
		return address;
	}
}
