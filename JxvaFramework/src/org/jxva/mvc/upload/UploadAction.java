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
package org.jxva.mvc.upload;

import java.io.File;
import java.io.IOException;

import com.jxva.entity.Path;
import com.jxva.mvc.Action;
import com.jxva.mvc.upload.Upload;
import com.jxva.mvc.upload.UploadFile;
import com.jxva.mvc.upload.UploadMsg;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-06-30 17:55:15 by Jxva
 */
public class UploadAction extends Action{


	public String execute(){
		try{			
			Upload upload = new Upload(request,Path.APP_PATH);
			UploadMsg msg = upload.save(2048,true);
			String filename=null;
			if (msg.isSuccessful()) {
				UploadFile upFile = upload.getUploadedFile("Filedata");
				File file=upFile.getUploadedFile();
				filename = file.getName();
			}
			return filename;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

}
