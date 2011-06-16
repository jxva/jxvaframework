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

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-07 23:27:22 by Jxva
 */
public class Dispatch {
	
	public static final int FORWARD=0;
	public static final int REDIRECT=1;
	public static final int OUTPRINT=2;
	public static final int PERFORM=3;
	
	private int flag;
	private String value;
	
	public Dispatch forward(String value){
		this.value=value;
		this.flag=FORWARD;
		return this;
	}
	
	public Dispatch redirect(String value){
		this.value=value;
		this.flag=REDIRECT;
		return this;
	}
	
	public Dispatch outprint(String value){
		this.value=value;
		this.flag=OUTPRINT;
		return this;
	}
	
	public Dispatch perform(String value){
		this.value=value;
		this.flag=PERFORM;
		return this;
	}

	public String getValue() {
		return value;
	}
	
	public int getFlag(){
		return flag;
	}
}
