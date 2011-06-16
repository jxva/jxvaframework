package net.jxva.dao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
     public static void testReg(){
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

     public static void main(String [] args){
      StringUtil.testReg();

     }
}