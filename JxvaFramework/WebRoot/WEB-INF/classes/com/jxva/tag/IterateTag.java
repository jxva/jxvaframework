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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * 循环遍历标签
 * @author  The Jxva Framework Foundation
 * @since 2006-12-22
 * <j:iterate name="collection">
 */
public class IterateTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 1L;
	public static final String INDEX_FLAG="#index";

	private String name=null;
	private Iterator<?> it;
	private String var=null;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	protected int index=0;
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void release() {
		super.release();
		name=null;
		var=null;
		index=0;
	}
	
	public int doStartTag() throws JspException {
		Object obj=MvelTagUtil.findValue(pageContext,name);
		if(obj==null){
			return SKIP_BODY; 
		}
		index=0;
		try {
			//直接采用Struts1.35里的代码
			//Collection col=(Collection)pageContext.getRequest().getAttribute(getName());
			//it=col.iterator();
			//Object obj=TagUtil.getScopeObject(pageContext, getName());
			
			 if (obj.getClass().isArray()) {
		            try {
		                // If we're lucky, it is an array of objects
		                // that we can iterate over with no copying
		                it= Arrays.asList((Object[]) obj).iterator();
		            } catch (ClassCastException e) {
		                int length = Array.getLength(obj);
		                ArrayList<Object> c = new ArrayList<Object>(length);

		                for (int i = 0; i < length; i++) {
		                    c.add(Array.get(obj, i));
		                }

		                it = c.iterator();
		            }
		        } else if (obj instanceof Collection<?>) {
		            it = ((Collection<?>) obj).iterator();
		        } else if (obj instanceof Iterator<?>) {
		            it = (Iterator<?>) obj;
		        } else if (obj instanceof Map<?,?>) {
		            it = ((Map<?,?>) obj).values().iterator();
		        } else if (obj instanceof Enumeration<?>) {
		            it = new IteratorAdapter((Enumeration<?>) obj);
		        } else {
		            it =null;
		        }
			 
			//先循环处理一次,如果不进行此步,会导致j:property出现异常
			if(it.hasNext()){
				pageContext.setAttribute(name,it.next());
				//index=0;
				//pageContext.setAttribute(var,name);
				pageContext.setAttribute(INDEX_FLAG, new Integer(index));
				return EVAL_BODY_INCLUDE;
			}else{
				return SKIP_BODY;//循环第一次时无数据,将直接退出
			}
		} catch (Exception e) {
			throw new JspException(e);
		}
	}
	
	public int doAfterBody() throws JspException {
		if(it.hasNext()){
			//传递参数给PropertyTag
			pageContext.setAttribute(name,it.next());
			index++;
			pageContext.setAttribute(INDEX_FLAG,new Integer(index));
			return EVAL_BODY_AGAIN;
		}else{
			return SKIP_BODY;
		}
	}
	
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(INDEX_FLAG);
		pageContext.removeAttribute(name);
		return EVAL_PAGE;
	}
}
