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

import java.sql.Timestamp;

import com.jxva.entity.Debug;
import com.jxva.log.Level;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-26 14:09:42 by Jxva
 */
public class Logger2 {
	
	private static final ThreadLocal<Logger2> threadLocal = new ThreadLocal<Logger2>();
	private final String className;
	
	private Logger2(Class<?> cls){
		this.className=cls.getName();
	}
	
	public static Logger2 getLogger(Class<?> cls){
		Logger2 log=threadLocal.get();
		if(log==null){
			log=new Logger2(cls);
			threadLocal.set(log);
		}
		return log;
	}
	
	public void fatal(String msg){
		log(msg,Level.FATAL);
	}
	
	public void error(String msg){
		log(msg,Level.ERROR);
	}
	
	public void warn(String msg){
		log(msg,Level.WARN);
	}
	
	public  void info(String msg){
		log(msg,Level.INFO);
	}
	
	public void debug(String msg){
		log(msg,Level.DEBUG);
	}
	
	public void trace(String msg){
		log(msg,Level.TRACE);
	}
	
	public  void trace(Exception e){
		log(Debug.getStackTrace(e),Level.TRACE);
	}
	

	
	/**
	 * 实现日志添加功能 
	 * @param text
	 * @param level
	 */
	private void log(String msg,Level level){
		StringBuilder sb=new StringBuilder(64);
		sb.append(new Timestamp(System.currentTimeMillis()).toString().substring(11,19)).append(' ');
		sb.append(level.toString().toLowerCase()).append(" :");
		sb.append("[").append(className).append("] -> ");
		sb.append(msg).append("\r\n");
		System.out.print(sb.toString());
		closeLogger();
	}
		
	public static void closeLogger(){
		Logger2 log=threadLocal.get();
		if (log != null) {            	
              threadLocal.set(null); 
        }
	}
}
