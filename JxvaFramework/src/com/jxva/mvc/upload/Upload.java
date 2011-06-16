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
package com.jxva.mvc.upload;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.jxva.entity.Encoding;
import com.jxva.util.RandomUtil;

/**
 * 超高效高速的文件上传框架,带进度显示<br>
 * 支持表单上传,支持SwfUpload上传,单个或批量上传<br>
 * <b>Usage:</b>
 * <pre>
 * Upload uploader = new Upload(request,uploadpath, "UTF-8", 500000);
 * UploadMsg upMsg = uploader.save();//上传文件
 * if(upMsg.isSuccessful){
 *   //UploadedFile file=uploader.getUploadedFile("FileData");单个文件
 *   List<UploadedFile> files = uploader.getUploadedFiles();//多个文件
 *   for (int i = 0; i < files.size(); i++) {
 *     UploadedFile upFile =files.get(i);
 *     String fullfilename=upFile.getUploadedAbsoluteFileName();
 *     String filename = new File(fullfilename).getName();
 *     //dosomething
 *   }
 * }
 * </pre>	
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:21:24 by Jxva
 */
public class Upload {
	
	private static final String DISPOSITION="content-disposition: form-data;";
	private static final String FILENAME="filename=\"";
	
	private final HttpServletRequest request;
	private final String savePath;
	private final String encoding;
	
	
	private final Map<String,UploadFile> uploadedFiles;
	private final Map<String,String> otherParameters;
	private final long totalLength;

	
	private volatile long totalRead;
	
	/**
	 * 以UTF-8编码上传文件
	 * @param request  HttpServletRequest
	 * @param savePath 文件上传保存路径
	 */
	public Upload(HttpServletRequest request,String savePath){
		this(request,savePath,Encoding.UTF_8);
	}
	
	/**
	 * 以指定编码上传文件
	 * @param request  HttpServletRequest
	 * @param savePath 文件上传保存路径
	 * @param encoding 使用编码方式
	 */
	public Upload(HttpServletRequest request,String savePath,String encoding){
		this.request = request;
		if (!savePath.endsWith("\\")&&!savePath.endsWith("/")) {
			this.savePath=savePath+ '/';
		}else{
			this.savePath=savePath;
		}
		this.encoding=encoding;
		this.totalLength = request.getContentLength();		
		this.otherParameters=new HashMap<String,String>();
		this.uploadedFiles = new HashMap<String,UploadFile>();
	}
	
	public UploadMsg save(final int allowFileSize,final boolean autoRenameFile) throws IOException{
		return this.save(allowFileSize,null, autoRenameFile,false);
	}

	public UploadMsg save(final int allowFileSize,final String[] allowFileExt,final boolean autoRenameFile) throws IOException{
		return this.save(allowFileSize, allowFileExt, autoRenameFile,false);
	}
	
