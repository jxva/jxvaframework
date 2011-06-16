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
package com.jxva.entity;


/**
 *  在如下环境下进行了测试
 *  <pre>
 * Test Environment
 *   Windows Linux Unix
 *   Tomcat6.0.10
 *   Resin3.1.1
 *   Jboss4.0.0/5.0.0
 *   WebLogic9.1
 *   WebSphere6.1
 *   WasCE1.1
 *   Apusic4.0.3
 *   JFox3
 *   Jetty6.1.3
 *  </pre>
 * @author  The Jxva Framework Foundation 
 * @since   1.0
 * @version 2008-11-26 14:14:23 PM by Jxva
 *
 */
public abstract class Path{
		
	public static final String CLASS_PATH;
	
	//if this path include "WEB-INF" will return "WEB-INF"'s absolute path,otherwise return "classes"'s parent path
	public static final String WEB_INF_PATH;
	public static final String APP_PATH;
	public static final String ROOT_PATH;

	
	static{
		String currentPath=getPath(Path.class);
		
		if(currentPath.indexOf(".jar!/")>-1||currentPath.indexOf("classes")>-1){
			String classPath=currentPath.replaceAll("/./","/");		//weblogic
			//if this class be included .jar file,will replace "/lib/*.!/" to "/classes/"
			classPath=classPath.replaceAll("/lib/([^\'']+)!/","/classes/"); //jar
			classPath=classPath.split("/classes/")[0]+"/classes/";
			//if os is not windows system
			if(classPath.indexOf(':')<0){
				classPath='/'+classPath;
			}
			CLASS_PATH=classPath;
		}else{
			CLASS_PATH=Path.class.getClassLoader().getResource(".").getPath().substring(1);
		}
			
		WEB_INF_PATH=CLASS_PATH.substring(0,CLASS_PATH.substring(0,CLASS_PATH.lastIndexOf('/')).lastIndexOf('/')+1);
		
		APP_PATH=WEB_INF_PATH.substring(0,WEB_INF_PATH.substring(0,WEB_INF_PATH.lastIndexOf('/')).lastIndexOf('/')+1);
	
		ROOT_PATH=CLASS_PATH.substring(0,CLASS_PATH.indexOf('/')+1);
	}
	
	
	
	/**
	 * 获取参数cls的目录路径
	 * @param cls
	 * @return
	 */
	public static String getPath(Class<?> cls){
		String t=getAbsoluteFile(cls);
		return t.substring(0,t.lastIndexOf('/')+1).replaceAll("(file:/)|(file:)|(wsjar:)|(jar:)|(zip:)","");
//		t=t.replaceAll("file:/", "");	//windows
//		t=t.replaceAll("file:", "");	//linux,unix
//		t=t.replaceAll("wsjar:","");	//websphere wsjar: has to at jar: before
//		t=t.replaceAll("jar:","");		//tomcat,jboss,resin,wasce,apusic
//		t=t.replaceAll("zip:","");		//weblogic
	}
	
	/**
	 * 获取参数cls的文件路径
	 * @param cls
	 * @return
	 */
	public static String getAbsoluteFile(Class<?> cls){
		return cls.getResource(cls.getSimpleName() + ".class").toString().replaceAll("%20", " ");
	}
}