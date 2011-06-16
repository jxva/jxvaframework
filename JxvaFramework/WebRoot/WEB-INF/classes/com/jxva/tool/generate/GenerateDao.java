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
package com.jxva.tool.generate;

import java.io.File;
import java.util.List;

import com.jxva.dao.Jdbc;
import com.jxva.dao.jdbc.Column;
import com.jxva.log.Logger;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.tool.tpl.DaoTpl;
import com.jxva.util.ModelUtil;
import com.jxva.util.NIOUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 12:58:38 by Jxva
 */
public class GenerateDao implements Generate {
	
	private static final Logger log=Logger.getLogger(GenerateDao.class);
	
	/* (non-Javadoc)
	 * @see com.jxva.tool.generate.Generate#generate(com.jxva.tool.model.GenerateConfig)
	 */
	public void generate(GenerateConfig gc) throws Exception {
		Jdbc jdbc=gc.getJdbc();
		
		String daoPath=gc.getPackagePath()+"dao/";
		
		File path = new File(daoPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		String packageName=gc.getUi().txtPackageName.getText();
		String ignorePrefix=gc.getUi().txtIgnorePrefix.getText();
		String[] tableViews=gc.getUi().listTableViews.getSelection();
		
		for(int i=0;i<tableViews.length;i++){
			String tblName=tableViews[i];
			String className=getClassName(ignorePrefix,tblName);
			String primaryKey=getPrimaryKey(jdbc,tblName);
			String fileName=className + "Dao.java";
			String daoTpl=DaoTpl.getTpl(packageName,className,primaryKey,"");
			NIOUtil.write(new File(daoPath + fileName),daoTpl);
			
			log.info(packageName +".dao."+fileName+" generate success.");
		}
	}
	
	private String getPrimaryKey(Jdbc jdbc,String tblName){
		List<Column> keys=jdbc.getPrimaryKeyColumns(tblName);
		if(keys.isEmpty()){
			return jdbc.getAllColumns(tblName).get(0).getName();
		}else{
			return keys.get(0).getName();
		}
	}

	private String getClassName(String ignorePrefix,String tblName){
		if(!ignorePrefix.equals("")){
			tblName=tblName.replaceAll(ignorePrefix,"");
		}
		return ModelUtil.getClassName(tblName);
	}
}
