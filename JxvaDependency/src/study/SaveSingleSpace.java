/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package study;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-05 13:08:47 by Jxva
 */
public class SaveSingleSpace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String t="I  Love     You        ".trim();
		Pattern p=Pattern.compile(" {2,}");
		Matcher m=p.matcher(t);
		String r=m.replaceAll(" ");
		System.out.println(r);
	}
	
	public static void test(){
		String stringInfo = "{infoNum='10' EdwardBlog='http://hi.baidu.com/Edwardworld'       topicLength='20'    titleShow='yes' EdwardMotto='I am a man,I am a true man!' /}";
	     
	      System.out.println("待处理的字符串：" + stringInfo);

	         Pattern p=Pattern.compile("[.,\"\\?!:']");//增加对应的标点

	         Matcher m=p.matcher(stringInfo);

	         String first=m.replaceAll(""); //把英文标点符号替换成空，即去掉英文标点符号

	         System.out.println("去掉英文标点符号后的字符串：" + first);

	         p=Pattern.compile(" {2,}");//去除多余空格

	         m=p.matcher(first);

	         String second=m.replaceAll(" ");

	         System.out.println("去掉多余空格后的字符串" + second);//second为最终输出的字符串
	}

}
