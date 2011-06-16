package net.jxva;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A=(a1= (a2=v2) (a3=v3) (a4=(a5=v5)) (a6=v6))B=(b1= (b2=v2) (b3=v3) (b4=(b5=v5)) (b6=v6))C=(b1= (b2=v2) (b3=v3) (b4=(b5=v5)) (b6=v6))D=(b1= (b2=v2) (b3=v3) (b4=(b5=v5)) (b6=v6))EB=(b1= (b2=v2) (b3=v3) (b4=(b5=v5)) (b6=v6))

 * @author Administrator
 *
 */
public class SQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String s="a.id in (1,2,3)          and a.name='adsfasdf''adfasdf''''adfasdf''' or a.type='aas' or a.test1 exists (select  from b where b.id in (2,2,5,7)) and 1=1 and (a.id = 1 or a.type=2) and (c.dfafd=adsfasf and c.daat='adfafda') and a.id between 10 and 20 or a.name not like 'test%'";
		String s="select @ from Book b left join User u union (select @ from Book b0 right join User u0)";
		 // 去除前后空白
        s=s.trim();
        // 将Sql语句中原有的回车换行替换成空白
        s=s.replaceAll("(\\n+|\\r+)", " ");
		
		//单引号内数据独立
		Matcher m=Pattern.compile("'(?:[^']|'')*'").matcher(s);
		int p=0;
		while(m.find()){
			String result=m.group();
			System.out.println(result);
			s=s.replaceAll(result,"_"+p);
			p++;
		}
		//除去多余空格
		s=s.replaceAll(" {2,}"," ");
		System.out.println(s);
		System.out.println("------------------------");
		
		//小括号内数据独立
		//Matcher m1=Pattern.compile("\\(.*?\\)").matcher(s);//只匹配最内一层
		//Matcher m1=Pattern.compile("\\([^()]*\\)").matcher(s);//只匹配最内一层
		Matcher m1=Pattern.compile("\\(([^()]|\\([^()]*\\))*\\)").matcher(s);//只匹配最内两层
		//<div[^>]*>[^<>]*(((?'Open'<div[^>]*>)[^<>]*)+((?'-Open'</div>)[^<>]*)+)*(?(Open)(?!))</div> //匹配嵌套的DIV
		//Matcher m1=Pattern.compile("<div[^>]*>[^<>]*(((?'Open'<div[^>]*>)[^<>]*)+((?'-Open'</div>)[^<>]*)+)*(?(Open)(?!))</div>").matcher(s);
		int p1=1000;
		while(m1.find()){
			String result1=m1.group();
			System.out.println(result1);
			//result1=result1.replaceAll("\\(","\\\\(");
			//result1=result1.replaceAll("\\)","\\\\)");
			System.out.println(result1);
			s=s.replaceAll(result1,"_"+p1);
			p1++;
		}
		System.out.println(s);
	}

}
