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

import java.io.IOException;

/**
 * 文件上传返回的数据
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:23:21 by Jxva
 */
public class UploadMsg {
	
	private boolean isSuccessful;//是否成功
	private int result;//上传处理结果
	private IOException ioException;//系统抛出的异常
	private int allowMega;//最大允许的文件大小
	
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

	public int getAllowMega() {
		return allowMega;
	}
	public void setAllowMega(int allowMega) {
		this.allowMega = allowMega;
	}
	public void setIoException(IOException ioException) {
		this.ioException = ioException;
	}
	public IOException getIoException() {
		return ioException;
	}
}