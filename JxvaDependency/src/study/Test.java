/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
package study;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-26 17:00:45 by Jxva
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new java.sql.Date(System.currentTimeMillis()));
		System.out.println(new Timestamp(System.currentTimeMillis()));
		System.out.println(new java.sql.Date(new Date().getTime()));
	}

}
