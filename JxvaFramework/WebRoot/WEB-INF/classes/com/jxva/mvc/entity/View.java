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
package com.jxva.mvc.entity;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * 2008-12-31 1.0 beta
 * 		具备DAO,MVC其它功能
 * 2009-01-31 1.0 rc1
 * 		新增N点,与改进了N点
 * 2009-03-02 1.0 rc2
 * 		新增DAO中的JQL解析器
 * 2009-04-01 1.0 rc3
 * 2009-05-01 1.0 rc4
 * 2009-06-01 1.0 rc5
 * 2009-07-01 1.0 release 
 * View Layer Print
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:00:32 by Jxva
 */
public abstract class View {		
	
	public static final String HEADER="<b style='font-size:16px;font-family:Arial, Helvetica, SimSun, sans-serif;'>Jxva Framework Debug Information</b><pre style='padding:6px 10px;font-size:12px;font-family:Verdana;line-height:150%;'>\n";
	
	public static final String FOOTER="\n</pre><hr size='1' noshade style='border:1px dotted #000000'/><b style='font-size:12px;font-family:Arial, Helvetica, SimSun, sans-serif;'>Copyright &copy; 2006-2010 By Jxva Framework Version 1.0.0 Release Candidate Six</b>";
	
	public static String getInfoWithHeaderAndFooter(final String info){
		final StringBuilder sb=new StringBuilder();
		sb.append(View.HEADER);
		sb.append(info);
		sb.append(View.FOOTER);
		return sb.toString();
	}
	
	public static void printInfo(final PrintWriter writer,final String info){
		writer.print(info);
		writer.flush();
		writer.close();
	}
	
	public static void printInfoWithHeaderAndFooter(final PrintWriter writer,final String info){
		writer.print(View.HEADER);
		writer.print(info);
		printFooter(writer);
	}
	
	public static void printExceptionWithHeaderAndFooter(final PrintWriter writer,final Exception e){
		writer.print(View.HEADER);
		e.printStackTrace(writer);
		printFooter(writer);
	}
	
	public static void printInvocationTargetExceptionWithHeaderAndFooter(final PrintWriter writer,final InvocationTargetException e){
		writer.print(View.HEADER);
		e.getTargetException().printStackTrace(writer);
		printFooter(writer);
	}
	
	private static void printFooter(final PrintWriter writer){
		writer.print(View.FOOTER);
		writer.flush();
		writer.close();
	}
}
