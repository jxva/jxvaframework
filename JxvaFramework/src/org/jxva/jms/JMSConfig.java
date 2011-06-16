
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
package org.jxva.jms;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.plugin.PluginException;
import com.jxva.plugin.Pluginable;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;


public class JMSConfig implements Pluginable{
	

	/*
	 * jms提供商实现jms接口的版本
	 */
	public static String jmsVersion="1.0";
	/*
	 * 发送端
	 */
	public static List<Parameters> senders=new LinkedList<Parameters>();
	/*
	 * 接收端
	 */
	public static List<Parameters> receivers=new LinkedList<Parameters>();
	
	/**
	 * 
	 * @param ID
	 * @return Parameters
	 */
	public static Parameters getSenderByID(String ID){
		if(ID==null||ID.trim().equals("")){
			return null;
		}
		for(int i=0;i<JMSConfig.senders.size();i++){
			Parameters sender=JMSConfig.senders.get(i);
			if(sender.ID.equals(ID)){
				return sender;
			}
		}
		return null;
	}
	

	public void initialize()throws PluginException{
		senders.clear();
		receivers.clear();
				
		
		Element root =XmlUtil.getDocument(new File(Path.CLASS_PATH+"jxva-jms.xml"),Encoding.UTF_8).getRootElement();
		
		/*
		 * 版本信息等
		 */
		Element jmsElement=root.getElement("jms");
		JMSConfig.jmsVersion=jmsElement.getAttributeValue("version");
		
		/*
		 * 接收端
		 */
		//Element receiversElement=root.getElement("receivers");
		
		/**
		List<?> receiverElements=receiversElement.elements("receiver");
		for(int i=0;receiverElements!=null&&i<receiverElements.size();i++){
			Element receiverElement=(Element)receiverElements.get(i);
			
			Parameters receiver=new Parameters();
			receiver.ID=receiverElement.attributeValue("ID");
			receiver.NAME=receiverElement.attributeValue("NAME");
			receiver.INITIAL_CONTEXT_FACTORY=receiverElement.elementText("INITIAL_CONTEXT_FACTORY");
			receiver.CONNECTION_FACTORY=receiverElement.elementText("CONNECTION_FACTORY");
			receiver.PROVIDER_URL=receiverElement.elementText("PROVIDER_URL");
			receiver.DESTINATION=receiverElement.elementText("DESTINATION");
			receiver.LISTENER=receiverElement.elementText("LISTENER");
			
			String type=receiverElement.elementText("TYPE");
			if(JMSConfig.jmsVersion!=null&&JMSConfig.jmsVersion.equals("1.1")){
				receiver.TYPE=2;
			}else{
				if(type==null||type.equals("queue")){
					receiver.TYPE=0;
				}else if(type.equals("topic")){
					receiver.TYPE=1;
				}
			}	
			JMSConfig.receivers.add(receiver);
		}
		

		Element sendersElement=root.element("senders");
		List<?> senderElements=sendersElement.elements("sender");
		for(int i=0;senderElements!=null&&i<senderElements.size();i++){
			Element senderElement=(Element)senderElements.get(i);
			
			Parameters sender=new Parameters();
			sender.ID=senderElement.attributeValue("ID");
			sender.NAME=senderElement.attributeValue("NAME");
			sender.INITIAL_CONTEXT_FACTORY=senderElement.elementText("INITIAL_CONTEXT_FACTORY");
			sender.CONNECTION_FACTORY=senderElement.elementText("CONNECTION_FACTORY");
			sender.PROVIDER_URL=senderElement.elementText("PROVIDER_URL");
			sender.DESTINATION=senderElement.elementText("DESTINATION");
			
			String type=senderElement.elementText("TYPE");
			if(JMSConfig.jmsVersion!=null&&JMSConfig.jmsVersion.equals("1.1")){
				sender.TYPE=2;
			}else{
				if(type==null||type.equals("queue")){
					sender.TYPE=0;
				}else if(type.equals("topic")){
					sender.TYPE=1;
				}
			}					
			
			JMSConfig.senders.add(sender);
		}
		*/
	}
	
	public void dispose(){
		
	}
}