	public UploadMsg save(final int allowFileSize,final String[] allowFileExt,final boolean autoRenameFile,final boolean saveInTempFile) throws IOException {
		if (totalLength/1024 > allowFileSize) {
			throw new IOException("upload file's size is "+(totalLength/1024)+" KB too large,allow file's size is "+allowFileSize+" KB");
		}
		final List<String> allowFileExtList=allowFileExt==null?null:Arrays.asList(allowFileExt);
		String mimeBoundary="";
		String contentType = request.getContentType();
		if (contentType != null&& contentType.indexOf(',') != -1) {
			contentType = contentType.substring(0, contentType.indexOf(','));
		} else if (contentType != null&& contentType.startsWith("multipart/form-data")) {
			int i = contentType.indexOf("boundary=") +9;
			mimeBoundary = "--" + contentType.substring(i);
		}
		
		UploadMsg msg = new UploadMsg();
		msg.setAllowMega(allowFileSize/1024);
		try {
			final ServletInputStream sis=request.getInputStream();
			String line = "";
			do {
				
				if(line!=null&&!line.startsWith(mimeBoundary)){
					line = readLine(sis);//第一个文件,第一行（边界）
				}
				if (line==null||line.length() <= 0) {
					msg.setSuccessful(false);
					msg.setResult(UploadResult.UPLOAD_FILE_EMPTY);
					return msg;
				}

				//格式不正确,文件没有正确的边界
				if (line==null||!line.startsWith(mimeBoundary)) {
					msg.setSuccessful(false);
					msg.setResult(UploadResult.UPLOAD_BAD_FORMAT);
					return msg;
				} else {
					line = readLine(sis);//Content-Disposition
					
					//流结尾
					if (line == null || line.length() <= 0) {
						break;
					}
										
					//如果不是文件,将做为参数处理
					if(line.toLowerCase().indexOf(FILENAME)==-1&&line.toLowerCase().startsWith(DISPOSITION)){
						int paraNamePost=line.indexOf("name=\"")+6;
						String paraName=line.substring(paraNamePost,line.indexOf('\"',paraNamePost));
						String paraValue="";
						line=this.readLine(sis);
						do{
							line=this.readLine(sis);
							if(line==null){
								break;
							}else if(!line.startsWith(mimeBoundary)){
								paraValue+=line;
							}
							
						}while(!line.startsWith(mimeBoundary));
						if(request.getMethod().equalsIgnoreCase("GET")){
							paraValue=new String(paraValue.getBytes(Encoding.ISO_8859_1),encoding);
						}
						this.otherParameters.put(paraName,paraValue);
						continue;
					}
					if(line==null){
						break;
					}
										
					//如果用户上传的文件名为空时,将跳过此文件的上传 add at 2009-07-27
					if(line.toLowerCase().indexOf(FILENAME)==line.length()-11){
						byte buffer[] = new byte[8192];
						int lines = 0;
						do {
							int i = readLine(buffer, 0, buffer.length,sis);
							lines++;
							if (i <= 0) {
								break;
							}
							line = new String(buffer, 0, i);
							if(lines==1){//空行
								continue;
							}
						} while (line != null && !line.startsWith(mimeBoundary));
						continue;
					}
					
					//上传文件基本信息
					UploadFile uploadFile = new UploadFile();
					
					String uploadingFileName=null;
					String uploadingFileExtensionName=null;
					
					if (line.toLowerCase().startsWith(DISPOSITION)) {
						String parameterName=null;
						String uploadingAbsoluteFileName=null;
						
						if(line.toLowerCase().indexOf(FILENAME)>-1){
							uploadingAbsoluteFileName=getAbsoluteFileName(line);
							parameterName=getParameterName(line);
							uploadingFileName=getFileName(uploadingAbsoluteFileName);
							uploadingFileExtensionName=getFileExtensionName(uploadingFileName);
							if(allowFileExtList!=null){
								if(!allowFileExtList.contains(uploadingFileExtensionName)){
									throw new IOException("UnSupport file's type,allow file's type is "+allowFileExtList);
								}
							}
						}else{
							parameterName=getParameterName(line);
						}
						uploadFile.setUploadingAbsoluteFileName(uploadingAbsoluteFileName);
						uploadFile.setUploadingFileName(uploadingFileName);
						uploadFile.setParameterName(parameterName);
					}

					readLine(sis);//Content-Type
					
					
					
					//创建目标文件
					File uploadedFile =null;
								
					if(!saveInTempFile){
						File dir = new File(savePath);
						if (!dir.exists()) {
							dir.mkdir();
						}	
						String uploadedFileName = calculateUploadedFileName(uploadingFileName,uploadingFileExtensionName,autoRenameFile);
						uploadedFile = new File(savePath + uploadedFileName);
						uploadedFile.createNewFile();					
					}else{
						uploadedFile=File.createTempFile("jxva_upload_"+System.currentTimeMillis(),'.'+uploadingFileExtensionName);
					}
					
					//保存文件基本信息
					uploadFile.setUploadedFile(uploadedFile);
				

					//写文件
					final BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
					byte buffer[] = new byte[8192];
					boolean isEnter = false;//是否回车换行

					int lines = 0;
					do {
						int i = readLine(buffer, 0, buffer.length,sis);
						lines++;
						if (i <= 0) {
							break;
						}
						line = new String(buffer, 0, i);
						if(lines==1){//空行
							continue;
						}
						if (!line.startsWith(mimeBoundary)) {
							if (isEnter) {
								bufferedoutputstream.write(13);
								bufferedoutputstream.write(10);
								isEnter = false;
							}
							if (i >= 2 && buffer[i - 2] == 13&& buffer[i - 1] == 10) {
								isEnter = true;
								bufferedoutputstream.write(buffer, 0, i - 2);
							} else {
								bufferedoutputstream.write(buffer, 0, i);
							}
						}
					} while (line != null && !line.startsWith(mimeBoundary));
					bufferedoutputstream.flush();
					bufferedoutputstream.close();
					if (lines > 2) {
						uploadedFiles.put(uploadFile.getParameterName(),uploadFile);
					} else {
						uploadedFile.delete();
					}
					//写文件 ends
				}
			} while (true);
			msg.setSuccessful(true);
			msg.setResult(UploadResult.UPLOAD_SUCCESS);
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
			msg.setSuccessful(false);
			msg.setResult(UploadResult.UPLOAD_EXCEPTION);
			msg.setIoException(e);
			return msg;
		}
	}

