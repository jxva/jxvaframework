
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
 * @since 2006-12-16
 * <j:radio prop="property" value="" checked="true|false" disabled="true|false" readonly="true|false" Event="*"/>
 * 
 *<tag>
    <name>radio</name>
    <tag-class>com.jxva.tag.RadioTag</tag-class>
    <body-content>JSP</body-content>
    <attribute>
        <name>prop</name>
        <required>true</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
    <attribute>
        <name>value</name>
        <required>true</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
    <attribute>
        <name>[Others]</name>
        <required>false</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
    <attribute>
        <name>[States]</name>
        <required>false</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
    <attribute>
        <name>[Events]</name>
        <required>false</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
  </tag>
 */

public class RadioTag extends BaseInputTag {

	private static final long serialVersionUID = 1L;
	
	public int doEndTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder(128);
			sb.append("<input type=\"radio\" name=\"").append(name).append("\" value=\"").append(value).append("\"");
			sb.append(prepareOtherAttribute());
			sb.append(prepareStates());
			sb.append(prepareEventHandlers());
			if(!hasBeanValue()){
				if(checked)sb.append(" checked=\"true\"");			
			}else{
				if(HtmlUtil.filter(result).equals(value))sb.append(" checked=\"true\"");
			}
			sb.append(" />");
			pageContext.getOut().print(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
}