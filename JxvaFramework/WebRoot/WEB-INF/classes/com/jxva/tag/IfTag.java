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

/**
 *  	<%
		request.setAttribute("a",34);
		request.setAttribute("b",9);
	%>
	<j:if test="param.c!=null">
		hello china <j:property value="param.c"/>
	</j:if>
	<j:if test="a-b<0">
		Ok1
	</j:if>
	<j:elseif test="a==b">
		Ok2
	</j:elseif>
	<j:elseif test="a-b==25">
		Ok3
	</j:elseif>
	<j:else>
		Error
	</j:else>
	
	<j:set name="name" value="fff" />
	<j:if test="name == 'Max'">
          Max's file here
    </j:if>
	<j:elseif test="name == 'Scott'">
            Scott's file here
    </j:elseif>
    <j:elseif test="name == 'fff'">
            Scott's file here
    </j:elseif>
	<j:else>
            Other's file here
    </j:else>
    
    
   <tag>
	    <name>if</name>
	    <tag-class>com.jxva.tag.IfTag</tag-class>
	    <body-content>JSP</body-content>
	    <attribute>
	        <name>test</name>
	        <required>true</required>
	        <rtexprvalue>false</rtexprvalue>
	    </attribute>
  	</tag>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-16 17:44:12 by Jxva
 */
public class IfTag extends ConditionTag{

	private static final long serialVersionUID = 1L;

	public int doStart()throws JspException{
		Object result=MvelTagUtil.findValueByIterateTag(this,pageContext,test);
		pageContext.removeAttribute(CONDITION_TAG_FLAG);
		if((Boolean)result){//当if为true时,将不存在值
			return EVAL_BODY_INCLUDE;
		}else{
			pageContext.setAttribute(CONDITION_TAG_FLAG,false);
			return SKIP_BODY;
		}
	}
}
