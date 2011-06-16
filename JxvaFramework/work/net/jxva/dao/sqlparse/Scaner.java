package net.jxva.dao.sqlparse;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scaner {
	private final static String[] KEY_WORD = {"select","from","left","join","fetch","where","in","and","like","or","order","group","by"};

	public static void main(String[] args){
		String sql="from Book   b left   join fetch  b.user u where b.userId in (select a.userId from User a where a.userId in (1,2,3,4) and a.username like '%t%') and  b.bookId in (1,2,3,4) and b.title='fdaf''dsa' and b.title like '%c%' order by b.bookId desc";
		// 用正则表达式查找匹配字符
		//使用'(?:[^']|'')*'匹配SQL中的字符,如果只匹配单个字符可以使用\'.+?\'
		Matcher m = Pattern.compile("/\\*.*\\*/|'(?:[^']|'')*'|(\\++|\\--|\\>=|\\<=)|\\d+\\.\\d*|\\w+|\".+\"|\\S").matcher(sql);
		while (m.find()) {
			check(m.group());
		}
	}

	private static void check(String s) {// 检测匹配字符串
		if (Character.isDigit(s.charAt(0)))
			checkDigit(s);
		else if (s.length() == 1)
			checkChar(s.charAt(0));
		else
			checkString(s);
	}

	private static void checkDigit(String s){ // 检测数字
		if (s.indexOf(".") != -1)
			out("浮点数", Double.valueOf(s).toString());
		else
			out("整     数", s);
	}

	private static void checkChar(char c){ // 检测单个字符
		switch (c) {
		case '<':
			out("小于号", c);
			break;
		case '>':
			out("大于号", c);
			break;
		case '=':
			out("等于号", c);
			break;
		case '+':
			out("加     号", c);
			break;
		case '-':
			out("减     号", c);
			break;
		case '*':
			out("乘     号", c);
			break;
		case '/':
			out("除     号", c);
			break;
		case '(':
			out("左小括号", c);
			break;
		case ')':
			out("右小括号", c);
			break;
		case '[':
			out("左中括号", c);
			break;
		case ']':
			out("右中括号", c);
			break;
		case '{':
			out("左大括号", c);
			break;
		case '}':
			out("右大括号", c);
			break;
		case ',':
			out("逗     号", c);
			break;
		case ';':
			out("分     号", c);
			break;
		case '!':
			out("取     反", c);
			break;
		case '.':
			out("点     号", c);
			break;
		case ':':
			out("冒     号", c);
			break;
		default:
			out("标识符", c);
			break;
		}
	}

	private static void checkString(String s){ // 检测多个字符
		if (s.charAt(0) == '\'')
			out("字     符", s);
		else if (s.charAt(0) == '"')
			out("字符串", s);
		else if (s.charAt(0) == '/')
			out("注      释", s);
		else if (s.equals("++"))
			out("自加符", s);
		else if (s.equals("--"))
			out("自减符", s);
		else if (s.equals(">="))
			out("大于等于", s);
		else if (s.equals("<="))
			out("小于等于", s);
		else if (isKeyWord(s))
			out("关键字", s);
		else
			out("标识符", s);
	}

	private static boolean isKeyWord(String s){ // 是否是关键字
		return Arrays.asList(KEY_WORD).contains(s);
	}

	private static void out(String s, Object o){ // 向显示器输出结果
		if (o instanceof String)
			System.out.println(s + " : " + (String) o);
		else
			System.out.println(s + " : "+ ((Character) o).charValue());

	}


}