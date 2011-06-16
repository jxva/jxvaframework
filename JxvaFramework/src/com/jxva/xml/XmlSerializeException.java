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
package com.jxva.xml;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-23 21:25:53 by Jxva
 */
public class XmlSerializeException extends XmlParseException  {
	
	private static final long serialVersionUID = -5742090778687090991L;
	
	public XmlSerializeException() {
		super();
	}

    public XmlSerializeException(String s) {
        super(s);
    }
    
    public XmlSerializeException(Throwable root) {
		super(root);
	}

	public XmlSerializeException(String string, Throwable root) {
		super(string, root);
	}

}
