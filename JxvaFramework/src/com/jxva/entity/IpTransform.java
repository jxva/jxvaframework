package com.jxva.entity;

public class IpTransform {

	/**
	 * 字符串ip转换为long
	 * 
	 * @param 字符串ip
	 * @return
	 */
	public static long encode(String ip) {
		String[] ips = ip.split("[.]");
		long num = 16777216L * Long.parseLong(ips[0]) + 65536L
				* Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2])
				+ Long.parseLong(ips[3]);
		return num;
	}

	/**
	 * 长整型ip转换为string
	 * 
	 * @param long型ip
	 * @return
	 */
	public static String decode(long ipLong) {
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, '.');
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}
	
	public static void main(String[] args){
		String ip="192.168.1.1";
		long e=encode(ip);
		System.out.println(e);
		System.out.println(decode(e));
	}

}
