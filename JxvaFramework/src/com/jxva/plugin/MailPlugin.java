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
package com.jxva.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.log.Logger;
import com.jxva.mail.MailSmtp;
import com.jxva.util.UtilException;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-20 09:25:28 by Jxva
 */
public class MailPlugin  implements Pluginable{
	
	private static final Logger log=Logger.getLogger(MailPlugin.class);
	private static Map<String,MailSmtp> mailSmtps=new HashMap<String,MailSmtp>();

	@Override
	public void dispose() {
		mailSmtps.clear();
		mailSmtps=null;
	}

	@Override
	public void initialize() throws PluginException {
		try{
			mailSmtps.clear();
			log.info("Mail Smtp initializing... ");
			Element root = XmlUtil.getDocument(new File(Path.CLASS_PATH+"jxva-mail.xml"),Encoding.UTF_8).getRootElement();
			for(int i=0;i<root.getChildCount();i++){
	        	Element el=root.getElement(i);
	        	if(el!=null){
		        	String mailSmtpName=el.getAttributeValue("name");
		        	log.info("Mail Smtp's '"+mailSmtpName+"' initializing... ");
		        	MailSmtp mailSmtp=new MailSmtp();
			        mailSmtp.setAuth(Boolean.valueOf(el.getElementText("auth")));	
			        mailSmtp.setFrom(el.getElementText("from"));
			        mailSmtp.setHost(el.getElementText("host"));
			        mailSmtp.setUsername(el.getElementText("username"));
			        mailSmtp.setPassword(el.getElementText("password"));
			        mailSmtp.setTimeout(Integer.valueOf(el.getElementText("timeout")));
			        mailSmtps.put(mailSmtpName,mailSmtp);
	        	}
	        }
		}catch(UtilException e){
			throw new PluginException(e);
		}		
	}
	
	public static MailSmtp getMailSmtp(String name){
		return mailSmtps.get(name);
	}
}
