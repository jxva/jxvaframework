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

package com.jxva.entity;

/**
 * Generates increasing identifiers (in a single VM only).
 * Not valid across multiple VMs. Identifiers are not necessarily
 * strictly increasing, but usually are.
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-02-09 16:13:50 by Jxva
 */
public final class Timestamper {
	
	private static short counter = 0;
	private static long time;
	private static final int BIN_DIGITS = 12;
	public static final short ONE_MS = 1<<BIN_DIGITS;
	
	private Timestamper() {}
	
	public static long next() {
		synchronized(Timestamper.class) {
			return next( System.currentTimeMillis()<< BIN_DIGITS);
		}
	}
	
	public static long nano() {
		synchronized(Timestamper.class) {
			return next( System.nanoTime()<< BIN_DIGITS);
		}
	}
	
	private static long next(long newTime){
		if (time<newTime) {
			time = newTime;
			counter = 0;
		}else if (counter < ONE_MS - 1 ) {
			counter++;
		}
		return time + counter;
	}
}