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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import com.jxva.entity.Encoding;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:23:11 by Jxva
 */
public abstract class FileUtil {
	
	public static final long ONE_KB = 1024;

	public static final long ONE_MB = ONE_KB * ONE_KB;
	
	public static final long ONE_GB = ONE_KB * ONE_MB;
	
	public static final File[] EMPTY_FILE_ARRAY = new File[0];
	
	public static final int BUFFER_SIZE = 4096;
	
	
    public static String byteCountToDisplaySize(long size) {
        String displaySize;
        if (size / ONE_GB > 0) {
            displaySize = String.valueOf(size / ONE_GB) + " GB";
        } else if (size / ONE_MB > 0) {
            displaySize = String.valueOf(size / ONE_MB) + " MB";
        } else if (size / ONE_KB > 0) {
            displaySize = String.valueOf(size / ONE_KB) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }
        return displaySize;
    }
	
	/**
	 * 将文本内容追加到文件中,以原文件内容后面新增
	 * @param fileName 文件名
	 * @param text 文本内容
	 * @param encoding 编码方式
	 * @return 加入成功:true 加入失败:false
	 */
	public static boolean append(File file, String text, String encoding){
		return write(file, text, encoding, true);
	}


	/**
	 * 新建一个新文件
	 * @param fileName 文件名
	 * @return 新建成功:true 新建失败:false
	 * @throws UtilException
	 */
	public static boolean create(File file)throws UtilException {
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			return true;
		} catch (IOException e) {
			throw new UtilException(e);
		}
	}

	
	public static String getFileExtensionName(String fileName){
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	
	/**
	 * 读取一个文件的内容,默认编码为UTF-8
	 * @param fileName 文件名
	 * @return 返回文件的文本内容
	 * @throws UtilException 
	 */
	public static String read(File file)throws UtilException{
		return read(file,Encoding.UTF_8);
	}
	
	/**
	 * 读取一个InputStream文件的内容,读取完后,将InputStream关闭
	 * @param fileName InputStream文件名
	 * @return 返回文件的文本内容
	 * @throws UtilException
	 */
	public static String read(InputStream inputStream)throws UtilException{
		try{
			StringBuilder  sb=new StringBuilder();  
			byte[] bytearray=new   byte[BUFFER_SIZE];  
			do{  
				int len=inputStream.read(bytearray,0,1024);
				sb.append(new String(bytearray,0,len));  
				if(len<1024)break;  
			}while(inputStream.available()>0); 
			return sb.toString();
		}catch(IOException e){
			throw new UtilException(e);
		}finally {
			try {if(inputStream!=null)inputStream.close();} catch (IOException e) {}
		}
	}

	/**
	 * 读取一个文件的内容
	 * @param fileName 文件名
	 * @param encoding 编码方式
	 * @return 返回文件的文本内容
	 * @throws UtilException 
	 */
	public static String read(File file, String encoding)throws UtilException {
		InputStreamReader input=null;
		BufferedReader br =null;
		try {
			input = new InputStreamReader(new FileInputStream(file), encoding);
			br = new BufferedReader(input);
			StringBuilder sb = new StringBuilder();
			String line =null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
			return sb.toString();
		} catch (IOException e) {
			throw new UtilException(e);
		}finally{
			try {if(input!=null)input.close();} catch (IOException e) {}
			try {if(br!=null)br.close();} catch (IOException e) {}
		}
	}

	/**
	 * 删除一个文件
	 * @param file 文件
	 * @return 删除成功:true 删除失败:false
	 */
	public static boolean delete(File file) {
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 重命名文件名
	 * @param srcFileName 源文件名
	 * @param destFileName 目标文件名
	 * @return 重命名成功:true 重命名失败:false
	 */
	public static boolean rename(File src,File dest) {
		if (src.exists()) {
			return src.renameTo(dest);
		}
		return false;
	}

	/**
	 * 将文本内容加入到文件中,原文件内容清空
	 * @param fileName 文件名
	 * @param text 文本内容
	 * @param encoding 编码方式
	 * @return 加入成功:true 加入失败:false
	 */
	public static boolean write(File file, String text, String encoding) {
		return write(file, text, encoding, false);
	}
	
	/**
	 * 移动文件,移动成功之后,源文件将被删除
	 * @param srcFileName 源文件名
	 * @param destFileName 目标文件名
	 * @return 移动成功:true 移动失败:false
	 * @throws IOException 
	 */
	public static boolean moveFile(File src,File dest) throws UtilException{
		copy(src,dest);
		return delete(src);
	}
	
	public static long copy(File src, File dest) throws UtilException {
		Assert.notNull(src, "No input File specified");
		Assert.notNull(dest, "No output File specified");
		try{
			return copy(new BufferedInputStream(new FileInputStream(src)),new BufferedOutputStream(new FileOutputStream(dest)));
		} catch (FileNotFoundException e) {
			throw new UtilException(e);
		}
	}

	public static void copy(byte[] in, File out) throws UtilException {
		Assert.notNull(in, "No input byte array specified");
		Assert.notNull(out, "No output File specified");
		
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(in);
			OutputStream outStream;
			outStream = new BufferedOutputStream(new FileOutputStream(out));
			copy(inStream, outStream);
		} catch (FileNotFoundException e) {
			throw new UtilException(e);

		}
	}

	public static byte[] copyToByteArray(File in)throws UtilException{
		Assert.notNull(in, "No input File specified");
		try {
			return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
		} catch (FileNotFoundException e) {
			throw new UtilException(e);
		}
	}

	public static long copy(InputStream in, OutputStream out) throws UtilException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");
		try {
			long byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}catch(IOException e){
			throw new UtilException(e);
		}finally {
			try {in.close();}catch (IOException ex) {}
			try {out.close();}catch (IOException ex) {}
		}
	}

	public static void copy(byte[] in, OutputStream out) throws UtilException {
		Assert.notNull(in, "No input byte array specified");
		Assert.notNull(out, "No OutputStream specified");
		try {
			out.write(in);
		} catch (IOException e) {
			throw new UtilException(e);
		}finally {
			try {out.close();}catch (IOException ex) {}
		}
	}

	public static byte[] copyToByteArray(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}


	public static int copy(Reader in, Writer out) throws UtilException {
		Assert.notNull(in, "No Reader specified");
		Assert.notNull(out, "No Writer specified");
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}catch(IOException e){
			throw new UtilException(e);
		}finally {
			try {in.close();}catch (IOException ex) {}
			try {out.close();}catch (IOException ex) {}
		}
	}


	public static void copy(String in, Writer out)throws UtilException {
		Assert.notNull(in, "No input String specified");
		Assert.notNull(out, "No Writer specified");
		try {
			out.write(in);
		} catch (IOException e) {
			throw new UtilException(e);
		}finally {
			try {out.close();}catch (IOException ex) {}
		}
	}

	public static String copyToString(Reader in)throws UtilException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}
	
	private static boolean write(File file, String text, String encoding,boolean isAppend) throws UtilException {
		Writer writer = null;
		try {
			if (!file.exists())create(file);
			writer = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(),isAppend), encoding);
			writer.write(text + "\r\n");
			writer.flush();
			return true;
		}catch(IOException e){
			throw new UtilException(e);
		}finally {
			try {if(writer!=null)writer.close();} catch (IOException e) {}
		}
	}
}
