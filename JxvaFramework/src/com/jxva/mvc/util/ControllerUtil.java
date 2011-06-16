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
package com.jxva.mvc.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxva.dao.DAOHolder;
import com.jxva.entity.Base16;
import com.jxva.entity.Encoding;
import com.jxva.entity.Jscape;
import com.jxva.entity.Path;
import com.jxva.http.HttpTransfer;
import com.jxva.mvc.Action;
import com.jxva.mvc.ActionContext;
import com.jxva.mvc.entity.View;
import com.jxva.plugin.SysPlugin;
import com.jxva.util.RandomUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-29 10:23:45 by Jxva
 */
public class ControllerUtil {
	
	private static final ConcurrentMap<String,File> map=new ConcurrentHashMap<String,File>();
	public static final String FORWARD_FLAG="forward_cache_"+RandomUtil.getRandomNum(1000);
	
	public static void createForwardCache(HttpServletRequest request,File file){
		String url=request.getRequestURL().toString();
		String ext=url.substring(url.lastIndexOf('.'));
		String appUrl=url.replace(request.getServletPath(),"/");;
		String queryString=request.getQueryString();
		String invokeUrl=url+"?"+(queryString==null?"":queryString+"&")+FORWARD_FLAG+"=true";
		String remoteUrl=appUrl+"framework!htmlBuilder"+ext+"?url="+Jscape.escape(invokeUrl)+"&file="+file.getName();
		new HttpTransfer(remoteUrl).get(Encoding.UTF_8);
	}
	
	public static File getCacheFile(HttpServletRequest request){
		synchronized(map){
			String fileName=Base16.encode(((request.getServletPath()+"?"+request.getQueryString()))).toLowerCase()+".html";
			File file=map.get(fileName);
			if(file==null){
				file=new File(Path.APP_PATH+SysPlugin.CACHE_PATH.substring(1)+fileName);
				map.put(fileName,file);
			}
			return file;
		}
	}
	
	public static  String[] getActionAndMethod(String url){
		String[] s=new String[2];
		String prefix=url.substring(0,url.lastIndexOf('.'));
		int exclamationMark=url.indexOf('!');
		if(exclamationMark>-1){
			s[0]=prefix.substring(0,exclamationMark);
			s[1]=prefix.substring(exclamationMark+1);
		}else{
			s[0]=prefix;
			s[1]="execute";
		}
		return s;
	}
	
	public static String execute(Class<?> cls,String ops,HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			Object obj=cls.newInstance();
			try{
				if(obj instanceof Action){
					//synchronized(obj){
						//cls.getSuperclass().getConstructor(HttpServletRequest.class,HttpServletResponse.class).newInstance(request,response);
						return (String)((Action) obj).init(request,response).getClass().getMethod(ops).invoke(obj);
					//}
				}
				return (String)cls.getMethod(ops).invoke(obj);
			}finally{
				obj=null;
				if(SysPlugin.USE_DAO)DAOHolder.removeDAO();
			}
		}catch(InvocationTargetException e){
			e.getTargetException().printStackTrace();
			View.printInvocationTargetExceptionWithHeaderAndFooter(ActionContext.getResponse().getWriter(),e);
			return null;
		}
	}
	
	public static String getValue(final String result,final String value){
		if(result==null)return value;
		return value.length()==0?result:value.replace("*",result);
	}
}
