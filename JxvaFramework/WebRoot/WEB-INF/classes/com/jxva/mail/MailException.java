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
package com.jxva.mail;

import com.jxva.exception.NestableRuntimeException;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-20 11:37:50 by Jxva
 */
public class MailException extends NestableRuntimeException{

	private static final long serialVersionUID = 417852335206307591L;
	
	public MailException(){
		super();
	}
	
	public MailException(Throwable root) {
		super(root);
	}

	public MailException(String string, Throwable root) {
		super(string, root);
	}

	public MailException(String s) {
		super(s);
	}


}
