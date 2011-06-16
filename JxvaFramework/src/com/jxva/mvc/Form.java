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
package com.jxva.mvc;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jxva.dao.type.JavaType;
import com.jxva.dao.type.Types;
import com.jxva.util.DateUtil;
import com.jxva.util.FieldUtil;
import com.jxva.util.ModelUtil;
import com.jxva.util.NumberUtil;
import com.jxva.util.StringUtil;

/**
 * 接收表单数据的提交,自动转换为所需要的bean对象或相关格式
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:03:27 by Jxva
 */
public class Form {
	
	private final HttpServletRequest request;
		
	public Form(HttpServletRequest request){
		this.request=request;
	}
	/**
	 * 将表单数据直接转换为bean对象
	 * Usage:<br>
	 * 		User user=Form.form2Bean(User.class,request);
	 * @param beanClass class对象
	 * @return bean对象
	 */
	public <T> T form2Bean(final Class<T> beanClass)throws MvcException{
		if (request == null)return null;
		T bean=null;
		try {
			bean = beanClass.newInstance();
		} catch (InstantiationException e) {
			throw new MvcException(e);
		} catch (IllegalAccessException e) {
			throw new MvcException(e);
		}
		
		final List<Field> fields = FieldUtil.getFields(beanClass);
		for (Field field:fields) {
			final String fieldName =field.getName();
			try{
				final String value = getParam(fieldName);
				final String setterFieldName=ModelUtil.getSetterName(fieldName);
				final Class<?> fieldType =field.getType();
				if (value == null||(value.trim().equals("")&&!fieldType.equals(java.lang.String.class))) {
					continue;
				}
				int type=JavaType.get(fieldType);
				switch(type){
					case Types.VARCHAR:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.String.class).invoke(bean,value);
						break;
					case Types.INTEGER:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.Integer.class).invoke(bean,Integer.valueOf(value));
						break;
					case Types.TIMESTAMP:
						beanClass.getDeclaredMethod(setterFieldName,java.sql.Timestamp.class).invoke(bean,Timestamp.valueOf(value));
						break;
					case Types.SMALLINT:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.Short.class).invoke(bean, Short.valueOf(value));
						break;
					case Types.BIGINT:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.Long.class).invoke(bean,Long.valueOf(value));
						break;
					case Types.DATE:
						beanClass.getDeclaredMethod(setterFieldName,java.sql.Date.class).invoke(bean,Date.valueOf(value));
						break;
					case Types.DOUBLE:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.Double.class).invoke(bean,Double.valueOf(value));
						break;
					case Types.TIME:
						beanClass.getDeclaredMethod(setterFieldName,java.sql.Time.class).invoke(bean,Time.valueOf(value));
						break;
					case Types.CHAR:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.Character.class).invoke(bean,Character.valueOf(value.charAt(0)));
						break;
					case Types.DECIMAL:
						beanClass.getDeclaredMethod(setterFieldName,java.math.BigDecimal.class).invoke(bean,new java.math.BigDecimal(value));
						break;
					case Types.DATETIME:
						beanClass.getDeclaredMethod(setterFieldName,java.util.Date.class).invoke(bean,DateUtil.parse(value));
						break;
					case Types.BOOLEAN:
						beanClass.getDeclaredMethod(setterFieldName,java.lang.Boolean.class).invoke(bean,Boolean.valueOf(value));
					default:
						throw new MvcException("don't support type:"+fieldType);						
				}
			}catch(Exception e){
				throw new MvcException("fieldName '"+fieldName+"' type is incompatible",e);
			}
		}				
		return bean;
	}
		
	/**
	 * 相当于 request.getParameter(param)
	 * @param param
	 * @return request.getParameter(param)的值
	 */
	public String getParam(final String param){
		return request.getParameter(param);
	}
	
	/**
	 * 
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public String getParam(final String param,final String defaultValue){
		String result=getParam(param);
		return StringUtil.isEmpty(result)?defaultValue:result;
	}
	
	/**
	 * 相当于Integer.valueOf(request.getParameter(param))
	 * @param param
	 * @return Integer.valueOf(request.getParameter(param))的整型值
	 */
	public int getIntParam(final String param){
		return getIntParam(param,0);
	}
	
	/**
	 * 相当于Integer.valueOf(request.getParameter(param))<br/>
	 *  1. 当参数为null或""时,将返回默认值<br/>
	 *  2. 当参数为非整型时,将抛出异常,由控制中心捕获返回给视图层
	 * @param param
	 * @param defaultValue
	 * @return param的整型的值或defaultValue
	 */
	public int getIntParam(final String param,final int defaultValue){
		final String s=getParam(param);
		return NumberUtil.isNumeric(s)?Integer.parseInt(s):defaultValue;
	}
	

	/**
	 * 
	 * 将获取的数组值转换为字符串<br/>
	 * @param values
	 * @return 以","逗号分隔的字符串
	 */
	public String toString(final Object[] values){
		final StringBuilder sb=new StringBuilder();
		if(values!=null){
			for(int k=0;k<values.length;k++){
				sb.append(',').append(values[k]);
			}
			return sb.substring(1);
		}
		return sb.toString();
	}
	
	public String[] getArrayParam(final String param){
		return request.getParameterValues(param);
	}
	
	public int[] getIntArrayParam(final String param){
		final String[] values=getArrayParam(param);
		final int[] intArray=new int[values.length];
		if(values!=null){
			for(int k=0;k<values.length;k++){
				intArray[k]=Integer.parseInt(values[k]);
			}
		}
		return intArray;
	}
		
	/**
	 * 类似于ruby的多参数获取
	 * @param params
	 * @return 返回数组值
	 */
	public String[] getParams(final String...params){
		final String[] s=new String[params.length];
		for(int i=0;i<params.length;i++){
			s[i]=getParam(params[i]);
		}
		return s;
	}
	
	public String getAppUrl(){
		return request.getRequestURL().toString().replace(request.getServletPath(),"/");
	}
}