	/**
	 * 读取一行
	 * @throws IOException
	 */
	private String readLine(ServletInputStream sis) throws IOException {
		int readBytes = -1;
		final byte buffer[] = new byte[8192];
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		do {
			readBytes = readLine(buffer, 0, buffer.length,sis);
			if (readBytes != -1) {
				os.write(buffer, 0, readBytes);
			}
		} while (readBytes == buffer.length);
		os.flush();
		byte content[] = os.toByteArray();
		if (content.length == 0) {
			return null;
		} else {
			if(encoding!=null&&encoding.length()>0){
				return new String(content, 0, content.length - 2,encoding);
			}else{
				return new String(content, 0, content.length - 2);
			}
		}
	}

	/**
	 * 读取一行
	 * @param buffer
	 * @param offset
	 * @param length
	 * @return 
	 * @throws IOException
	 */
	private int readLine(byte buffer[], int offset, long length,ServletInputStream sis)throws IOException {
		if (totalRead >= totalLength) {
			return -1;
		}
		if (length > totalLength - totalRead) {
			length = totalLength - totalRead;
		}
		int readBytes =sis.readLine(buffer, offset, (int)length);
		synchronized(this){
			totalRead += readBytes;
		}
		return readBytes;
	}	
	
	/**
	 * 文件的参数名
	 * @param line
	 */
	private String getParameterName(String line){
		final String paraNameFlag = "form-data; name=\"";
		int i = -1;

		//如果不是以"content-disposition: form-data"开头
		if (!line.toLowerCase().startsWith(DISPOSITION)) {
			return null;
		}

		//如果没有"filename="串
		i = line.indexOf(paraNameFlag);
		if (i == -1) {
			return null;
		}

		i += paraNameFlag.length();
		int k = line.indexOf('\"', i);
		return line.substring(i, k);
	}	

	/**
	 * 文件绝对路径名
	 * @param line
	 */
	private String getAbsoluteFileName(String line) {
		//如果不是以"content-disposition: form-data"开头
		if (!line.toLowerCase().startsWith(DISPOSITION)) {
			return null;
		}
		//如果没有"filename="串
		int i = line.indexOf(FILENAME);
		if (i == -1) {
			return null;
		}
		i += FILENAME.length();
		int k = line.indexOf('\"', i);
		return line.substring(i, k);
	}


	private String getFileName(String absoluteFileName) {
		int i =absoluteFileName.lastIndexOf('\\');
		return i>-1?absoluteFileName.substring(i+1):absoluteFileName;
	}


	private String getFileExtensionName(String fileName) {
		int i = fileName.lastIndexOf('.');
		return i>-1? fileName.substring(i+1).toLowerCase():null;
	}

	/**
	 * 计算保存文件名,如果文件存在,在文件名前加"#",如果仍然存在,继续在前面加"#"...
	 *  
	 */
	private String calculateUploadedFileName(String uploadingFileName,String uploadingFileExtensionName,boolean autoRenameFile) {
		String ret = uploadingFileName;
		while (true) {
			File file = new File(savePath + ret);
			if(autoRenameFile){
				return RandomUtil.getAutoId().substring(8)+'.'+uploadingFileExtensionName;
			}else{
				if (file.exists()) {
					ret = '#' + ret;
				} else {
					return ret;
				}
			}
		}
	}
	
	/**
	 * 批量上传时,得到上传文件基本信息列表
	 * @return 
	 */
	public List<UploadFile> getUploadedFiles() {
		List<UploadFile> ret=new LinkedList<UploadFile>();
		ret.addAll(uploadedFiles.values());
		return ret;
	}
	
	/**
	 * 单个上传时,得到上传文件基本信息
	 * @param paraName
	 * @return
	 */
	public UploadFile getUploadedFile(String paraName){
		return (UploadFile)uploadedFiles.get(paraName);
	}
	
	/**
	 * 获取其它参数
	 * @return
	 */
	public Map<String,String> getOtherParameters(){
		return this.otherParameters;
	}
	
	public long getTotalLength() {
		return totalLength;
	}

	public synchronized long getTotalRead() {
		return totalRead;
	}
	
	public synchronized int getProgress() {
		if(totalLength<=0)return 0;
		return (int)(totalRead*100/totalLength);
	}
}