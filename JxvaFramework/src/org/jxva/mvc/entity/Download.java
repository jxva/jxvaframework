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
package org.jxva.mvc.entity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * for download
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:03:19 by Jxva
 */
public class Download {
    private ServletContext context;
    private HttpServletResponse response;
    private int blockSize;
    private String contentType;
    
    /**
     * @param request
     * @param response
     */
    public Download(HttpServletRequest request, HttpServletResponse response){
        this.context = request.getSession().getServletContext();
        this.response = response;
        blockSize = 0x1000;
        contentType = "application/x-msdownload";
    }

    /**
     * @param request
     * @param response
     * @param contentType
     */
    public Download(HttpServletRequest request, HttpServletResponse response, String contentType){
        this(request,response);
        this.contentType =contentType;
    }
    
    /**
     * 
     * @param content
     * @param fileName
     * @throws IOException
     * @throws ServletException
     */
	public  void downloadBytes(byte content[], String fileName) throws IOException,ServletException {
		if (content == null || content.length == 0){
			throw new ServletException("Content length is zero");
		}
		response.setContentType(contentType);
		response.setContentLength(content.length);
		
		String header="attachment; filename=\"" + fileName + "\"";
		response.setHeader("Content-Disposition",header);
		
		ServletOutputStream servletoutputstream = response.getOutputStream();
		servletoutputstream.write(content);
		servletoutputstream.flush();
		return;
	}

	/**
	 * 
	 * @param path
	 * @param fileName
	 * @throws ServletException
	 * @throws IOException
	 */
	public void downloadFile(String path, String fileName) throws ServletException,IOException {
		File file = new File(path);
		if (!file.exists()) {
			if (context == null){
				file = new File(path);
			}else{
				file = new File(context.getRealPath(path));
			}
			if (!file.exists()){
				throw new IOException("Can not find the file");
			}
		}
		if (!file.canRead()){
			throw new IOException("The file is not readable");
		}
		if (fileName == null || fileName.equals("")){
			fileName = file.getName();
		}
		response.setContentType(contentType);
		response.setContentLength((int) file.length());
		
		String header="attachment; filename=\"" + fileName + "\"";
		response.setHeader("Content-Disposition",header);

		ServletOutputStream servletoutputstream = response.getOutputStream();
		int readedSize = 0;
		long fileSize = file.length();
		FileInputStream fileinputstream = new FileInputStream(file);
		byte block[] = new byte[blockSize];
		while ((long) readedSize < fileSize) {
			int i = fileinputstream.read(block, 0, blockSize);
			readedSize += i;
			servletoutputstream.write(block, 0, i);
		}
		servletoutputstream.flush();
		fileinputstream.close();
	}
	
	/**
	 * 
	 * @param inputstream
	 * @param fileName
	 * @throws IOException
	 * @throws ServletException
	 */
	public void downloadInputStream(InputStream inputstream, String fileName)throws IOException, ServletException {
		BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(bytearrayoutputstream);
		for (int i = -1; (i = bufferedinputstream.read()) != -1;){
			bufferedoutputstream.write(i);
		}
		bufferedoutputstream.flush();
		bytearrayoutputstream.flush();
		downloadBytes(bytearrayoutputstream.toByteArray(),fileName);
	}
}