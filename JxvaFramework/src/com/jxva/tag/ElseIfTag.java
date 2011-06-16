
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

public class ElseIfTag extends ConditionTag{
	
	private static final long serialVersionUID = 1L;
	
	public int doStart()throws JspException{
		try{
			Object conditionValue=pageContext.getAttribute(CONDITION_TAG_FLAG);
			if(conditionValue!=null){
				Object result=MvelTagUtil.findValueByIterateTag(this,pageContext,test);
				if((Boolean)result){//当elseif为true时,将不存在值
					pageContext.removeAttribute(CONDITION_TAG_FLAG);
					return EVAL_BODY_INCLUDE;
				}else{
					pageContext.setAttribute(CONDITION_TAG_FLAG,false);
					return SKIP_BODY;
				}
			}else{
				return SKIP_BODY;
			}
		}catch(Exception e){
			pageContext.removeAttribute(CONDITION_TAG_FLAG);
			throw new JspException(e);
		}
	}
}
