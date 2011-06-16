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
package demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Snippet {
	public static String getStackTrace(Exception e) {
			String s = "";
			try {
				StringWriter stringwriter = new StringWriter();
				PrintWriter printwriter = new PrintWriter(stringwriter);
				e.printStackTrace(printwriter);
				printwriter.close();
				stringwriter.close();
				s = stringwriter.toString();
			}
			catch (Exception _ex) {
			}
			return s;
		}
	
	public static void main(String[] args){
		try{
			int i=4/0;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(getStackTrace(e));
		}
	}
}
