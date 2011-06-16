
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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * 
 * 
 * 
 * 一个典型的 JMS 程序要经过以下步骤才能开始产生和使用消息: 
 * 1,通过 JNDI 查询 ConnectionFactory 。
 * 2,通过 JNDI 查询一个或者多个 Destination。
 * 3,用 ConnectionFactory 创建一个 Connection。
 * 4,用 Connection 创建一个或者多个 Session。
 * 5,用 Session 和 Destination 创建所需要的 MessageProducer 和 MessageConsumer。
 * 6,启动 Connection。 
 */
public class JMSUtil { 
	protected Map<Object, String> properties;//JMS传输配置
	protected Context context;//上下文
	
	/*
	 * 通用接口
	 */
	protected boolean init=false;
	protected ConnectionFactory connectionFactory;
	protected Connection connection;
	protected Session session;
	protected Destination destination;
	protected MessageProducer producer;
	protected MessageConsumer consumer;
	
	/*
	 * PP
	 */
	protected boolean ppInit=false;
	protected QueueConnectionFactory queueConnectionFacotory;
	protected QueueConnection queueConnection;
	protected QueueSession queueSession;
	protected Queue queue;
	protected QueueSender queueSender;
	protected QueueReceiver queueReceiver;
	
	/*
	 * PS
	 */
	protected boolean psInit=false;
	protected TopicConnectionFactory topicConnectionFacotory;
	protected TopicConnection topicConnection;
	protected TopicSession topicSession;
	protected Topic topic;
	protected TopicPublisher publisher;
	protected TopicSubscriber subscriber;
	
	/*
	 * 是发送,还是接收
	 */
	protected boolean issender;
	
