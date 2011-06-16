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

import java.util.ResourceBundle;

import com.jxva.entity.Path;

/**
 * 日志配置文档logger.properties初始化类
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:42:03 by Jxva
 */
public final class LogConfig {
	
	public static final String CONSOLE_HANDLER="jxva.log.console.handler";
	public static final String CONSOLE_LEVEL="jxva.log.console.level";
	public static final String FILE_HANDLER="jxva.log.file.handler";
	public static final String FILE_LEVEL="jxva.log.file.level";
	public static final String FILE_SAVEPATH="jxva.log.file.savepath";
	
	private final ResourceBundle rb;
	
	public LogConfig() {
		rb = ResourceBundle.getBundle("jxva-log");
	}
	
	public boolean getConsoleHandler(){
		return Boolean.valueOf(rb.getString(CONSOLE_HANDLER));
	}
	
	public Level getConsoleLevel(){
		return Level.parse(rb.getString(CONSOLE_LEVEL));
	}
	
	public boolean getFileHandler(){
		return Boolean.valueOf(rb.getString(FILE_HANDLER));
	}
	
	public Level getFileLevel(){
		return Level.parse(rb.getString(FILE_LEVEL));
	}
	
	public String getLogFileSavePath(){
		String savePath=rb.getString("FILE_SAVEPATH");
		if(savePath.equalsIgnoreCase("default")){
			return Path.WEB_INF_PATH+"logs/";
		}else{
			return savePath;
		}
	}
}
