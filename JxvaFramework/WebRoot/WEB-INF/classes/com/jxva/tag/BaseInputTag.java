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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.jxva.util.ModelUtil;

/**
 * 
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-27 10:44:05 by Jxva
 */
public class BaseInputTag extends BaseTag {

	private static final long serialVersionUID = 1L;

	public BaseInputTag() {
		super();
	}
	
	protected String result;
	protected String bean;

	public int doStartTag() throws JspException {
		try {
			FormTag fromtag = (FormTag) TagSupport.findAncestorWithClass(this,FormTag.class);
			bean = fromtag.getBean();
			if (pageContext.getAttribute(bean) != null) {
				Object obj = ModelUtil.getPropertyValue(pageContext.getAttribute(bean),name);
				result=(obj==null?"":obj.toString());
			}
		} catch (NullPointerException e) {
			result = null;
		}
		return EVAL_BODY_INCLUDE;
	}

    protected String prepareEventHandlers() {
    	StringBuilder sb=new StringBuilder();
        prepareMouseEvents(sb);
        prepareKeyEvents(sb);
        prepareTextEvents(sb);
        prepareFocusEvents(sb);
        return sb.toString();
    }
	
	protected void prepareMouseEvents(StringBuilder sb) {
		prepareAttribute(sb, "onclick", onclick);
		prepareAttribute(sb, "ondblclick",ondblclick);
		prepareAttribute(sb, "onmouseover",onmouseover);
		prepareAttribute(sb, "onmouseout", onmouseout);
		prepareAttribute(sb, "onmousemove",onmousemove);
		prepareAttribute(sb, "onmousedown",onmousedown);
		prepareAttribute(sb, "onmouseup",onmouseup);
	}

    protected void prepareKeyEvents(StringBuilder sb) {
        prepareAttribute(sb, "onkeydown",onkeydown);
        prepareAttribute(sb, "onkeyup",onkeyup);
        prepareAttribute(sb, "onkeypress",onkeypress);
    }
    
    protected void prepareTextEvents(StringBuilder sb) {
        prepareAttribute(sb, "onselect",onselect);
        prepareAttribute(sb, "onchange",onchange);
    }
    
    protected void prepareFocusEvents(StringBuilder sb) {
        prepareAttribute(sb, "onblur",onblur);
        prepareAttribute(sb, "onfocus",onfocus);
    }
    
    protected String prepareOtherAttribute(){
    	StringBuilder sb=new StringBuilder();
    	prepareAttribute(sb, "alt",alt);
    	prepareAttribute(sb, "title",title);
    	prepareAttribute(sb, "id", id);
    	prepareAttribute(sb, "class",cssClass);
    	prepareAttribute(sb, "style",cssStyle);
        return sb.toString();
    }
    
    protected String prepareStates(){
    	StringBuilder sb=new StringBuilder(32);
       	if(readonly)sb.append(" readonly=\"true\"");
		if(disabled)sb.append(" disabled=\"true\"");
        return sb.toString();
    }
    
	protected void prepareAttribute(StringBuilder sb, String attribute,Object value) {
		if (value != null) {
			sb.append(' ');
			sb.append(attribute);
			sb.append("=\"");
			sb.append(value);
			sb.append('"');
		}
	}
	
	protected boolean hasBeanValue(){
		return !(bean==null||pageContext.getAttribute(bean)==null||result==null);
	}
}