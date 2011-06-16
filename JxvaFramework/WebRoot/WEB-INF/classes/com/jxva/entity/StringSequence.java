package com.jxva.entity;

import com.jxva.util.StringUtil;

public abstract class StringSequence {
	/**
	 * 将字符串编码成表示每个字母的整型值的序列 比如:abc转换为97.98.99
	 * @param str 字符串
	 * @return 编码后的整型值序列
	 */
	public static String encode(String str) {
		if (StringUtil.isEmpty(str))return str;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			sb.append(Integer.toString((int) str.charAt(i))).append('.');
		}
		return sb.substring(0,sb.length()-1);
	}

	/**
	 * 将整型值序列解码为字符串
	 * @param str 整型序列串
	 * @return 解码后的字符串
	 */
	public static String decode(String str) {
		if (StringUtil.isEmpty(str))return str;
		String[] tokens = str.split("\\.");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.length; i++) {
			char ch = (char) Integer.parseInt(tokens[i]);
			sb.append(ch);
		}
		return sb.toString();
	}
}
