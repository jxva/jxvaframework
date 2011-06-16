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
package com.jxva.dao.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 16:29:51 by Jxva
 */
public class ByteArray {
	
	private final InputStream in;
	
	public ByteArray(InputStream in){
		this.in=in;
	}

	public byte[] detach() throws IOException {
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int readed=in.read(buffer);
		while(readed>-1){
			bos.write(buffer,0,readed);
			readed=in.read(buffer);
		}
		in.close();
		bos.flush();
		return bos.toByteArray();
	}
}
