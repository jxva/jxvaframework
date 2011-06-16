package net.jxva.dao.sqlparse.clause;

import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String txt="ni hao aa bb hello world";
		String result="aa bb";
		txt=Pattern.compile(result).matcher(txt).replaceAll(",");
		System.out.println(txt);
	}

}
