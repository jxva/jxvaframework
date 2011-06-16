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
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <p>A shared implementation of the nestable exception functionality.</p>
 * <p>
 * The code is shared between
 * {@link com.jxva.exception.NestableError NestableError},
 * {@link com.jxva.exception.NestableException NestableException} and
 * {@link com.jxva.exception.NestableRuntimeException NestableRuntimeException}.
 * </p>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:29:00 by Jxva
 */
public class NestableDelegate implements Serializable {

	private static final long serialVersionUID = -3166972550410041012L;
	private Throwable nestable;

	public static boolean topDown = true;
	public static boolean trimStackFrames = true;

	public NestableDelegate(Nestable nestable) {
		if ( nestable instanceof Throwable ) {
			this.nestable = ( Throwable ) nestable;
		}else {
			throw new IllegalArgumentException( "The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable" );
		}
	}

	public String getMessage(int index) {
		Throwable t = this.getThrowable( index );
		if ( Nestable.class.isInstance( t ) ) {
			return ( ( Nestable ) t ).getMessage( 0 );
		}
		else {
			return t.getMessage();
		}
	}

	public String getMessage(String baseMsg) {
		StringBuilder msg = new StringBuilder(512);
		if ( baseMsg != null ) {
			msg.append( baseMsg );
		}

		Throwable nestedCause = ExceptionUtil.getCause( this.nestable );
		if ( nestedCause != null ) {
			String causeMsg = nestedCause.getMessage();
			if ( causeMsg != null ) {
				if ( baseMsg != null ) {
					msg.append( ": " );
				}
				msg.append( causeMsg );
			}

		}
		return ( msg.length() > 0 ? msg.toString() : null );
	}

	public String[] getMessages() {
		Throwable[] throwables = this.getThrowables();
		String[] msgs = new String[throwables.length];
		for ( int i = 0; i < throwables.length; i++ ) {
			msgs[i] = Nestable.class.isInstance( throwables[i] ) ?
					( ( Nestable ) throwables[i] ).getMessage( 0 ) :
					throwables[i].getMessage();
		}
		return msgs;
	}

	public Throwable getThrowable(int index) {
		if ( index == 0 ) {
			return this.nestable;
		}
		Throwable[] throwables = this.getThrowables();
		return throwables[index];
	}

	public int getThrowableCount() {
		return ExceptionUtil.getThrowableCount( this.nestable );
	}

	public Throwable[] getThrowables() {
		return ExceptionUtil.getThrowables( this.nestable );
	}

	public int indexOfThrowable(Class<?> type, int fromIndex) {
		if ( fromIndex < 0 ) {
			throw new IndexOutOfBoundsException( "The start index was out of bounds: " + fromIndex );
		}
		Throwable[] throwables = ExceptionUtil.getThrowables( this.nestable );
		if ( fromIndex >= throwables.length ) {
			throw new IndexOutOfBoundsException( "The start index was out of bounds: "
					+ fromIndex + " >= " + throwables.length );
		}
		for ( int i = fromIndex; i < throwables.length; i++ ) {
			if ( throwables[i].getClass().equals( type ) ) {
				return i;
			}
		}
		return -1;
	}

	public void printStackTrace() {
		printStackTrace( System.err );
	}

	public void printStackTrace(PrintStream out) {
		synchronized ( out ) {
			PrintWriter pw = new PrintWriter( out, false );
			printStackTrace( pw );
			// Flush the PrintWriter before it's GC'ed.
			pw.flush();
		}
	}

	public void printStackTrace(PrintWriter out) {
		Throwable throwable = this.nestable;
		// if running on jre1.4 or higher, use default printStackTrace
		if ( ExceptionUtil.isThrowableNested() ) {
			if ( throwable instanceof Nestable ) {
				( ( Nestable ) throwable ).printPartialStackTrace( out );
			}
			else {
				throwable.printStackTrace( out );
			}
			return;
		}

		// generating the nested stack trace
		List<String[]> stacks = new ArrayList<String[]>();
		while ( throwable != null ) {
			String[] st = getStackFrames( throwable );
			stacks.add( st );
			throwable = ExceptionUtil.getCause( throwable );
		}

		// If NOT topDown, reverse the stack
		String separatorLine = "Caused by: ";
		if ( !topDown ) {
			separatorLine = "Rethrown as: ";
			Collections.reverse( stacks );
		}

		// Remove the repeated lines in the stack
		if ( trimStackFrames ) trimStackFrames( stacks );

		synchronized ( out ) {
			for ( Iterator<String[]> iter = stacks.iterator(); iter.hasNext(); ) {
				String[] st =iter.next();
				for ( int i = 0, len = st.length; i < len; i++ ) {
					out.println( st[i] );
				}
				if ( iter.hasNext() ) out.print( separatorLine );
			}
		}
	}

	protected String[] getStackFrames(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter( sw, true );

		// Avoid infinite loop between decompose() and printStackTrace().
		if ( t instanceof Nestable ) {
			( ( Nestable ) t ).printPartialStackTrace( pw );
		}
		else {
			t.printStackTrace( pw );
		}
		return ExceptionUtil.getStackFrames( sw.getBuffer().toString() );
	}

	protected void trimStackFrames(List<String[]> stacks) {
		for ( int size = stacks.size(), i = size - 1; i > 0; i-- ) {
			String[] curr = stacks.get( i );
			String[] next = stacks.get( i - 1 );

			List<String> currList = new ArrayList<String>(Arrays.asList( curr ));
			List<String> nextList = new ArrayList<String>( Arrays.asList( next ) );
			ExceptionUtil.removeCommonFrames( currList, nextList );

			int trimmed = curr.length - currList.size();
			if ( trimmed > 0 ) {
				currList.add( "\t... " + trimmed + " more" );
				stacks.set( i,currList.toArray( new String[currList.size()] ) );
			}
		}
	}
}
