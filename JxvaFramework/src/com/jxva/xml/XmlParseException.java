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

import com.jxva.exception.NestableException;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:18:18 by Jxva
 */
public class XmlParseException extends NestableException  {
	
	private static final long serialVersionUID = -5742090778687090991L;
	
	private Throwable detail;
	private int row = -1;
	private int column = -1;
	
	public XmlParseException() {
		super();
	}

    public XmlParseException(String s) {
        super(s);
    }
    
    public XmlParseException(Throwable root) {
		super(root);
	}

	public XmlParseException(String string, Throwable root) {
		super(string, root);
	}

    public XmlParseException(String msg, XmlParser parser, Throwable chain) {
        super ((msg == null ? "" : msg+" ")
               + (parser == null ? "" : "(position:"+parser.getPositionDescription()+") ")
               + (chain == null ? "" : "caused by: "+chain));

        if (parser != null) {
            this.row = parser.getLineNumber();
            this.column = parser.getColumnNumber();
        }
        this.detail = chain;
    }

    public Throwable getDetail() { return detail; }

    public int getLineNumber() { return row; }
    public int getColumnNumber() { return column; }

    public void printStackTrace() {
        if (detail == null) {
            super.printStackTrace();
        } else {
            synchronized(System.err) {
                System.err.println(super.getMessage() + "; nested exception is:");
                detail.printStackTrace();
            }
        }
    }
}

