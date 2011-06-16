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
 *
 */
package com.jxva.entity;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-01-12 19:50:37 by Jxva
 */
public class UserAgent {
	
	private final String userAgent;
	public UserAgent(HttpServletRequest request){
		this.userAgent=request.getHeader("User-Agent");
	}
	
	public String getOs(){
		String os=userAgent;
		if(os.indexOf("NT 5.0")>-1){
			return "Windows 2000";
		}else if(os.indexOf("NT 5.1")>-1){
			return "Windows XP";
		}else if(os.indexOf("NT 5.2")>-1){
			return "Windows 2003";
		}else if(os.indexOf("NT 6.0")>-1){
			return "Windows Vista";
		}else if(os.indexOf("9x")>-1){
			return "Windows Me";
		}else if(os.toLowerCase().indexOf("linux")>-1){
			return "Linux";
		}else if(os.toLowerCase().indexOf("unix")>-1){
			return "Unix";
		}else{
			return "Other";
		}
	}
		
	public String getBrowser(){
		String b=userAgent;
		try{
			//Maxthon TencentTraveler TheWorld MyIE2 GreenBrowser
			if(b.indexOf("MSIE")>-1){
				String ie=(b.split(";")[1]).replaceAll("MSIE","IE");
				if(b.toLowerCase().indexOf("maxthon")>-1){
					return "Maxthon for "+ie;
				}else if(b.toLowerCase().indexOf("tencenttraveler")>-1){
					return "TencentTraveler for "+ie;
				}else if(b.toLowerCase().indexOf("theworld")>-1){
					return "TheWorld for "+ie;
				}else if(b.toLowerCase().indexOf("myie2")>-1){
					return "MyIE2 for "+ie;
				}else if(b.toLowerCase().indexOf("greenbrowser")>-1){
					return "GreenBrowser for "+ie;
				}else{
					return ie;
				}
			}else if(b.indexOf("Firefox")>-1){
				return "Firefox "+b.split("Firefox")[1].substring(1);
			}else if(b.indexOf("Opera")>-1){
				return "Opera "+b.split("Opera")[1].split(" ")[0].substring(1);
			}else if(b.indexOf("Chrome")>-1){
				return "Chrome "+b.split("Chrome")[1].split(" ")[0].substring(1);
			}else if(b.indexOf("Safari")>-1){
				return "Safari "+b.split("Safari")[1].substring(1);
			}else if(b.indexOf("Shiretoko")>-1){
				return "Shiretoko "+b.split("Shiretoko")[1].split(" ")[0].substring(1);
			}else if(b.indexOf("Linux")>-1){
				return "Linux";
			}else{
				return "Other";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "Error";
		}
	}
}
