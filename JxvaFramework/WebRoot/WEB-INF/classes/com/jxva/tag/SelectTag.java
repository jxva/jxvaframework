
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
 *
 */
package com.jxva.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.jxva.util.ModelUtil;

public class SelectTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_VALUE="default_value_for_jxva_framework";

	protected String name;
	protected String multiple;
	protected String size;
	protected String onchange;
	protected String cssClass;
	protected String cssStyle;
	protected String id;
	protected String defaultValue;
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setMultiple(String multiple) {
		this.multiple =multiple;
	}
    public void setSize(String size) {
        this.size = size;
    }
    
	public void setOnchange(String onchange) {
		this.onchange=onchange;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int doStartTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder(128);
			sb.append("<select name=\"").append(name).append("\"");
			if(multiple!=null)TagUtil.addAttribute(sb,"multiple","true");
			TagUtil.addAttribute(sb,"size",size);
			TagUtil.addAttribute(sb,"onchange",onchange);
			TagUtil.addAttribute(sb,"class",cssClass);
			TagUtil.addAttribute(sb,"style",cssStyle);
			TagUtil.addAttribute(sb,"id",id);
			sb.append('>');
			pageContext.getOut().print(sb.toString());

			FormTag formTag = (FormTag) TagSupport.findAncestorWithClass(this,FormTag.class);
			if (formTag!=null) {
				String bean = formTag.getBean();
				Object obj = pageContext.getAttribute(bean);
				if(obj!=null){
					pageContext.setAttribute(name, ModelUtil.getPropertyValue(obj,name));
					if(defaultValue!=null)pageContext.setAttribute(DEFAULT_VALUE,MvelTagUtil.findValue(pageContext,defaultValue));
				}
			}
			return EVAL_BODY_INCLUDE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		pageContext.removeAttribute(DEFAULT_VALUE);
		return EVAL_PAGE;
	}

	public int doAfterBody() throws JspException {
		try {
			pageContext.getOut().print("</select>");
			pageContext.removeAttribute(name);
			pageContext.removeAttribute(name+".value");
			return EVAL_BODY_INCLUDE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
}
