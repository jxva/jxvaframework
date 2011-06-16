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

import java.io.File;

import javax.servlet.ServletException;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.mvc.util.TemplateUtil;
import com.jxva.util.FileUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-09-18 09:26:31 by Jxva
 */
public class HtmlTemplate {
	
	public String execute(TemplateCallback<String> action,String tplName) throws ServletException {
		action.doInTemplate();
		return TemplateUtil.execute(tplName);
	}
	
	public boolean execute(TemplateCallback<String> action,String tplName,String destName) throws ServletException {
		String text=execute(action,tplName);
		return FileUtil.write(new File(Path.APP_PATH+destName), text,Encoding.UTF_8);
	}
}
