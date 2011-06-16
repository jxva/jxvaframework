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

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-08 09:52:14 by Jxva
 */
public abstract class DispatchTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	protected String value;
	

	public void setValue(String value) {
		this.value = value;
	}
	
	public abstract void dispatch()throws IOException,ServletException;

	public int doStartTag() throws JspException {
		try {
			dispatch();
			return EVAL_BODY_INCLUDE;
		} catch (IOException e) {
			throw new JspException(e);
		} catch (ServletException e) {
			throw new JspException(e);
		}
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void release() {
		super.release();
		value = null;
	}
}
