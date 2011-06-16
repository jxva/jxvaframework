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
package org.jxva;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-06 11:33:55 by Jxva
 */
public class UDPServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		final DatagramSocket ds = new DatagramSocket(168);

		
		while (true){
			final byte[] buf = new byte[128];
			final DatagramPacket ip = new DatagramPacket(buf, buf.length);
			//System.out.println(Thread.currentThread().getName());
			ds.receive(ip);
			new Thread(new Runnable(){
				public void run(){
					String result=new String(buf,0,ip.getLength());
					System.out.println(result);
					
					try {
						//返回给客户端的数据
						ip.setData("china".getBytes());
						ds.send(ip);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