	/**
	 * 
	 * @param bundleBase
	 * @param issender
	 * @throws Exception
	 */
	protected JMSUtil(String bundleBase,boolean issender) throws Exception {
		properties=new HashMap<Object, String>();
		try{
			ResourceBundle bundle = ResourceBundle.getBundle(bundleBase);
			Enumeration<String> keys = bundle.getKeys();
			while(keys!=null&&keys.hasMoreElements()){
				Object key = keys.nextElement();
				String means=bundle.getString((String) key).trim();
				properties.put(key,means);
			}
			this.issender=issender;
			getContext();
		}catch(Exception e){
			System.out.println("初始化JMS 的 JNDI 配置出错:");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param prop
	 * @param issender
	 * @throws Exception
	 */
	protected JMSUtil(Properties prop,boolean issender) throws Exception {
		properties=new HashMap<Object, String>();
		try{
			Enumeration<Object> keys = prop.keys();
			while(keys!=null&&keys.hasMoreElements()){
				Object key = keys.nextElement();
				String means=prop.getProperty((String) key).trim();
				properties.put(key,means);
			}
			this.issender=issender;
			getContext();
		}catch(Exception e){
			System.out.println("初始化JMS 的 JNDI 配置出错:");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param prop
	 * @param issender
	 * @throws Exception
	 */
	protected JMSUtil(Map<Object, String> prop,boolean issender) throws Exception {
		properties=prop;
		this.issender=issender;
		getContext();
	}
	
	/**
	 * 
	 * @param initFact
	 * @param providerUrl
	 * @return Context
	 * @throws Exception
	 */
	protected void getContext()throws Exception{
		Hashtable<String, String> properties = new Hashtable<String, String>();
		//初始化工厂
		properties.put(Context.INITIAL_CONTEXT_FACTORY,this.getProperty("INITIAL_CONTEXT_FACTORY"));
		//提供商URL		
		properties.put(Context.PROVIDER_URL,this.getProperty("PROVIDER_URL"));
		
		context =new InitialContext(properties);
	}
	
	/**
	 * 初始化点对点
	 * 
	 * @param persistent
	 * @throws Exception
	 */
	protected void initPP(int persistent)throws Exception{
		/*
		 * 初始化连接工厂
		 */
		queueConnectionFacotory = (QueueConnectionFactory) context.lookup(getProperty("QUEUE_CONNECTION_FACTORY"));
		queueConnection = queueConnectionFacotory.createQueueConnection();
		queueConnection.start();
		
		/*
		 * 创建会话
		 */
		queueSession=queueConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
		
		/*
		 * 得到队列
		 */
		queue=(Queue) context.lookup(getProperty("QUEUE"));
		
		if(issender){
			/*
			 * 发送者
			 */
			queueSender = queueSession.createSender(queue);
			queueSender.setDeliveryMode(persistent);
		}else{		
			/*
			 * 接收者
			 */
			queueReceiver=queueSession.createReceiver(queue);
		}
		
		this.ppInit=true;
	}
	
	/**
	 * 初始化订阅发布
	 * 
	 * @param persistent
	 * @throws Exception
	 */
	protected void initPS(int persistent)throws Exception{
		/*
		 * 初始化连接工厂
		 */
		topicConnectionFacotory = (TopicConnectionFactory) context.lookup(getProperty("TOPIC_CONNECTION_FACTORY"));
		topicConnection = topicConnectionFacotory.createTopicConnection();
		topicConnection.start();
		
		/*
		 * 创建会话
		 */
		topicSession=topicConnection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		
		/*
		 * 得到主题
		 */
		topic=(Topic) context.lookup(getProperty("TOPIC"));
		
		if(issender){
			/*
			 * 发送者
			 */
			publisher = topicSession.createPublisher(topic);
			publisher.setDeliveryMode(persistent);
		}else{
			/*
			 * 接收者
			 */
			subscriber=topicSession.createSubscriber(topic);
			//System.out.println("jiangzan");
			//subscriber=session.createDurableSubscriber(topic,"jiangzan");
		}
		this.psInit=true;
	}
	
	/**
	 * 初始化通用接口
	 * @param persistent
	 * @throws Exception
	 */
	protected void init(int persistent)throws Exception{
		/*
		 * 初始化连接工厂
		 */
		connectionFactory = (ConnectionFactory) context.lookup(getProperty("CONNECTION_FACTORY"));
		connection = connectionFactory.createConnection();
		connection.start();
		
		/*
		 * 创建会话
		 */
		session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		
		/*
		 * 得到目的
		 */
		destination=(Destination) context.lookup(getProperty("DESTINATION"));
		
		if(issender){
			/*
			 * 发送者
			 */
			producer = session.createProducer(destination);
			producer.setDeliveryMode(persistent);
		}else{		
			/*
			 * 接收者
			 */
			consumer=session.createConsumer(destination);
		}
		
		this.init=true;
	}
	
	/**
	 * 关闭所有
	 *
	 */
	protected void close()throws Exception{
		if(this.ppInit){
			try{this.queueSender.close();this.queueSender=null;}catch(Exception e){}
			try{this.queueSession.close();this.queueSession=null;}catch(Exception e){}
			try{this.queueConnection.close();this.queueConnection=null;}catch(Exception e){}
			try{this.queueReceiver.close();this.queueReceiver=null;}catch(Exception e){}
			try{this.queue=null;}catch(Exception e){}
			try{this.queueConnectionFacotory=null;}catch(Exception e){}
			this.ppInit=false;
		}
		if(this.psInit){
			try{this.publisher.close();this.publisher=null;}catch(Exception e){}
			try{this.topicSession.close();this.topicSession=null;}catch(Exception e){}
			try{this.topicConnection.close();this.topicConnection=null;}catch(Exception e){}
			try{this.subscriber.close();this.subscriber=null;}catch(Exception e){}
			try{this.topic=null;}catch(Exception e){}
			try{this.topicConnectionFacotory=null;}catch(Exception e){}
			this.psInit=false;
		}
		if(this.init){
			try{this.producer.close();this.producer=null;}catch(Exception e){}
			try{this.session.close();this.session=null;}catch(Exception e){}
			try{this.connection.close();this.connection=null;}catch(Exception e){}
			try{this.consumer.close();this.consumer=null;}catch(Exception e){}
			try{this.destination=null;}catch(Exception e){}
			try{this.connectionFactory=null;}catch(Exception e){}
			         
			this.init=false;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return String
	 */
	private String getProperty(String key){
		if(properties==null||properties.isEmpty()){
			return "";
		}
		if(properties.containsKey(key)){
			return properties.get(key);
		}
		return "";
	}
}