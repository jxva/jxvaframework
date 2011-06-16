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

/**
 *  enctype的值有multipart/form-data,application/x-www-form-urlencoded,text/plain
	三种类型,如果是IE浏览器,在为设置情况下默认值为application/x-www-form-urlencoded。
 * <j:form action="anywhere.jsp" method="post" bean="com.jxva.anything.JavaBean">
 * @author  The Jxva Framework Foundation
 * @since 2006-12-16
 * @modify 2008-10-18
 */

public class FormTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	protected String action;
	protected String method;
	protected String target;
	protected String enctype;
	protected String onsubmit;
	protected String bean;
	protected String name;
	protected String id;
	
	public void setAction(String action) {
		this.action = action;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
	
	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}
	
	public String getBean(){
		return this.bean;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id=id;
	}

	public int doStartTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder("<form ");
			TagUtil.addAttribute(sb, "action",action);
			TagUtil.addAttribute(sb, "method",method);
			TagUtil.addAttribute(sb, "target",target);
			if(enctype!=null){
				sb.append(" enctype=\"multipart/form-data\" ");
			}
			TagUtil.addAttribute(sb, "onsubmit",onsubmit);
			TagUtil.addAttribute(sb, "name",name);
			TagUtil.addAttribute(sb, "id",id);
			sb.append('>');
			pageContext.getOut().print(sb.toString());
			Object obj=MvelTagUtil.findValue(pageContext,bean);
			if (obj!= null) {
				pageContext.setAttribute(bean, obj);
			}
			return EVAL_BODY_INCLUDE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public int doAfterBody() throws JspException {
		try {
			pageContext.getOut().print("</form>");
			pageContext.removeAttribute(bean);
			return EVAL_BODY_INCLUDE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
	
	public void release() {
		super.release();
		action=null;
		method=null;
		target=null;
		enctype=null;
		onsubmit=null;
		bean=null;
		name=null;
		id=null;
	}
}
