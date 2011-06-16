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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.jxva.util.DateUtil;
import com.jxva.util.ObjectUtil;
import com.jxva.util.StringUtil;

/**
 * 
 *  <j:date value="value" format=""/>
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-03 22:19:45 by Jxva
 */
public class DateTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected String value;
	protected String format;
	
	public void setValue(String value) {
		this.value = value;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int doStartTag() throws JspException {
		try{
			if(StringUtil.isEmpty(value)){
				pageContext.getOut().print("");
			}else{
				Object result=MvelTagUtil.findValueByIterateTag(this,pageContext,value);
				if(ObjectUtil.isEmpty(result)){
					pageContext.getOut().print("");
				}else{
					DateFormat dateFormat=(format==null?DateUtil.DATETIME_YMDHMS:new SimpleDateFormat(format));
					pageContext.getOut().print(dateFormat.format((Date)result));
				}
			}
			return EVAL_BODY_INCLUDE;
		}catch(IOException e){
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
