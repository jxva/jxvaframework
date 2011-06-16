
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.NamingException;


public class Sender{	
	private JMSUtil jms;
	
	/**
	 * 
	 * @param bundleBase
	 * @throws NamingException
	 */
	public Sender(String bundleBase) throws Exception {
		jms=new JMSUtil(bundleBase,true);
	}
	
	/**
	 * 
	 * @param prop
	 * @throws Exception
	 */
	public Sender(Properties prop) throws Exception {
		jms=new JMSUtil(prop,true);
	}
	
	/**
	 * 
	 * @param prop
	 * @throws Exception
	 */
	public Sender(Map<Object,String> prop) throws Exception {
		jms=new JMSUtil(prop,true);
	}
	
	/**
	 * 发送文本消息(P2P)
	 * @param txt
	 */
	public void sendTxtP2P(String txt,Map<Object, String> flags,int persistent){
		try {
			if(!jms.ppInit){
				jms.initPP(persistent);
			}
			TextMessage msg=jms.queueSession.createTextMessage();
			msg.setText(txt);
			this.addFlags(msg,flags);
			jms.queueSender.send(msg);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送对象消息(P2P)
	 * @param obj
	 */
	public void sendObjP2P(Serializable obj,Map<Object, String> flags,int persistent){
		try {
			if(!jms.ppInit){
				jms.initPP(persistent);
			}
			ObjectMessage msg=jms.queueSession.createObjectMessage();
			msg.setObject(obj);
			this.addFlags(msg,flags);
			jms.queueSender.send(msg);	
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 发送文本消息(Pub-sub)
	 * @param txt
	 */
	public void sendTxtP2S(String txt,Map<Object, String> flags,int persistent){
		try {
			if(!jms.psInit){
				jms.initPS(persistent);
			}
			TextMessage msg=jms.topicSession.createTextMessage();
			msg.setText(txt);
			this.addFlags(msg,flags);
			jms.publisher.publish(msg);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送对象消息(Pub-sub)
	 * @param obj
	 */
	public void sendObjP2S(Serializable obj,Map<Object, String> flags,int persistent){
		try {
			if(!jms.psInit){
				jms.initPS(persistent);
			}
			ObjectMessage msg=jms.topicSession.createObjectMessage();
			msg.setObject(obj);
			this.addFlags(msg,flags);
			jms.publisher.publish(msg);	
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 发送对象消息(Pub-sub)
	 * @param obj
	 * @param secondsAlive
	 */
	public void sendObjP2S(Serializable obj,long secondsAlive,Map<Object, String> flags,int persistent){
		try {
			if(!jms.psInit){
				jms.initPS(persistent);
			}
			ObjectMessage msg=jms.topicSession.createObjectMessage();
			msg.setObject(obj);
			this.addFlags(msg,flags);
			jms.publisher.setTimeToLive(secondsAlive*1000);
			jms.publisher.publish(msg);	
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 发送文本消息(Pub-sub)
	 * @param txt
	 */
	public void sendTxt(String txt,Map<Object, String> flags,int persistent){
		try {
			if(!jms.init){
				jms.init(persistent);
			}
			TextMessage msg=jms.session.createTextMessage();
			msg.setText(txt);
			this.addFlags(msg,flags);
			jms.producer.send(msg);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送对象消息(Pub-sub)
	 * @param obj
	 */
	public void sendObj(Serializable obj,Map<Object, String> flags,int persistent){
		try {
			if(!jms.init){
				jms.init(persistent);
			}
			ObjectMessage msg=jms.session.createObjectMessage();
			msg.setObject(obj);
			this.addFlags(msg,flags);
			jms.producer.send(msg);	
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 发送对象消息(Pub-sub)
	 * @param obj
	 * @param secondsAlive
	 */
	public void sendObj(Serializable obj,long secondsAlive,Map<Object, String> flags,int persistent){
		try {
			if(!jms.init){
				jms.init(persistent);
			}
			ObjectMessage msg=jms.session.createObjectMessage();
			msg.setObject(obj);
			this.addFlags(msg,flags);
			jms.producer.setTimeToLive(secondsAlive*1000);
			jms.producer.send(msg);	
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void close()throws Exception{
		jms.close();
	}
	
	/**
	 * 
	 * @param msg
	 * @param flags
	 * @throws Exception
	 */
	private void addFlags(Message msg,Map<Object, String> flags)throws Exception{
		if(flags!=null&&!flags.isEmpty()){
			Set<Object> keySet=flags.keySet();
			Iterator<Object> keys=keySet.iterator();
			while(keys.hasNext()){
				String key=keys.next().toString();
				String value=flags.get(key);
				msg.setStringProperty(key,value);
				System.out.println(key+":"+value);
			}
		}
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception{
		Sender sender=new Sender("com.jxva.jms.MQConfig");
		Map<Object, String> m=new HashMap<Object, String>();
		m.put("op","op");
		sender.sendTxtP2P("XXfdsafsa",m,DeliveryMode.PERSISTENT);
		//sender.sendTxtP2S("fdsafsa",m,DeliveryMode.NON_PERSISTENT);
		sender.close();
		System.out.println("sended");
		System.exit(0);
	}
}
