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
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-27 10:43:56 by Jxva
 */
public abstract class ConditionTag  extends TagSupport{
	
	private static final long serialVersionUID = 1L;
	public static final String CONDITION_TAG_FLAG="condition_tag_flag_for_jxva_framework";

	protected String test;

	public void setTest(String test) {
		this.test = test;
	}
		
	public int doStartTag() throws JspException{
		return doStart();
	}
	
	public abstract int doStart()throws JspException;
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void release() {
		super.release();
		test = null;
	}
}
