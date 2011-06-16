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
package com.jxva.mvc;

import java.lang.reflect.InvocationTargetException;
import com.jxva.exception.NestableRuntimeException;

/**
 * MVC运行时异常处理类<br>
 * <b>Usage:</b>
 * <pre>
 * try{
 *    //do something
 * }catch(Exception e){
 *    throws new MvcException(e);
 * }
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:10:41 by Jxva
 */
public class MvcException extends NestableRuntimeException {

	private static final long serialVersionUID = -5356341184035030569L;

	public MvcException(){
		super();
	}
	
	public MvcException(Throwable root) {
		super(root);
	}

	public MvcException(String string, Throwable root) {
		super(string, root);
	}

	public MvcException(String s) {
		super(s);
	}
	
	public static String getStackTrace(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(toString(e.getStackTrace()));
		return sb.toString();
	}
	
	public static String getInvocationTargetException(InvocationTargetException e){
		StringBuilder sb = new StringBuilder();
		sb.append(toString(e.getTargetException().getStackTrace()));
		return sb.toString();
	}
	
	public static String toString(StackTraceElement[] traces){
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement trace:traces){
			sb.append("\t at ").append(trace).append("<br/>\r\n");
		}
		return sb.toString();
	}
	
	public static String outPrint(String name,String str){
		StringBuilder sb = new StringBuilder();
		sb.append("<font style='font-weight:bold;'>"+name+"</font><br/>\r\n");
		sb.append("<div style='padding:6px 40px;font-size:12px;line-height:130%;'>");
		sb.append(str);
		sb.append("</div><hr/><b>Jxva Framework</b>");
		return sb.toString();
	}
}
