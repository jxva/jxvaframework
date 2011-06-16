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

import java.util.ArrayList;
import java.util.List;

/**
 * 日志级别类,以类来替换enum
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:41:55 by Jxva
 */
//public enum Level {FATAL,ERROR,WARN,INFO,DEBUG,TRACE}
public final class Level {
	
    private static List<Level> known = new ArrayList<Level>();

    //表示一个严重失败。导致application的中断
	public static final Level FATAL = new Level("fatal", 0); 
	
	//表示一个错误事件。可以允许Application可以继续运行
	public static final Level ERROR = new Level("error", 1);
	
 	//表示一个有潜在的危险
	public static final Level WARN = new Level("warn", 2);
	
	//表示一个构件正在做重要运行情况，信息比较粗糙
	public static final Level INFO = new Level("info", 3); 	
	
	//表示一个构件详细的运行情况，用于调试Application
	public static final Level DEBUG = new Level("debug",4);
	
	//非常详细的跟踪信息，仅仅用于纪录该日志而已
	public static final Level TRACE = new Level("trace",5);


	private final String name;
	private final int value;
	
	protected Level(String name, int value) {
		this.name = name;
		this.value = value;
		known.add(this);
	}
	
	public static Level parse(String name){
		try{
			for (int i = 0; i < known.size(); i++) {
			    Level l = (Level) known.get(i);
			    if (name.equalsIgnoreCase(l.name.trim())) {
			    	return l;
			    }
			}
		}catch(Exception e){}
		return INFO;
	}
	
	public String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	public final int intValue() {
		return value;
	}
	
    public boolean equals(Object ox) {
    	try {
    	    Level lx = (Level)ox;
    	    return (lx.value == this.value);
    	} catch (Exception ex) {
    	    return false;
    	}
    }

    public int hashCode() {
    	return this.value;
    }
    
    
}


