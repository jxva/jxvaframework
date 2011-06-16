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
package util;

import com.jxva.util.StringUtil;
import com.jxva.util.UrlUtil;

import junit.framework.TestCase;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-08 11:46:16 by Jxva
 */
public class UrlUtilTest extends TestCase{
	
	private String[] urls1;
	private String[] urls2;
	private String[] urls3;
	
	public void setUp(){
		urls1=new String[12];
		urls1[0]="http://localhost";
		urls1[1]="https://localhost/test";
		urls1[2]="https://localhost/test/test.zte";
		urls1[3]="http://localhost/?method=login&info=fdsa";
		urls1[4]="https://localhost/test/test.zte?method=login";
		urls1[5]="http://localhost/test/test.zte?method=login&info=fdsa";
		
		urls1[6]="http://localhost:8080";
		urls1[7]="http://localhost:8080/test";
		urls1[8]="https://localhost:8080/test/test.zte";
		urls1[9]="http://localhost:8080/?method=login&info=fdsa";
		urls1[10]="http://localhost:8080/test/test.zte?method=login";
		urls1[11]="http://localhost:8080/test/test.zte?method=login&info=fdsa";
		
		urls2=new String[12];
		urls2[0]="http://127.0.0.1";
		urls2[1]="https://127.0.0.1/test";
		urls2[2]="http://127.0.0.1/test/test.zte";
		urls2[3]="http://127.0.0.1/?method=login&info=fdsa";
		urls2[4]="https://127.0.0.1/test/test.zte?method=login";
		urls2[5]="http://127.0.0.1/test/test.zte?method=login&info=fdsa";
		
		urls2[6]="https://127.0.0.1:8080";
		urls2[7]="http://127.0.0.1:8080/test";
		urls2[8]="https://127.0.0.1:8080/test/test.zte";
		urls2[9]="http://127.0.0.1:8080/?method=login&info=fdsa";
		urls2[10]="http://127.0.0.1:8080/test/test.zte?method=login";
		urls2[11]="http://127.0.0.1:8080/test/test.zte?method=login&info=fdsa";
		
		urls3=new String[12];
		urls3[0]="http://www.test.com";
		urls3[1]="https://www.test.com/test";
		urls3[2]="http://www.test.com/test/test.zte";
		urls3[3]="https://www.test.com/?method=login&info=fdsa";
		urls3[4]="http://www.test.com/test/test.zte?method=login";
		urls3[5]="https://www.test.com/test/test.zte?method=login&info=fdsa";
		
		urls3[6]="http://www.test.com:8080";
		urls3[7]="http://www.test.com:8080/test";
		urls3[8]="https://www.test.com:8080/test/test.zte";
		urls3[9]="http://www.test.com:8080/?method=login&info=fdsa";
		urls3[10]="http://www.test.com:8080/test/test.zte?method=login";
		urls3[11]="http://www.test.com:8080/test/test.zte?method=login&info=fdsa";
	}

	/**
	 * Test method for {@link com.jxva.util.UrlUtil#UrlUtil()}.
	 */
	public void testUrlUtil() {
		assertTrue(true);
	}
	
	/**
	 * Test method for {@link com.jxva.util.UrlUtil#getHostAddr(java.lang.String)}.
	 */
	public void testGetHostAddr() {
		for(int i=0;i<6;i++){
			assertEquals(UrlUtil.getHostAddr(urls1[i]),"localhost");
			assertEquals(UrlUtil.getHostAddr(urls2[i]),"127.0.0.1");
			assertEquals(UrlUtil.getHostAddr(urls3[i]),"www.test.com");
		}
		for(int i=6;i<urls1.length;i++){
			assertEquals(UrlUtil.getHostAddr(urls1[i]),"localhost:8080");
			assertEquals(UrlUtil.getHostAddr(urls2[i]),"127.0.0.1:8080");
			assertEquals(UrlUtil.getHostAddr(urls3[i]),"www.test.com:8080");
		}
	}

	/**
	 * Test method for {@link com.jxva.util.UrlUtil#getHostName(java.lang.String)}.
	 */
	public void testGetHostName() {
		for(String str:urls1){
			assertEquals(UrlUtil.getHostName(str),"localhost");
		}
		for(String str:urls2){
			assertEquals(UrlUtil.getHostName(str),"127.0.0.1");
		}
		for(String str:urls3){
			assertEquals(UrlUtil.getHostName(str),"www.test.com");
		}
	}
	
	/**
	 * Test method for {@link com.jxva.util.UrlUtil#getHostPort(java.lang.String)}.
	 */
	public void testGetHostPort() {
		for(int i=0;i<6;i++){
			assertEquals(UrlUtil.getHostPort(urls1[i]),"");
			assertEquals(UrlUtil.getHostPort(urls2[i]),"");
			assertEquals(UrlUtil.getHostPort(urls3[i]),"");
		}
		for(int i=6;i<urls1.length;i++){
			assertEquals(UrlUtil.getHostPort(urls1[i]),"8080");
			assertEquals(UrlUtil.getHostPort(urls2[i]),"8080");
			assertEquals(UrlUtil.getHostPort(urls3[i]),"8080");
		}
	}

	/**
	 * Test method for {@link com.jxva.util.UrlUtil#isIp(java.lang.String)}.
	 */
	public void testIsIp() {
		for(String str:urls1){
			assertFalse(UrlUtil.isIp(str));
		}
		for(String str:urls2){
			assertTrue(UrlUtil.isIp(str));
		}
		for(String str:urls3){
			assertFalse(UrlUtil.isIp(str));
		}
	}

	/**
	 * Test method for {@link com.jxva.util.UrlUtil#getDomain(java.lang.String)}.
	 */
	public void testGetDomain() {
		for(String str:urls1){
			assertEquals(UrlUtil.getDomain(str),"localhost");
		}
		for(String str:urls2){
			assertEquals(UrlUtil.getDomain(str),"127.0.0.1");
		}
		for(String str:urls3){
			assertEquals(UrlUtil.getDomain(str),".test.com");
		}
	}

	/**
	 * Test method for {@link com.jxva.util.UrlUtil#isExternalUrl(java.lang.String)}.
	 */
	public void testIsExternalUrl() {
		for(String str:urls1){
			assertTrue(UrlUtil.isExternalUrl(str));
		}
		for(String str:urls2){
			assertTrue(UrlUtil.isExternalUrl(str));
		}
		for(String str:urls3){
			assertTrue(UrlUtil.isExternalUrl(str));
		}
		assertFalse(UrlUtil.isExternalUrl("/fdsa"));
	}

}
