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
package com.jxva.i18n;

import com.jxva.exception.NestableRuntimeException;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-18 09:07:18 by Jxva
 */
public class I18NException extends NestableRuntimeException {

	private static final long serialVersionUID = -5356341184035030569L;

	public I18NException(){
		super();
	}
	
	public I18NException(Throwable root) {
		super(root);
	}

	public I18NException(String string, Throwable root) {
		super(string, root);
	}

	public I18NException(String s) {
		super(s);
	}

}
