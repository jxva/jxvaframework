
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
import java.util.Map;

import javax.jms.DeliveryMode;

import com.jxva.log.Logger;
import com.jxva.plugin.Pluginable;


public class Transfer implements Pluginable{
	
	private static Logger jlog=Logger.getLogger(Transfer.class);
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendTxt(String senderID,String msg)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxt(msg,null,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2P(msg,null,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2S(msg,null,DeliveryMode.NON_PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}	
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendObj(String senderID,Serializable msg)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObj(msg,null,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2P(msg,null,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2S(msg,null,DeliveryMode.NON_PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendTxt(String senderID,String msg,Map<Object,String> flags)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxt(msg,flags,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2P(msg,flags,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2S(msg,flags,DeliveryMode.NON_PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}	
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendObj(String senderID,Serializable msg,Map<Object,String> flags)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObj(msg,flags,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2P(msg,flags,DeliveryMode.NON_PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2S(msg,flags,DeliveryMode.NON_PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendPersistentTxt(String senderID,String msg)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxt(msg,null,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2P(msg,null,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2S(msg,null,DeliveryMode.PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}	
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendPersistentObj(String senderID,Serializable msg)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObj(msg,null,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2P(msg,null,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2S(msg,null,DeliveryMode.PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendPersistentTxt(String senderID,String msg,Map<Object,String> flags)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxt(msg,flags,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2P(msg,flags,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendTxtP2S(msg,flags,DeliveryMode.PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}	
	}
	
	/**
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendPersistentObj(String senderID,Serializable msg,Map<Object,String> flags)throws Exception{
		Parameters sender=JMSConfig.getSenderByID(senderID);
		if(sender==null){
			throw new Exception("找不到指定的发送端:"+senderID);
		}
		Map<Object, String> parameters=new HashMap<Object, String>();
		
		if(JMSConfig.jmsVersion.equals("1.1")){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("DESTINATION",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObj(msg,flags,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==0){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("QUEUE_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("QUEUE",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2P(msg,flags,DeliveryMode.PERSISTENT);
		}else if(sender.TYPE==1){
			parameters.put("INITIAL_CONTEXT_FACTORY",sender.INITIAL_CONTEXT_FACTORY);
			parameters.put("TOPIC_CONNECTION_FACTORY",sender.CONNECTION_FACTORY);
			parameters.put("TOPIC",sender.DESTINATION);
			parameters.put("PROVIDER_URL",sender.PROVIDER_URL);
			Sender realSender=new Sender(parameters);
			realSender.sendObjP2S(msg,flags,DeliveryMode.PERSISTENT);
		}else{
			throw new Exception("不合法的消息目的地类型:"+sender.TYPE);
		}
	}
	

	public void initialize(){
		//启动监听器
		for(int i=0;i<JMSConfig.receivers.size();i++){
			Parameters receiver=(Parameters)JMSConfig.receivers.get(i);
			Map<Object, String> parameters=new HashMap<Object, String>();
			if(JMSConfig.jmsVersion.equals("1.1")){
				parameters.put("INITIAL_CONTEXT_FACTORY",receiver.INITIAL_CONTEXT_FACTORY);
				parameters.put("CONNECTION_FACTORY",receiver.CONNECTION_FACTORY);
				parameters.put("DESTINATION",receiver.DESTINATION);
				parameters.put("PROVIDER_URL",receiver.PROVIDER_URL);
				//jms
			}else if(receiver.TYPE==0){
				parameters.put("INITIAL_CONTEXT_FACTORY",receiver.INITIAL_CONTEXT_FACTORY);
				parameters.put("QUEUE_CONNECTION_FACTORY",receiver.CONNECTION_FACTORY);
				parameters.put("QUEUE",receiver.DESTINATION);
				parameters.put("PROVIDER_URL",receiver.PROVIDER_URL);
			}else if(receiver.TYPE==1){
				parameters.put("INITIAL_CONTEXT_FACTORY",receiver.INITIAL_CONTEXT_FACTORY);
				parameters.put("TOPIC_CONNECTION_FACTORY",receiver.CONNECTION_FACTORY);
				parameters.put("TOPIC",receiver.DESTINATION);
				parameters.put("PROVIDER_URL",receiver.PROVIDER_URL);
			}else{
				jlog.warn("不合法的消息目的地类型:"+receiver.TYPE);
			}	
			try{
				new Receiver(parameters,receiver.LISTENER,receiver.TYPE);
			}catch(Exception e){
				e.printStackTrace();
				jlog.warn(e.getMessage());
			}
			System.out.println("消息监听器 "+receiver.NAME+" 已经启动,消息处理类为:"+receiver.LISTENER);
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception{
		Map<Object, String> parameters=new HashMap<Object, String>();
		parameters.put("INITIAL_CONTEXT_FACTORY","org.exolab.jms.jndi.InitialContextFactory");
		parameters.put("QUEUE_CONNECTION_FACTORY","JmsQueueConnectionFactory");
		parameters.put("QUEUE","queue1");
		parameters.put("PROVIDER_URL","rmi://localhost:1099");		
		Sender realSender=new Sender(parameters);
		realSender.sendTxtP2P("jxva",null,DeliveryMode.PERSISTENT);
		System.out.println("finished");
	}
	
	public void dispose(){
		
	}
}
