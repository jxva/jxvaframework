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
package org.jxva.mvc.entity;

import com.jxva.mvc.Action;
import com.jxva.mvc.Forward;
import com.jxva.mvc.Redirect;

/**
 * only for test
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:03:10 by Jxva
 */
public class DemoHandler extends Action{

	@Redirect("/index.jsp")
	public String execute() {
		return null;
	}
	
	@Forward("ddd")
	public void doAddInfo(){

	}
	
	public void doAjax(){

	}
	
	public static void main(String args[]) throws Exception{
		Object[] objs=DemoHandler.class.getDeclaredMethod("doAddInfo").getDeclaredAnnotations();
		if(objs.length>0){
			Forward d=(Forward)objs[0];
			System.out.println(d.value());
		}
	}
}
