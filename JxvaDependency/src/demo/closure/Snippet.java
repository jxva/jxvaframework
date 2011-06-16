/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package demo.closure;

/**
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 09:49:09 by Jxva 
 */
public class Snippet {
	/**
	 * 
	 */
	public static void main(String[] args){
		      Thread thread = new Thread(new Runnable(){ 
		           public void run(){
		                // 除 0 的错误
		                int n = 1/0;
		            } 
		       }, "testThread"); 
		      thread.start();
	}
}