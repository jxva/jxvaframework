package org.jxva;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jxva.ip.IPSeeker;

import com.jxva.util.FileUtil;


public class ShowIpAddress {
	
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
		list.add("中国");
		list.add("长城");
	}

	public static void main(String[] args) throws SQLException {
		String txt=FileUtil.read(new File("E:/eclipse/workspace/JxvaFramework/src/org/jxva/ip.txt"));
		String[] ips=txt.split("\n");
		for(String ip:ips){
			String s=IPSeeker.getInstance().getCountry(ip);
			System.out.println(s+"=ok");
			//if(s.length()>=2){
			//	System.out.println(s+"=ok");
				//if(list.contains(s.substring(0,2))){
				//	System.out.println(s+"=error");
		        	//return;
				//}
			//}
	
		}
	}
}
