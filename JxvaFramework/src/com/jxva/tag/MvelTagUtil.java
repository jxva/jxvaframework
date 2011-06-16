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

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.mvel2.MVEL;
import org.mvel2.PropertyAccessException;
import org.mvel2.optimizers.OptimizerFactory;

import com.jxva.mvc.ActionContext;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-29 15:10:57 by Jxva
 */
public abstract class MvelTagUtil {
	
	private static final ConcurrentMap<String,Serializable> expressions=new ConcurrentHashMap<String,Serializable>(); 
	
	static{
		OptimizerFactory.setDefaultOptimizer(OptimizerFactory.SAFE_REFLECTIVE);
	}
		
	public static Serializable compile(String expression){
		synchronized(expressions){
			Serializable o = expressions.get(expression);
	        if (o == null) {
				final Matcher matcher = TagUtil.PATTERN_CONTEXT.matcher(expression);  
				StringBuffer sb = new StringBuffer(expression.length()*2);
				while (matcher.find()) {
					final String result=matcher.group(0);
					int dotPos=result.indexOf('.');
					String tmp=null;
					if(dotPos>-1){
				    	String prefix=result.substring(1,dotPos);
				    	//if(prefix.equals("attr")){
				    	//	s=s.replace(result,"pageContext.attribute['"+result.substring(dotPos+1)+"']");
				    	if(prefix.equals("parameter")){
				    		tmp="request.getParameter('"+result.substring(dotPos+1)+"')";
				    	}else{
				    		tmp=prefix+".getAttribute('"+result.substring(dotPos+1)+"')";
				    	}
					}else{
						int bracketPos=result.indexOf('[');
						String prefix=result.substring(1,bracketPos);
						if(prefix.equals("parameter")){
							tmp="request.getParameter('"+result.substring(bracketPos+2,result.lastIndexOf('\''))+"')";
						}else{
							tmp=prefix+".getAttribute("+result.substring(bracketPos+1,result.length()-1)+')';
						}
					}
					matcher.appendReplacement(sb,tmp);
				}
				matcher.appendTail(sb);
				o=MVEL.compileExpression(sb.toString());
				expressions.put(expression,o);
	        }
	        //System.out.println("SSSSSSSS:"+o);
	        return o;
		}
	}
		
	public static Object findValue(PageContext pageContext,String expression) throws JspException{	
		try {
			final Object result=pageContext.getAttribute(expression);
			return (result==null)?MVEL.executeExpression(compile(expression),ActionContext.getTagContext()):result;
        } catch(PropertyAccessException e) {
        	expressions.remove(expression);
            throw new JspException(String.format("Could not read property from expression %s (missing a getter?)", expression), e);
        } catch(NullPointerException npe) {
            throw new JspException(String.format("Evaluation of property expression [%s] resulted in a NullPointerException", expression), npe);
        }
	}
	
	public static Object findValueByIterateTag(Tag tag,PageContext pageContext,String value) throws JspException{
		final IterateTag iterateTag =  (IterateTag)TagSupport.findAncestorWithClass(tag,IterateTag.class);
		if(iterateTag!=null){
			if(value.startsWith("::")){
				value=value.substring(2);
			}else{
				final String iterateVar=iterateTag.getVar()+'.';
				final Object index=pageContext.getAttribute(IterateTag.INDEX_FLAG);
				if(value.indexOf(iterateVar)>-1){
					value=value.replaceAll(iterateVar,iterateTag.getName()+'['+index+']');
				}else{
					if(value.indexOf(IterateTag.INDEX_FLAG)>-1){
						value=value.replaceAll(IterateTag.INDEX_FLAG,index.toString());
					}else{
						return MVEL.eval(value,pageContext.getAttribute(iterateTag.getName()));
					}
				}
			}
		}
		return findValue(pageContext,value);
	}
}