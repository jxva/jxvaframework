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
package com.jxva.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.jxva.i18n.I18N;

/**
 * <j:i18n name="" key=""/>
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-17 20:03:27 by Jxva
 */
public class I18NTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	protected String key;
	protected String name;


	public void setName(String name) {
		this.name = name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int doStartTag() throws JspException {
		return EVAL_BODY_AGAIN;
	}
	
	public int doEndTag() throws JspException {
		try {
			I18N i18n=new I18N(pageContext.getRequest().getLocale());
			Map<String,String> params=ParamCache.getParams();
			String result;
			if(params.isEmpty()){
				if(name==null){
					result=i18n.getString(key);
				}else{
					result=i18n.getStringByName(name,key);
				}
			}else{
				String[] paramArray=new String[params.size()];
				int i=0;
				for(String s:params.values()){
					paramArray[i]=s;
					i++;
				}
				if(name==null){
					result=i18n.getString(key,paramArray);
				}else{
					result=i18n.getStringByName(name,key,paramArray);
				}
			}
			params.clear();
			pageContext.getOut().print(result);
			return EVAL_PAGE;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}
	
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}
}
