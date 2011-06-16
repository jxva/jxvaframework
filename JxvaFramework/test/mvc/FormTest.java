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
package mvc;

import java.sql.Timestamp;

import mvc.provide.BeanProvider;

import com.jxva.mvc.Form;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-01 18:09:10 by Jxva
 */
public class FormTest extends BaseServletWarpper{

	private Form form;
	
	public void setUp() {
		super.setUp();
		form=new Form(request);
		request.setParameter("userId","22");
		request.setParameter("username","china");
		request.setParameter("birthday","2009-02-22 12:12:12");
	}
	
	/**
	 * Test method for {@link com.jxva.mvc.Form#Form(javax.servlet.http.HttpServletRequest)}.
	 */
	public void testForm() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link com.jxva.mvc.Form#form2Bean(java.lang.Class)}.
	 */
	public void testForm2Bean() {
		BeanProvider bp=form.form2Bean(BeanProvider.class);
		assertTrue(bp.getUserId()==22);
		assertEquals(bp.getUsername(),"china");
		assertEquals(bp.getBirthday(),Timestamp.valueOf("2009-02-22 12:12:12"));
	}

	/**
	 * Test method for {@link com.jxva.mvc.Form#getParam(java.lang.String)}.
	 */
	public void testGetParam() {
		assertEquals(form.getParam("userId"),"22");
		assertEquals(form.getParam("username"),"china");
		assertEquals(form.getParam("birthday"),"2009-02-22 12:12:12");
	}

	/**
	 * Test method for {@link com.jxva.mvc.Form#getIntParam(java.lang.String)}.
	 */
	public void testGetIntParamString() {
		assertTrue(form.getIntParam("userId")==22);
		
		request.setParameter("userId","notisint");
		assertTrue(form.getIntParam("userId")==0);
	}

	/**
	 * Test method for {@link com.jxva.mvc.Form#getIntParam(java.lang.String, int)}.
	 */
	public void testGetIntParamStringInt() {
		assertTrue(form.getIntParam("userId",0)==22);
		
		request.setParameter("userId","notisint");
		assertTrue(form.getIntParam(null,0)==0);
		assertTrue(form.getIntParam("",0)==0);
		assertTrue(form.getIntParam("notexistparam",0)==0);
		assertTrue(form.getIntParam("userId",0)==0);
		assertTrue(form.getIntParam("userId",22)==22);
	}

	/**
	 * Test method for {@link com.jxva.mvc.Form#getArrayParam(java.lang.String)}.
	 */
	public void testGetArrayParam() {
		request.setParameterValues("userIds","1");
		String[] value1=form.getArrayParam("userIds");
		assertEquals(value1,"1");
		request.setParameterValues("userIds","1,3,6,7,14,27");
		String[] value2=form.getArrayParam("userIds");
		assertEquals(value2,"1,3,6,7,14,27");
	}

	/**
	 * Test method for {@link com.jxva.mvc.Form#getParams(java.lang.String[])}.
	 */
	public void testGetParams() {
		String[] values1=form.getParams("userId");
		assertTrue(Integer.valueOf(values1[0])==22);
		
		String[] values2=form.getParams("username");
		assertEquals(values2[0],"china");
		
		String[] values=form.getParams("userId","username","birthday");
		assertTrue(Integer.valueOf(values[0])==22);
		assertEquals(values[1],"china");
		assertEquals(values[2],"2009-02-22 12:12:12");
	}

}
