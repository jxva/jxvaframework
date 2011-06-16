package study.prop;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		Properties p=new Properties();
		p.load(new InputStreamReader(new FileInputStream("E:/eclipse/workspace/JxvaCore/java/com/study/prop/test.properties"), "UTF-8"));
		//p.load(new FileInputStream("E:/eclipse/workspace/JxvaCore/java/com/study/prop/test.properties"));
		System.out.println(p.getProperty("username"));
	}

}
