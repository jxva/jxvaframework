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
package com.jxva.log;

import java.sql.Timestamp;

import com.jxva.entity.Debug;

/**
 * 日志实现类<br>
 * <b>Usage:</b>
 * <pre>
 * private static final Logger log=Logger.getLogger(Your.class);
 * log.info("dosomething");
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:42:11 by Jxva
 */
public final class Logger {
	
	private static final LogConfig conf=new LogConfig();
	private static final LogRecord logRecord=new LogRecord();
	private static final Logger logger=new Logger();
	private static final FileHandler FILE_HANDLER=new FileHandler();
	
	//private String className;
	private static final boolean consoleHandler;
	private static final boolean fileHandler;
	private static final int consoleLevel;
	private static final int fileLevel;
	
	static{
		consoleHandler=conf.getConsoleHandler();
		fileHandler=conf.getFileHandler();
		consoleLevel=conf.getConsoleLevel().intValue();
		fileLevel=conf.getFileLevel().intValue();
	}
	
	public static Logger getLogger(Class<?> cls){
		//logger.className=cls.getName();
		return logger;
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
		if(consoleHandler||fileHandler){
			logRecord.setDatetime(new Timestamp(System.currentTimeMillis()));
			logRecord.setLevel(level);
			logRecord.setMsg(msg);
			//logRecord.setClassName(logger.className);
		}else{
			return;
		}

		if(consoleHandler){
			if (consoleLevel >= level.intValue()) {
				System.out.print(Formatter.format(logRecord));
			}
		}
		if(fileHandler){
			if (fileLevel >= level.intValue()) {
				FILE_HANDLER.log(logRecord);
			}
		}
	}
}