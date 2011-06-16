
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

import com.jxva.util.HtmlUtil;

/**
 * @author  The Jxva Framework Foundation
 * @since 2006-12-16 <j:input name="msgid" value=""/>
 * 
 *        <tag> <name>input</name>
 *        <tag-class>com.jxva.tag.InputTag</tag-class>
 *        <body-content>JSP</body-content> <attribute> <name>name</name>
 *        <required>true</required> <rtexprvalue>true</rtexprvalue> </attribute>
 *        <attribute> <name>value</name> <required>false</required>
 *        <rtexprvalue>true</rtexprvalue> </attribute> </tag>
 */

public class InputTag extends BaseInputTag {

	private static final long serialVersionUID = 1L;

	protected String size;
	protected String maxlength;

	public void setSize(String size) {
		this.size = size;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public int doEndTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder(32);
			sb.append("<input type=\"text\" name=");
			sb.append('"').append(name).append('"');
			if (!hasBeanValue()) {
				prepareAttribute(sb, "value",value);
			} else {
				prepareAttribute(sb, "value",HtmlUtil.filter(result));
			}
			prepareAttribute(sb, "size",size);
			prepareAttribute(sb, "maxlength",maxlength);
			sb.append(prepareOtherAttribute());
			sb.append(prepareStates());
			sb.append(prepareEventHandlers());
			sb.append(" />");
			pageContext.getOut().print(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
}
