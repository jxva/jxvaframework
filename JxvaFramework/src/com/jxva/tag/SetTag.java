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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
  <j:set name="var" value=""/>
  <tag>
    <name>set</name>
    <tag-class>com.jxva.tag.SetTag</tag-class>
    <body-content>empty</body-content>
    <attribute>
        <name>name</name>
        <required>true</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
    <attribute>
        <name>value</name>
        <required>true</required>
        <rtexprvalue>true</rtexprvalue>
    </attribute>
  </tag>
  
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-05 23:53:44 by Jxva
 */
public class SetTag extends TagSupport{
	
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String value;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		/**
		try{
			if(getType()!=null){
				Object obj=Class.forName(getType()).newInstance();
				if(obj instanceof String){
					pageContext.setAttribute(getName(),getValue());
				}else if(obj instanceof Integer){
					pageContext.setAttribute(getName(),Integer.valueOf(getValue()));
				}else if(obj instanceof Timestamp){
					pageContext.setAttribute(getName(),Timestamp.valueOf(getValue()));
				}else if(obj instanceof Short){
					pageContext.setAttribute(getName(),Short.valueOf(getValue()));
				}else if(obj instanceof Long){
					pageContext.setAttribute(getName(),Long.valueOf(getValue()));
				}else if(obj instanceof Date){
					pageContext.setAttribute(getName(),Date.valueOf(getValue()));
				}else if(obj instanceof Double){
					pageContext.setAttribute(getName(),Double.valueOf(getValue()));
				}else if(obj instanceof Time){
					pageContext.setAttribute(getName(),Time.valueOf(getValue()));
				}else if(obj instanceof Character){
					pageContext.setAttribute(getName(),Character.toString(getValue().charAt(0)));
				}else if(obj instanceof BigDecimal){
					pageContext.setAttribute(getName(),new BigDecimal(getValue()));
				}
			}else{
				pageContext.setAttribute(getName(),getValue());
			}
		}catch(Exception e){
			pageContext.setAttribute(getName(),getValue());
		}
		*/
		pageContext.setAttribute(name,MvelTagUtil.findValueByIterateTag(this,pageContext,value));
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
