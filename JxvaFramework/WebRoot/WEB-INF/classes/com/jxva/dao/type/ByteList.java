package com.jxva.dao.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:53:07 by Jxva
 */
public class ByteList {
	private byte body[] = null;

	public byte[] detach() {
		return body;
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public byte[] read(InputStream in) throws IOException {
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		
		byte[] buffer=new byte[1024];
		int readed=in.read(buffer);
		while(readed>-1){
			bos.write(buffer,0,readed);
			readed=in.read(buffer);
		}
		in.close();
		bos.flush();
		body=bos.toByteArray();
		//System.out.println(new String(body));
		return body;
	}
}