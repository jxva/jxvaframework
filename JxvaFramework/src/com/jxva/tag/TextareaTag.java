
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

import com.jxva.util.HtmlUtil;
import com.jxva.util.ModelUtil;

/**
 * <j:textarea prop="property" rows="" cols=""/>
 * or
 * <j:textarea prop="property" rows="" cols="">text</j:textarea>
 * @author  The Jxva Framework Foundation
 * @since 2006-12-25
 * 
 * 
 * 
 */

public class TextareaTag extends BaseInputTag {

	private static final long serialVersionUID = 1L;

	protected String textareaContent;	
	protected String rows;
	protected String cols;
	protected boolean escape;
	protected boolean filter;
	
	public void setRows(String rows){
		this.rows=rows;
	}

	public void setCols(String cols){
		this.cols=cols;
	}
	
	public void setEscape(boolean escape) {
		this.escape = escape;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	
	public int doStartTag() throws JspException {
		textareaContent=null;
		return EVAL_BODY_AGAIN;
	}

	public int doAfterBody() throws JspException{
		if (bodyContent != null) {
            String text = bodyContent.getString();

            if (text != null) {
                text = text.trim();

                if (text.length() > 0) {
                	textareaContent = text;
                }
            }
        }
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		try {
			FormTag fromtag = (FormTag) TagSupport.findAncestorWithClass(this,FormTag.class);
			bean = fromtag.getBean();
			if (pageContext.getAttribute(bean) != null) {				
				Object obj = ModelUtil.getPropertyValue(pageContext.getAttribute(bean),name);
				result=(obj==null?"":obj.toString());
			}
			
			StringBuilder sb = new StringBuilder("<textarea name=\""+name+"\"");
			prepareAttribute(sb,"rows",rows);
			prepareAttribute(sb,"cols",cols);
			
			sb.append(prepareOtherAttribute());
			sb.append(prepareStates());
			sb.append(prepareEventHandlers());	
			sb.append(">");

			if(!hasBeanValue()){
				sb.append((textareaContent == null) ? "" :textareaContent);
			}else{
				if(escape){
					result=HtmlUtil.escape(result);
				}
				if(filter){
					result=HtmlUtil.filter(result);
				}
				sb.append(result);
			}	
			sb.append("</textarea>");
			pageContext.getOut().print(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

}