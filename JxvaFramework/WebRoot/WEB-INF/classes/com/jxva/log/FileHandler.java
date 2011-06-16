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

import java.io.File;

import com.jxva.entity.Path;
import com.jxva.util.FileUtil;
import com.jxva.util.NIOUtil;

/**
 * 日志保存到文件<br>
 * 默认的日志文件名格式为yyyy-MM-dd.log<br>
 * 默认保存目录为: /WEB-INF/logs/<br>
 * 保存目录可以自行在logger.properties文件中设置<br>
 * 如:
 * 		jxva.log.file.savepath=C:/
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:41:26 by Jxva
 */
public class FileHandler implements Handler{
	
	private static final String logspath=Path.WEB_INF_PATH+"logs/";
	private File logsFile;

	public void log(LogRecord logRecord) {
		String s=logRecord.getDatetime().toString().substring(0,10);
		if(logsFile==null||logsFile.getName().indexOf(s)==-1){
			logsFile=new File(logspath+s+".log");
			FileUtil.create(logsFile);
		}
		NIOUtil.append(logsFile,Formatter.format(logRecord));
	}
}
