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
package com.jxva.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import com.jxva.entity.Encoding;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:25:03 by Jxva
 */
public abstract class NIOUtil{
		

	interface FileChannelCallback<T>{
		T doInFileChannel(FileChannel fc)throws IOException;
	}
	
	interface FileChannelCreator{
		FileChannel createFileChannel()throws IOException;
	}
	
	private static <T> T execute(FileChannelCreator fcc,FileChannelCallback<T> callback)throws UtilException{
		FileChannel fc=null;
		try{
			fc=fcc.createFileChannel();
			return callback.doInFileChannel(fc);
		}catch(IOException e){
			throw new UtilException(e);
		}finally{
			try {if(fc!=null)fc.close();fc=null;} catch (IOException e) {}
		}
	}
	
	/**
	 * 读取一个文件的内容,默认编码为UTF-8
	 * @param fileName 文件名
	 * @return String 返回文件的文本内容
	 */
	public static String read(File file){
		return read(file,Encoding.UTF_8);
	}

	/**
	 * 读取一个文件的内容
	 * @param fileName 文件名
	 * @param encoding 编码方式
	 * @return String 返回文件的文本内容
	 * @throws UtilException 
	 */
	public static String read(final File file, final String encoding)throws UtilException{		
		return execute(new FileChannelCreator(){
			public FileChannel createFileChannel() throws IOException{
				return new FileInputStream(file.getAbsolutePath()).getChannel();
			}
		},new FileChannelCallback<String>(){
			public String doInFileChannel(FileChannel fc) throws IOException {
				int size = (int) fc.size();
				MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, size);
				Charset charset = Charset.forName(encoding);
				CharBuffer cb = charset.newDecoder().decode(mappedByteBuffer);
				return cb.toString();
			}
		});
	}
	
	/**
	 * 将文本内容追加到文件中,以原文件内容后面新增
	 * @param fileName 文件名
	 * @param text 文本内容
	 * @param encoding 编码方式
	 * @return boolean 加入成功:true 加入失败:false
	 */
	public static boolean append(File file, String text,String encoding) {
		return write(file, text,encoding,true);
	}
	
	/**
	 * 将文本内容追加到文件中,以原文件内容后面新增,默认编码为UTF-8
	 * @param fileName 文件名
	 * @param text 文本内容
	 * @return boolean 加入成功:true 加入失败:false
	 */
	public static boolean append(File file, String text) {
		return write(file, text,Encoding.UTF_8,true);
	}
	
	/**
	 * 将文本内容加入到文件中,原文件内容清空
	 * @param fileName 文件名
	 * @param text 文本内容
	 * @param encoding 编码方式
	 * @return boolean 加入成功:true 加入失败:false
	 */
	public static boolean write(File file, String text,String encoding) {
		return write(file, text,encoding,false);
	}

	/**
	 * 将文本内容加入到文件中,原文件内容清空,默认编码为UTF-8
	 * @param fileName 文件名
	 * @param text 文本内容
	 * @return boolean 加入成功:true 加入失败:false
	 */
	public static boolean write(File file, String text) {
		return write(file, text,Encoding.UTF_8,false);
	}
	
	private static boolean write(final File file, final String content, final String encoding,final boolean isAppend)throws UtilException{
		return execute(new FileChannelCreator(){
			public FileChannel createFileChannel() throws IOException{
				return new FileOutputStream(file.getAbsolutePath(),isAppend).getChannel();
			}
		},new FileChannelCallback<Boolean>(){
			public Boolean doInFileChannel(FileChannel fc) throws IOException {
				fc.write(ByteBuffer.wrap(content.getBytes(encoding)));
				return true;
			}
		});
	}
		
	public static long copy(File src,File dest) throws UtilException{
		FileChannel srcFc=null;
		FileChannel destFc=null;
		try{
			srcFc= new FileInputStream(src).getChannel();
	        destFc= new FileOutputStream(dest).getChannel();
	        long srcLength=srcFc.size();
	        srcFc.transferTo(0,srcLength,destFc);
	        // or
	        // destFc.transferFrom(srcFc, 0,destFc.size());
	        return srcLength;
		} catch (IOException e) {
			throw new UtilException(e);
		}finally{
			try {if(srcFc!=null)srcFc.close();srcFc=null;} catch (IOException e) {}
			try {if(destFc!=null)destFc.close();destFc=null;} catch (IOException e) {}
		}
	}
	
	public static void main(String[] args){
		copy(new File("C:/v2v3-0.2.tar.gz"),new File("C:/v2v3-0.2.tar"));
	}
}
