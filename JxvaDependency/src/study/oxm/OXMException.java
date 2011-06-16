
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
 *
 */
package study.oxm;

public class OXMException extends Exception {
	
	private static final long serialVersionUID = -3782741055599886856L;

	public OXMException() {
    	super();
    }


    public OXMException(String s) {
    	super(s);
    }
	
	/**
	 * 
	 * @param ex
	 * @return
	 */
	public static void getStackTrace(Exception e){
		StackTraceElement[] errors=e.getStackTrace();
		StringBuilder sb=new StringBuilder();
		sb.append(e).append("\r\n");
		for(int i=0;i<errors.length;i++){
			sb.append("\tat ").append(errors[i]).append("\r\n");
		}
		System.out.println(sb);
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args){
		try{
			throw new Exception("汶川地震!!!");
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
	}
}
