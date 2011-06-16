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
package com.jxva.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * The base class of all runtime exceptions which can contain other exceptions.
 * @see com.jxva.exception.NestableException
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:30:18 by Jxva
 */
public class NestableRuntimeException extends RuntimeException implements Nestable {

	private static final long serialVersionUID = 5885519978866255559L;

	protected NestableDelegate delegate = new NestableDelegate( this );

	private Throwable cause = null;

	public NestableRuntimeException() {
		super();
	}

	public NestableRuntimeException(String msg) {
		super( msg );
	}

	public NestableRuntimeException(Throwable cause) {
		super();
		this.cause = cause;
	}

	public NestableRuntimeException(String msg, Throwable cause) {
		super( msg );
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		if ( super.getMessage() != null ) {
			return super.getMessage();
		}else if ( cause != null ) {
			return cause.toString();
		}else {
			return null;
		}
	}

	public String getMessage(int index) {
		if ( index == 0 ) {
			return super.getMessage();
		}
		else {
			return delegate.getMessage( index );
		}
	}

	public String[] getMessages() {
		return delegate.getMessages();
	}

	public Throwable getThrowable(int index) {
		return delegate.getThrowable( index );
	}

	public int getThrowableCount() {
		return delegate.getThrowableCount();
	}

	public Throwable[] getThrowables() {
		return delegate.getThrowables();
	}

	public int indexOfThrowable(Class<?> type) {
		return delegate.indexOfThrowable( type, 0 );
	}

	public int indexOfThrowable(Class<?> type, int fromIndex) {
		return delegate.indexOfThrowable( type, fromIndex );
	}

	public void printStackTrace() {
		delegate.printStackTrace();
	}

	public void printStackTrace(PrintStream out) {
		delegate.printStackTrace( out );
	}

	public void printStackTrace(PrintWriter out) {
		delegate.printStackTrace( out );
	}

	public final void printPartialStackTrace(PrintWriter out) {
		super.printStackTrace( out );
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		Throwable tempCause = cause;
		oos.defaultWriteObject();
		cause = tempCause;
	}

}
