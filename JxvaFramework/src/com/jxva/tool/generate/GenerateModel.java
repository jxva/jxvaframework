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
 *
 */
package com.jxva.tool.generate;

import java.io.File;

import com.jxva.dao.Jdbc;
import com.jxva.log.Logger;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.tool.tpl.ModelTpl;
import com.jxva.util.ModelUtil;
import com.jxva.util.NIOUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:52:42 by Jxva
 */
public class GenerateModel implements Generate{
	private static final Logger log=Logger.getLogger(GenerateModel.class);
	public void generate(GenerateConfig gc) throws Exception {
		
		
		Jdbc jdbc=gc.getJdbc();
		
		String modelPath=gc.getPackagePath()+"model/";
		
		File path = new File(modelPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		String packageName=gc.getUi().txtPackageName.getText();
		String ignorePrefix=gc.getUi().txtIgnorePrefix.getText();
		String[] tableViews=gc.getUi().listTableViews.getSelection();
		
		for(int i=0;i<tableViews.length;i++){
			String tblName=tableViews[i];
			String className=getClassName(ignorePrefix,tblName);
			String fileName=className + ".java";
			String modelTpl=ModelTpl.getTpl(packageName,tblName,className,ignorePrefix,jdbc);
			
			NIOUtil.write(new File(modelPath + fileName),modelTpl);
			log.info(packageName +".model."+fileName+" generate success.");
		}
	}
	
	private static String getClassName(String ignorePrefix,String tblName){
		if(!ignorePrefix.equals("")){
			tblName=tblName.replaceAll(ignorePrefix,"");
		}
		return ModelUtil.getClassName(tblName);
	}
	
}
