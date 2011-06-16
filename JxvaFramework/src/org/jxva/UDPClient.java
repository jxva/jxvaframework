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
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mvel2.MVEL;

import com.jxva.util.CharUtil;
import com.jxva.util.RandomUtil;

/**
 * 发送数据的客户端
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-07-06 11:36:03 by Jxva
 */
public class UDPClient {

	private static InetAddress target;
	private static DatagramSocket ds ;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		target = InetAddress.getByName("127.0.0.1");
		ds = new DatagramSocket(8888);
		
		ExecutorService pool = Executors.newFixedThreadPool(1000);
		for (int i = 0; i <1000; i++) {
			pool.execute(new Runnable() {
				public void run() {
					try {
						send(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,10));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		pool.shutdown();
	}
	
	public static void send(String info)throws IOException {
		byte[] buf = info.getBytes();
		DatagramPacket op = new DatagramPacket(buf, buf.length, target,168);
		ds.send(op);
		
		//ds.close();
	}
}
