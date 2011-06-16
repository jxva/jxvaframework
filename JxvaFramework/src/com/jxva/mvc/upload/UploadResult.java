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

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-06-30 18:14:52 by Jxva
 */
public abstract class UploadResult {
	public static final int UPLOAD_SUCCESS=0;//成功
	public static final int UPLOAD_FILE_TOO_LARGE=1;//文件太大
	public static final int UPLOAD_FILE_EMPTY=2;//内容为空
	public static final int UPLOAD_UNSUPPORT_EXT=3;//不支持的文件后缀
	public static final int UPLOAD_BAD_FORMAT=4;//格式不正确,不可解析
	public static final int UPLOAD_EXCEPTION=5;//系统异常
}
