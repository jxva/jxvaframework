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
package org.jxva.log;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

import com.jxva.entity.Path;

/**
 * JDK Logging Apater
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:43:17 by Jxva
 */
public class LoggerApater {
	
	private static java.util.logging.Logger log;
	private static LoggerApater logger=null;
		
	private LoggerApater(){}
	
	public static LoggerApater getInstance(Class<?> cls){
		if(logger==null){
			LogManager lm = LogManager.getLogManager();
			try {
				FileInputStream fi = new FileInputStream(new File(Path.CLASS_PATH+"logging.properties"));
				lm.readConfiguration(fi);
			} catch (Exception e) {
				System.out.println("DDDDDDDDDDDDDDDD"+lm);
			}
			log = java.util.logging.Logger.getLogger(cls.getName());
		    lm.addLogger(log);
		    logger=new LoggerApater();
		}
		return logger;
	}
	
	public static void main(String args[]){
		LoggerApater l=LoggerApater.getInstance(LoggerApater.class);
		l.info("fdsfsa");
	}
	
	public void severe(String msg){
		log.severe(msg);
	}
	
	public void warning(String msg){
		log.warning(msg);
	}
	
	public void info(String msg){
		log.info(msg);
	}
	
	public void config(String msg){
		log.config(msg);
	}
	
	public void fine(String msg){
		log.fine(msg);
	}
	
	public void finer(String msg){
		log.finer(msg);
	}
	
	public void finest(String msg){
		log.finest(msg);
	}
	
	public void trace(Exception e){
		log.severe(getStackTrace(e));
	}
	
	private String getStackTrace(Exception e){
		StackTraceElement[] traces=e.getStackTrace();
		StringBuilder sb=new StringBuilder();
		sb.append("\r\n");
		for(StackTraceElement trace:traces){
			sb.append("\tat ").append(trace).append("\n");
		}
		return sb.toString();
	}
}
