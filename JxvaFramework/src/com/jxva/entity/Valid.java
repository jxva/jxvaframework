package com.jxva.entity;

import java.util.regex.Pattern;

import com.jxva.util.StringUtil;

public abstract class Valid{
	
	public static boolean isNull(String str){
		return str==null;
	}
	
	public static boolean isEmpty(String str){
		return str==null||str.length()==0;
	}
	
	public static boolean hasLength(String str){
		return StringUtil.hasLength(str);
	}
	
	public static boolean hasText(String str){
		return StringUtil.hasText(str);
	}
	
	public static boolean isUsername(String username,int min,int max){
		return isMatch("^[a-zA-Z][a-zA-Z0-9_]{"+(min-1)+","+(max-1)+"}$",username);
	}
	
	public static boolean isPassword(String password,int min,int max){
		return isMatch("[\\p{ASCII}]{"+min+","+max+"}$",password);
	}
	
	public static boolean isEmail(String email){
		return isMatch("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",email);
	}
	
	public static boolean isIp(String str){
		return isMatch("^(\\d{1}|\\d{2}|[0-1]\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1}|\\d{2}|[0-1]\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1}|\\d{2}|[0-1]\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1}|\\d{2}|[0-1]\\d{2}|2[0-4]\\d|25[0-5])$",str);
	}
	
	public static boolean isUrl(String str){
		return isMatch("^(file|http|https|ftp|mms|telnet|news|wais|mailto):\\/\\/(.+)$",str);
	}
	
	public static boolean isMobile(String str){
		return isMatch("^0{0,1}1(3|5)[0-9]{9}$",str);
	}
	
	public static boolean isPhone(String str){
		return isMatch("(^[0-9]{3,4}\\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\\([0-9]{3,4}\\)[0-9]{3,8}$)|(^[0-9]{3,4}[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)",str);
	}
	
	
	public static boolean isPostcode(String str){
		return isMatch("^(\\d){6}$",str);
	}
	
	/**
	 * 正则表达式数据匹配
	 * @param regex 正则表达式
	 * @param data 数据
	 * @return 匹配:true 不匹配:false
	 */
	public static boolean isMatch(String regex,String data){
		return data==null?false:Pattern.compile(regex).matcher(data).find();
	}
}
