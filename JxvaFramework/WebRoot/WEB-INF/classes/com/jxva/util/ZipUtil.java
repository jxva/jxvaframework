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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

import com.jxva.entity.Encoding;
import com.jxva.util.zip.ZipEntry;
import com.jxva.util.zip.ZipInputStream;
import com.jxva.util.zip.ZipOutputStream;

/**
 * zip or unzip file or folder,support user-defined encoding (include chinese encoding GBK/GB2312)
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:27:32 by Jxva
 */
public abstract class ZipUtil{
	
    /**
     * unzip zip file to target path<br>
     * example:<br>
     *     unzip("E:/demo.zip","E:/test","GBK"); 
     * @param zipFile zip file
     * @param destPath target path
     * @param encoding character encoding
     * @throws UtilException
     */
    public static void unzip(String zipFile, String destPath,String encoding) throws UtilException{
	    try{	
	        unzip(new FileInputStream(zipFile), destPath,encoding);
	    }catch(FileNotFoundException e){
			throw new UtilException(e);
		}
    }

    /**
     * unzip zip file stream to target path<br>
     * example:<br>
     *     FileInputStream zipInputStream=new FileInputStream("E:/demo.zip");<br>
     *     unzip(zipInputStream,"E:/test","GBK");
     * @param zipFileStream zip file stream
     * @param destPath target path
     * @param encoding character encoding
     * @throws UtilException
     */
    public static void unzip(InputStream zipFileStream, String destPath,String encoding) throws UtilException{
    	try{
	        char separator = File.separatorChar;
	        if (!destPath.endsWith(String.valueOf(separator))){
	            destPath = destPath + separator;
	        }
	        File dir = new File(destPath);
	        if((dir.exists() && !dir.isDirectory()) || !dir.exists()){
	            dir.mkdirs();
	        }
	        CheckedInputStream cis = new CheckedInputStream(zipFileStream, new Adler32());
	        ZipInputStream     zis = new ZipInputStream(new BufferedInputStream(cis),encoding);
	        ZipEntry           ze = null;
	        while ((ze = zis.getNextEntry()) != null){
	            String fileName = ze.getName();
	            fileName = fileName.replace('/', separator);
	
	            if (fileName.indexOf(separator) >= 0){
	                String path = fileName;
	                // make dir
	                String supPath = "";
	                while (path.indexOf(separator) > 0){
	                    String oneLeverPath = path.substring(0, path.indexOf(separator));
	                    File   file = new File(destPath + supPath + oneLeverPath);
	                    file.mkdir();
	
	                    path = path.substring(path.indexOf(separator) + 1, path.length());
	                    supPath = supPath + oneLeverPath + separator;
	                }
	            }
	            //System.out.println(ze.getName());
	            if (ze.isDirectory()){
	                dir = new File(ze.getName());
	                dir.mkdir();
	            }else{
	                fileName = destPath + fileName;
	
	                FileOutputStream oneFile = new FileOutputStream(fileName);
	                byte[]           bBuf = new byte[1024];
	                int              length = 0;
	                while ((length = zis.read(bBuf)) != -1){
	                    oneFile.write(bBuf, 0, length);
	                }
	                oneFile.close();
	            }
	        }
    	}catch(Exception e){
    		throw new UtilException(e);
    	}
    }
    
    /**
     * zip src path to target zip file
     * @param srcPath src path
     * @param destZipFile target zip file
     * @param encoding character encoding
     * @throws UtilException
     */
    public static void zip(String srcPath,String destZipFile,String encoding)throws UtilException{
    	try{
			FileOutputStream dest = new FileOutputStream(destZipFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest),encoding);
			out.setMethod(ZipOutputStream.DEFLATED);
			File dir = new File(srcPath);
			boolean containsPathRoot=srcPath.endsWith("\\")||srcPath.endsWith("/");
			writeZipBytes(containsPathRoot?srcPath.length()-1:srcPath.length(),dir,out);
			out.close();
    	}catch(Exception e){
    		throw new UtilException(e);
    	}
    }
    
    private static void writeZipBytes(int rootLength,File file,ZipOutputStream out)throws Exception{
    	if(file.isFile()){
    		final int BUFFER = 2048;
			byte data[] = new byte[BUFFER];
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream original = new BufferedInputStream(fin, BUFFER);
			String entryName=file.getAbsolutePath().substring(rootLength+1);
			ZipEntry entry = new ZipEntry(entryName);
			out.putNextEntry(entry);
			int readed;
			while ((readed = original.read(data, 0,BUFFER)) != -1) {
				out.write(data, 0, readed);
			}
			original.close();
    	}else{
    		File[] files = file.listFiles();
			for (File _file:files) {
				writeZipBytes(rootLength,_file,out);
			}
    	}
    }

    public static void main(String[] args) throws Exception{
    	unzip("E:/55.zip", "E:/555",Encoding.GBK);
    	zip("E:/单点登录相关文档", "E:/555ok.zip",Encoding.GBK);
    	unzip("E:/555ok.zip", "E:/666",Encoding.GBK);
    }
}

