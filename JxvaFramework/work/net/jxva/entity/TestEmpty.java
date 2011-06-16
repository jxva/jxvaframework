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
package net.jxva.entity;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-15 11:41:55 by Jxva
 */
public class TestEmpty {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s=" ";
		System.out.println(s.length());
		System.out.println(t());
	}
	
	public static String t(){
		try{
			System.out.println("try");
			System.out.println("try1");
			System.out.println("try2");
			System.out.println("try3");
			return "return";
		}finally{
			System.out.println("finally");
		}
	}

}
