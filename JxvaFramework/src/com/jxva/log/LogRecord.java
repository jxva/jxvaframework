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

/**
 * 日志记录
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:43:37 by Jxva
 */
public class LogRecord {
	
	private Timestamp datetime;
	private String className;
	private Level level;
	private String msg;
	
	public LogRecord(){
		
	}
	
	public LogRecord(String className,Timestamp datetime,Level level,String msg){
		this.className=className;
		this.datetime=datetime;
		this.level=level;
		this.msg=msg;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassName() {
		return className;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Level getLevel() {
		return level;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public Timestamp getDatetime() {
		return datetime;
	}
}
