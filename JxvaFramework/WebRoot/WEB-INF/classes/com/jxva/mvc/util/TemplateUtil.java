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
package com.jxva.mvc.util;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;

import javax.servlet.ServletException;

import org.mvel2.PropertyAccessException;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import com.jxva.entity.Path;
import com.jxva.mvc.ActionContext;
import com.jxva.plugin.SysPlugin;
import com.jxva.tag.TagUtil;
import com.jxva.util.FileUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-28 17:41:52 by Jxva
 */
public abstract class TemplateUtil {
	
	private static final ConcurrentMap<String,CompiledTemplate> compileds=new ConcurrentHashMap<String,CompiledTemplate>(); 
	
	public static CompiledTemplate compile(final String tplName){
		synchronized(compileds){
			CompiledTemplate o = compileds.get(tplName);
	        if (o == null) {
	        	final String tplContent=FileUtil.read(new File(Path.APP_PATH+tplName));
				final Matcher matcher = TagUtil.PATTERN_CONTEXT.matcher(tplContent);  
				StringBuffer sb = new StringBuffer(tplContent.length()+256);
				while (matcher.find()) {
					final String result=matcher.group(0);
					int dotPos=result.indexOf('.');
					String tmp=null;
					if(dotPos>-1){
				    	String prefix=result.substring(1,dotPos);
				    	if(prefix.equals("parameter")){
				    		tmp="request.getParameter('"+result.substring(dotPos+1)+"')";
				    	}else{
				    		tmp=prefix+".getAttribute('"+result.substring(dotPos+1)+"')";
				    	}
					}else{
						int bracketPos=result.indexOf('[');
						String prefix=result.substring(1,bracketPos);
						if(prefix.equals("parameter")){
							tmp="request.getParameter('"+result.substring(bracketPos+2,result.lastIndexOf('\''))+"')";
						}else{
							tmp=prefix+".getAttribute("+result.substring(bracketPos+1,result.length()-1)+")";
						}
					}
					matcher.appendReplacement(sb,tmp);
				}
				matcher.appendTail(sb);
				o=TemplateCompiler.compileTemplate(sb.toString());
				if(!SysPlugin.DEBUG)compileds.put(tplName,o);
	        }
	        return o;
		}
	}
	
	public static String execute(String tplName) throws ServletException{
		try{
			return (String)TemplateRuntime.execute(compile(tplName),ActionContext.getTagContext());
		} catch(PropertyAccessException e) {
			compileds.remove(tplName);
            throw new ServletException(e);
        }
	}
}
