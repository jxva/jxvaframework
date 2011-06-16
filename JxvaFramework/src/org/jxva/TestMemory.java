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
package org.jxva;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-31 18:11:04 by Jxva
 */
public class TestMemory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().freeMemory()/(1024L*1024L));
		System.out.println(System.getProperty("sun.reflect.noCaches"));
		System.out.println(System.currentTimeMillis()/1000);
		
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String todayTime=sDateFormat.format(System.currentTimeMillis()/1000);
		System.out.println("todayTime:"+todayTime);
		
		Calendar c = Calendar.getInstance();
		long l = c.getTimeInMillis(); 
		System.out.println(l);
	}
}
