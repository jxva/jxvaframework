package net.jxva.dao.sqlparse;

import java.util.regex.Pattern;

public class Splitter {
	public static void main(String[] args) throws Exception {
		// Create a pattern to match breaks
		Pattern p = Pattern.compile("[\\s]*,[\\s]*");//"[,\\s]+"用split对以逗号和或空格分隔的输入字符串进行切分
		// Split input with the pattern
		String[] result = p.split(" one , two , three four ,  five");
		for (int i = 0; i < result.length; i++)
			System.out.println(result[i]);
	}
}
