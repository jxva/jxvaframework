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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.jxva.util.ObjectUtil;
import com.jxva.util.StringUtil;

/**
 * <j:time value="" format=""/>
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-06-21 22:26:25 by Jxva
 */
public class TimeTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected String value;

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		try{
			if(StringUtil.isEmpty(value)){
				pageContext.getOut().print("0 秒");
			}else{
				Object result=MvelTagUtil.findValueByIterateTag(this,pageContext,value);
				if(ObjectUtil.isEmpty(result)){
					pageContext.getOut().print("0 秒");
				}else{
					Long time=(Long)result;
					StringBuilder sb=new StringBuilder();
					count(time,sb);
					pageContext.getOut().print(sb.toString());
				}
			}
			return EVAL_BODY_INCLUDE;
		}catch(IOException e){
			throw new JspException(e);
		}
	}
	
	private static void count(long time,StringBuilder sb){
		long s=time;
		if(time>(60*60*24)){
			s=time%(60*60*24);
			sb.append(time/(60*60*24)).append("天");
		}else if(time>(60*60)){
			s=time%(60*60);
			sb.append(time/(60*60)).append("时");
		}else if(time>60){
			s=time%60;
			sb.append(time/60).append("分");
		}else{
			sb.append(time).append("秒");
			return;
		}
		count(s,sb);
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
