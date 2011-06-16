/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
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
package com.jxva.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:27:01 by Jxva
 */
public abstract class TplUtil {

	/**
	 * 替换模板文件参数值
	 * @param str
	 * @param map
	 * @return
	 */
	public static String replaceParams(String str,Map<String,String> map){ 
        for(String key:map.keySet()){     
            str = str.replaceAll("\\$\\{"+key+"\\}",map.get(key)==null?"":map.get(key));
        }          
		return str;
	}

	/**
	 * 生成新的文件
	 * @param tpl
	 * @param newfile
	 * @param map
	 * @return
	 */
	public static boolean createFile(String tpl,String newFileName,Map<String,String> map){
		return NIOUtil.write(new File(newFileName),replaceParams(FileUtil.read(new File(tpl)),map));
	}
		
	public static void main(String[] args){
		Map<String,String> map=new HashMap<String,String>();
		map.put("title","测试一下");
		map.put("content","晕死吧");
		createFile("tpl.htm","new.htm",map);
	}
}
