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
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author  The Jxva Framework Foundation
 * @since 2006-12-16 <j:option value=""/>text</j:option>
 */

public class OptionTag extends BodyTagSupport{

	private static final long serialVersionUID = 1L;

	protected String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		return EVAL_BODY_AGAIN;
	}

	public int doAfterBody() throws JspException{
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		try {
			Object defaultValue=null;
			String currentValue=null;
			SelectTag selectTag = (SelectTag)TagSupport.findAncestorWithClass(this,SelectTag.class);
			if(selectTag!=null){
				defaultValue=pageContext.getAttribute(SelectTag.DEFAULT_VALUE);
			}
			
			StringBuilder sb = new StringBuilder(128);
			sb.append("<option value=").append('"');
			currentValue=MvelTagUtil.findValueByIterateTag(this,pageContext,value).toString();
			sb.append(currentValue);
			sb.append('"');
			
			if(defaultValue!=null) {
				if(currentValue.equals(TagUtil.formatValue(defaultValue))){
					sb.append(" selected=\"true\"");
				}
			}
			sb.append('>');
			sb.append(bodyContent.getString());
			sb.append("</option>");
			pageContext.getOut().print(sb.toString());
			return EVAL_PAGE;			
		}catch (IOException e) {
			throw new JspException(e);
		}

	}
	
	public void release() {
		super.release();
		value=null;
	}


}