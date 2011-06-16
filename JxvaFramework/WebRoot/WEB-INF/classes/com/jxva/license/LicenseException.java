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
package com.jxva.license;

import com.jxva.exception.NestableRuntimeException;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-28 16:51:17 by Jxva
 */
public class LicenseException extends NestableRuntimeException{

	private static final long serialVersionUID = 417852335206307591L;
	
	public LicenseException(){
		super(LicenseConst.UNKNOWN_LICENSE_EDITION);
	}
	
	public LicenseException(Throwable root) {
		super(root);
	}

	public LicenseException(String string, Throwable root) {
		super(string, root);
	}

	public LicenseException(String s) {
		super(s);
	}

}