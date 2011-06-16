
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

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class UtilListener implements MessageListener{
	/*
	 *  (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessage){
				ObjectMessage msg=(ObjectMessage) message;
				Serializable obj=msg.getObject();
				System.out.println("Jxva JMS:"+obj.toString());
			}else if (message instanceof TextMessage) {
				String text = ((TextMessage) message).getText();
				System.out.println("Jxva JMS:"+text);
			}else{
				throw new Exception("暂时不支持此消息类型:"+message.getClass());
			}
		} catch (Exception ex) {
			System.out.println("onMessage err:"+ex.getMessage());
		}
	}
}
