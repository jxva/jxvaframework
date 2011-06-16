package demo.sql;

import java.util.regex.Pattern;

public class Sql {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Pattern pattern = Pattern.compile("admin\\[\\d+\\]");
		System.out.println(pattern.matcher("admin[03]").find());
		
	}

}
