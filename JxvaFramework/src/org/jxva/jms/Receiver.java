
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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.MessageListener;


public class Receiver {
	public static final int TYPE_PP = 0;
	public static final int TYPE_PS = 1;
	public static final int TYPE_COMMON = 2;

	private int type;
	private String listenerName;
	private JMSUtil jms;

	/**
	 * 
	 * @param bundleBase
	 * @param listenerName
	 * @param type
	 * @throws Exception
	 */
	public Receiver(String bundleBase, String listenerName, int type)throws Exception {
		this.listenerName = listenerName;
		this.type = type;
		jms = new JMSUtil(bundleBase, false);
		listen();
	}

	/**
	 * 
	 * @param prop
	 * @param listenerName
	 * @param type
	 * @throws Exception
	 */
	public Receiver(Properties prop, String listenerName, int type)throws Exception {
		this.listenerName = listenerName;
		this.type = type;
		jms = new JMSUtil(prop, false);
		listen();
	}

	/**
	 * 
	 * @param prop
	 * @param listenerName
	 * @param type
	 * @throws Exception
	 */
	public Receiver(Map<Object,String> prop, String listenerName, int type) throws Exception {
		this.listenerName = listenerName;
		this.type = type;
		jms = new JMSUtil(prop, false);
		listen();
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void listen() throws Exception {
		try {
			MessageListener listener = (MessageListener) Class.forName(listenerName).newInstance();
			System.out.println("Type="+type);
			if (type == 0) {
				jms.initPP(-1);
				jms.queueReceiver.setMessageListener(listener);
			} else if (type == 1) {
				jms.initPS(-1);
				jms.subscriber.setMessageListener(listener);
			} else if (type == 2) {
				jms.init(-1);
				jms.consumer.setMessageListener(listener);
			} else {
				throw new Exception("type illegal!");
			}
			System.out.println("message listener is to listen......\r\nlistener name is: "+ listenerName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		jms.close();
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String args[]) {
		//Receiver receiver;
		try {
			Properties pro=System.getProperties();
			List<Object> keys=new LinkedList<Object>();
			keys.addAll(pro.keySet());
			for(int i=0;i<keys.size();i++){
				System.out.println(keys.get(i));
			}
			new Receiver("com.jxva.jms.MQConfig","com.jxva.jms.UtilListener", 0);
			System.out.println("listened");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}