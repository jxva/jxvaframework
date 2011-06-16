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
package com.jxva.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

import com.jxva.entity.Encoding;
import com.jxva.util.Assert;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-01 14:13:14 by Jxva
 */
public class HttpTransfer {
		
	private String url;	
	//private String authorization;
		
	public HttpTransfer(String url){
		this.url=url;
	}
	
	public void setProxy(String proxyHost,String proxyPort){
		Properties prop = System.getProperties();
		prop.setProperty("proxySet","true");
		prop.setProperty("http.proxyHost",proxyHost);
		prop.setProperty("http.proxyPort",proxyPort);
	}
	
	public void setProxyAuthorization(final String username,final String password){
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,new String(password).toCharArray());
			}
		});
		//String authentication = username+':'+password;
		//this.authorization=" Basic " + new BASE64Encoder().encode(authentication.getBytes());
	}
	
	
	public <T> T execute(HttpURLConnectionCallback<T> action) throws HttpException {
		Assert.notNull(action, "HttpURLConnectionCallback object must not be null");
		try {
			return action.doInConnection((HttpURLConnection) new URL(url).openConnection());
		} catch (MalformedURLException e) {
			throw new HttpException(url,e);
		} catch (IOException e) {
			throw new HttpException(url,e);
		}
	}
	
	public String post(final String postParam,final String encoding){
		return execute(new HttpURLConnectionCallback<String>(){
			public String doInConnection(HttpURLConnection conn)throws HttpException {
				try {
					//if(authorization!=null)conn.setRequestProperty("Proxy-Authorization",authorization);
					conn.setRequestMethod("POST");
		            conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
		            conn.setRequestProperty("Accept-Charset", "UTF-8");
		            conn.setDoOutput(true);
		            conn.setDoInput(true);
		            conn.connect();

		            PrintWriter out = new PrintWriter(conn.getOutputStream());//发送数据
		            out.print(postParam);
		            out.flush();
		            out.close();
		             
//		            String header;
//		            for(int i=0;true;i++){
//		                header=conn.getHeaderField(i);
//		                if(header==null)break;
//		                System.out.println("header:"+header);
//		                System.out.println(conn.getContentType());
//		            }
		            
		            int res =conn.getResponseCode();
		            if (res == 200){
			              BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
			              String line = null;
			              StringBuilder sb = new StringBuilder();
			              while ((line = reader.readLine()) != null){
			            	  sb.append(line).append('\n');
			              }
			              return sb.toString();
		             }else{
		            	 throw new HttpException("accept data found error from "+url);
		             }
				} catch (IOException e) {
					throw new HttpException(e);
				}finally{
					conn.disconnect();
				}	
			}
		});
	}

	public String get(final String encoding) {
		return execute(new HttpURLConnectionCallback<String>(){
			public String doInConnection(HttpURLConnection conn)throws HttpException {
				BufferedReader reader=null;
				try {
					//if(authorization!=null)conn.setRequestProperty("Proxy-Authorization",authorization);
					conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
					conn.setRequestProperty("Accept-Charset", "UTF-8");
					conn.setDoInput(true);
				    conn.setDoOutput(true);
				    conn.setRequestMethod("GET");
				    conn.setUseCaches(false);
					reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
					String line = null;
					StringBuilder sb = new StringBuilder();
					while ((line = reader.readLine()) != null){
						sb.append(line).append('\n');
					}
					return sb.toString();
				} catch (IOException e) {
					throw new HttpException(e);
				}finally{
					if(reader!=null)try {reader.close();} catch (IOException e) {}
					conn.disconnect();
				}	
			}
		});
	}
	
	public boolean saveAs(final String filename) {
		Assert.notNull(filename, "filename must not be null");
		return execute(new HttpURLConnectionCallback<Boolean>(){
			public Boolean doInConnection(HttpURLConnection conn)throws HttpException {
				//if(authorization!=null)conn.setRequestProperty("Proxy-Authorization",authorization);
				FileOutputStream fos = null;
				BufferedInputStream bis = null;
				try {
					bis = new BufferedInputStream(conn.getInputStream());
					fos = new FileOutputStream(filename);
					
					byte[] buf = new byte[4096];
					int size = 0;
					while ((size = bis.read(buf)) != -1) {
						fos.write(buf, 0, size);
					}
					return true;
				} catch (IOException e) {
					throw new HttpException(e);
				}finally{
					if(fos!=null)try {fos.flush();fos.close();} catch (IOException e) {}
					if(bis!=null)try {bis.close();} catch (IOException e) {}
					conn.disconnect();
				}	
			}
		});
	}
}
