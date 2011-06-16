package org.jxva.ip;

import java.util.ArrayList;
import java.util.List;

public class Client {
	
	private static final List<String> list=new ArrayList<String>();
	
	static{
		list.add("江西");
		list.add("山东");
		list.add("山西");
		list.add("河南");
		list.add("河北");
		list.add("湖南");
		list.add("湖北");
		list.add("辽宁");
		list.add("浙江");
		list.add("安徽");
		list.add("江苏");
		list.add("北京");
		list.add("天津");
		list.add("上海");
		list.add("重庆");
		list.add("宁夏");
		list.add("西藏");
		list.add("新疆");
		list.add("内蒙");
		list.add("香港");
		list.add("澳门");
		list.add("台湾");
		list.add("黑龙");
		list.add("云南");
		list.add("吉林");
		list.add("安徽");
		list.add("广东");
		list.add("广西");
		list.add("海南");
		list.add("甘肃");
		list.add("福建");
		list.add("四川");
		list.add("贵州");
		list.add("陕西");
		list.add("青海");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s=IPSeeker.getInstance().getCountry("218.75.63.12");
		System.out.println(list.contains(s.substring(0,2)));
		System.out.println(IPSeeker.getInstance().getAddress("222.243.199.210"));

	}

}
