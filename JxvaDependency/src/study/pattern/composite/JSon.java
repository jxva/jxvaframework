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
package study.pattern.composite;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-30 14:26:24 by Jxva
 */
public class JSon{

	private StringBuilder data;
	
	public JSon(){
		this.data=new StringBuilder();
	}
	
	public JSon addChild(Node node) {
		data.append(',').append(node.toString());
		return this;
	}


	public JSon addAfter(Node node) {
		// TODO Auto-generated method stub
		return null;
	}


	public JSon addBefore(Node node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		return '{'+data.toString()+'}';
	}
}
