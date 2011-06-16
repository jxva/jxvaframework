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
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-03-30 14:18:23 by Jxva
 */
public class Node {
	
	private String name;
	private Object value;
	
	public Node(String name,Object value){
		this.name=name;
		this.value=value;
	}
	
	public Object getValue(){
		return this.value;
	}
	
	public String getName(){
		return this.name;
	}

	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append('"').append(name).append('"').append(':');
		sb.append('"').append(value).append('"');
		return sb.toString();
	}
}
