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
 *
 */
package study.oxm;

import java.lang.reflect.Method;

/**
 * @author  The Jxva Framework Foundation
 *
 */
public class BeanUtil {
	
	/**
	 * 
	 * @param cls
	 * @param fieldName
	 * @param paras
	 * @return Method
	 * @throws Exception
	 */
	public static Method getSetter(Class<?> cls, String fieldName, Class<?>[] paras)throws Exception {
		return  cls.getMethod("set"+upperFirstChar(fieldName), paras);
	}
	
	/**
	 * 
	 * @param cls
	 * @param fieldName
	 * @param paras
	 * @return Method
	 * @throws Exception
	 */
	public static Method getGetter(Class<?> cls, String fieldName, Class<?>[] paras)throws Exception {
		return cls.getMethod("get"+upperFirstChar(fieldName), paras);
	}
	
	/**
	 * 
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public static Object getPropertyValue(Object obj,String propertyName)throws Exception{
		return BeanUtil.getGetter(obj.getClass(),propertyName,null).invoke(obj);

	}
	
	private static String upperFirstChar(String str){		
		//遍历每一个字母,进行相应处理
		StringBuffer sb=new StringBuffer(str);
		for(int i=0;i<sb.length();i++){
			//如果首字符不是26个英文字母中的某个,则去掉该字符
			if(i==0
				&&!(
					((int)'a'<=(int)sb.charAt(i))&&((int)sb.charAt(i)<=(int)'z')
					||((int)'A'<=(int)sb.charAt(i))&&((int)sb.charAt(i)<=(int)'Z')
					)
			){
				sb.deleteCharAt(i);
				i--;
				continue;
			}//如果首字符不是26个英文字母中的某个,则去掉该字符 ends
			
			//如果是首字母,并且是小写字母的话,将它转为大写
			if(i==0&&((int)'a'<=(int)sb.charAt(i))&&((int)sb.charAt(i)<=(int)'z')){
				sb.setCharAt(i,(char)((int)sb.charAt(i)-32));
			}//如果是首字母,并且是小写字母的话,将它转为大写 ends
		}
		//遍历每一个字母,进行相应处理 ends
		
		return sb.toString();
	}	
}
