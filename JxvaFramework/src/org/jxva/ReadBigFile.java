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

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-09-03 16:06:36 by Jxva
 */
public class ReadBigFile {

	/**
	 * 读取超大文件内容
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
	    byte[] bs = new byte[1024];
	    ByteBuffer byteBuf = ByteBuffer.allocate(1024);
	    FileChannel channel = new RandomAccessFile("E:/semc_java_me_cldc_sdk.2-5-0-2.zip","r").getChannel();
	    while(channel.read(byteBuf) != -1) {
	      //int size = byteBuf.position();
	      byteBuf.rewind();
	      byteBuf.get(bs);
	      // 把文件当字符串处理，直接打印做为一个例子。
	      //System.out.print(new String(bs, 0, size));
	      byteBuf.clear();
	    }
	  }

}
