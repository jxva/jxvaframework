
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
 * 
 * <j:url value="/example/ok.jsp"/>
 * @author  The Jxva Framework Foundation
 * @since 2008-11-05
 * <tag>
 *   <name>url</name>
 *   <tag-class>com.jxva.tag.UrlTag</tag-class>
 *   <body-content>JSP</body-content>
 *   <attribute>
 *       <name>value</name>
 8       <required>true</required>
 *       <rtexprvalue>true</rtexprvalue>
 *   </attribute>
 * </tag>
 */
public class UrlTag  extends TagSupport{

	private static final long serialVersionUID = 1L;

	protected String value;
	
	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		pageContext.setAttribute(value,value);
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		try{
			pageContext.getOut().print(pageContext.getServletContext().getContextPath()+value);
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
}
