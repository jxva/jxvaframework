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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jxva.entity.Encoding;
import com.jxva.http.HttpTransfer;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-09 10:01:57 by Jxva
 */
public class Test {
	private static final Pattern PATTERN_CONTEXT = Pattern.compile("(#(parameter|pageContext|request|session|application|attr)((.[a-zA-Z0-9_]+)|(\\[\\'[a-zA-Z0-9_]+\\'\\])))");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String s="#request['ok']";
		//final String s="#request.user.email+#request['ok']+#session.user.info+#parameter.test+#parameter['info']";
		final String s="#request.video.title";
		System.out.println(s);
		System.out.println(s.length());
		String r="request.attribute['user'].email+request.attribute['ok']+session.attribute['user'].info+request.getParameter('test')+request.getParameter('info')";
		System.out.println(r);
		System.out.println(r.length());
		System.out.println(compile2(s));
		//multi();
		final HttpTransfer http=new HttpTransfer("http://127.0.0.1:8080/video/welcome.jv");
		ExecutorService pool = Executors.newFixedThreadPool(100);
		for (int i = 0; i <100; i++) {
			pool.execute(new Runnable() {
				public void run() {
					//System.out.println(Thread.currentThread().getName());
					//System.out.println(compile2(s));
					http.get(Encoding.UTF_8);
				}
			});
		}
		pool.shutdown();
	}
	
	private static String compile2(String s){
		final Matcher matcher = PATTERN_CONTEXT.matcher(s);  
		StringBuffer sb = new StringBuffer(s.length()*2);
		while (matcher.find()) {
			final String result=matcher.group(0);
	    	int dotPos=result.indexOf('.');
	    	String tmp=null;
	    	if(dotPos>-1){
		    	String prefix=result.substring(1,dotPos);
		    	//if(prefix.equals("attr")){
		    	//	s=s.replace(result,"pageContext.attribute['"+result.substring(dotPos+1)+"']");
		    	if(prefix.equals("parameter")){
		    		tmp="request.getParameter('"+result.substring(dotPos+1)+"')";
		    	}else{
		    		tmp=prefix+".attribute['"+result.substring(dotPos+1)+"']";
		    	}
	    	}else{
	    		int bracketPos=result.indexOf('[');
	    		String prefix=result.substring(1,bracketPos);
	    		if(prefix.equals("parameter")){
	    			tmp="request.getParameter('"+result.substring(bracketPos+2,result.lastIndexOf('\''))+"')";
	    		}else{
	    			tmp=prefix+".attribute"+result.substring(bracketPos);
	    		}
	    	}
	    	matcher.appendReplacement(sb,tmp);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static String compile(String s){
		final Matcher matcher = PATTERN_CONTEXT.matcher(s);  
	    while (matcher.find()) {  
	    	final String result=matcher.group(0);
	    	int dotPos=result.indexOf('.');
	    	if(dotPos>-1){
		    	String prefix=result.substring(1,dotPos);
		    	//if(prefix.equals("attr")){
		    	//	s=s.replace(result,"pageContext.attribute['"+result.substring(dotPos+1)+"']");
		    	if(prefix.equals("parameter")){
		    		s=s.replace(result,"request.getParameter('"+result.substring(dotPos+1)+"')");
		    	}else{
		    		s=s.replace(result,prefix+".attribute['"+result.substring(dotPos+1)+"']");
		    	}
	    	}else{
	    		int bracketPos=result.indexOf('[');
	    		String prefix=result.substring(1,bracketPos);
	    		if(prefix.equals("parameter")){
	    			s=s.replace(result,"request.getParameter('"+result.substring(bracketPos+2,result.lastIndexOf('\''))+"')");
	    		}else{
	    			s=s.replace('#'+prefix,prefix+".attribute");
	    		}
	    	}
		}  
		return s;
	}
	
	public static void multi(){
		final HttpTransfer http=new HttpTransfer("http://127.0.0.1:8080/video/welcome.jv");
		for(int i=0;i<100;i++){
			new Thread(new Runnable(){

				@Override
				public void run() {
					http.get(Encoding.UTF_8);
				}
				
			}).start();
		}
	}
}
