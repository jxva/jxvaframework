package com.jxva.entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.jxva.log.Logger;

public abstract class Debug {

	private static final Logger log=Logger.getLogger(Debug.class);
	
	public static String getStackTrace(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.close();
		pw.flush();
		try {
			sw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return sw.toString();
	}
	
	public static void callback(Class<?> cls,String msg){
		log.debug(cls.getName()+" -> "+msg);
	}
	
	public static void callback(String msg){
		log.debug(msg);
	}
	
	public static void callback(Exception e,String msg){
		log.debug(msg+' '+getStackTrace(e));
	}
	
	public static void callback(Exception e){
		log.debug(getStackTrace(e));
	}
	
	 public static void print(Exception e) {
		String str=getStackTrace(e);
		int idx = str.indexOf(')');
		idx = str.indexOf(')', idx + 1);
		idx = str.indexOf(')', idx + 1);
		int ldx = str.lastIndexOf(' ', idx);
		log.debug('\n'+str.substring(ldx + 1, idx + 1));
	}
}