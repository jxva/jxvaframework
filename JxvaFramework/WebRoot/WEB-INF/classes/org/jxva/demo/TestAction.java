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
package org.jxva.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.jxva.entity.Encoding;
import com.jxva.entity.HtmlBuilder;
import com.jxva.entity.Path;
import com.jxva.mvc.Action;
import com.jxva.mvc.upload.Upload;
import com.jxva.mvc.upload.UploadFile;
import com.jxva.mvc.upload.UploadMsg;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-06-29 10:32:21 by Jxva
 */
public class TestAction extends Action{
	
	private static final String UPLOAD_PATH=Path.APP_PATH+"upload/";
	

	public String html(){
		HtmlBuilder.build(form.getAppUrl()+"mtag!iterate.jv",new File(Path.APP_PATH+"iterate.html"),Encoding.UTF_8);
		return "iterate.html";
	}
	
	public String execute(){
		return SUCCESS;
	}
	
	public String upload() throws IOException{		
		Upload upload = new Upload(request,UPLOAD_PATH);
		UploadMsg msg = upload.save(204800,true);
		String filename=null;
		if (msg.isSuccessful()) {
			UploadFile upFile = upload.getUploadedFile("Filedata");
			File file=upFile.getUploadedFile();
			filename = file.getName();
			System.out.println("SSSSSSSSSS:"+filename);
			Map<String,String> params=upload.getOtherParameters();
			for(String key:params.keySet()){
				System.out.println(key+'='+params.get(key));
			}
		}
		return filename;
	}
	
	public String uploadMulti() throws IOException{		
		Upload upload = new Upload(request,UPLOAD_PATH);
		UploadMsg msg = upload.save(2048,true);
		StringBuilder sb=new StringBuilder();
		if (msg.isSuccessful()) {
			List<UploadFile> files = upload.getUploadedFiles();
			for(UploadFile f:files){
				File file=f.getUploadedFile();
				String filename = file.getName();
				sb.append(filename).append(',');
				System.out.println("MMMMMMMMMM:"+filename);
			}
			Map<String,String> params=upload.getOtherParameters();
			for(String key:params.keySet()){
				System.out.println(key+'='+params.get(key));
			}
		}
		return sb.toString();
	}
}
