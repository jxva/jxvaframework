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

import java.io.File;

/**
 * 上传成功后,获取文件信息
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:23:14 by Jxva
 */
public class UploadFile {
	
	private String uploadingAbsoluteFileName;
	private String uploadingFileName;
	private File uploadedFile;	
	private String parameterName;
	
	public String getUploadingAbsoluteFileName() {
		return uploadingAbsoluteFileName;
	}
	public void setUploadingAbsoluteFileName(String uploadingAbsoluteFileName) {
		this.uploadingAbsoluteFileName = uploadingAbsoluteFileName;
	}
	public String getUploadingFileName() {
		return uploadingFileName;
	}
	public void setUploadingFileName(String uploadingFileName) {
		this.uploadingFileName = uploadingFileName;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public File getUploadedFile() {
		return uploadedFile;
	}
	
}
