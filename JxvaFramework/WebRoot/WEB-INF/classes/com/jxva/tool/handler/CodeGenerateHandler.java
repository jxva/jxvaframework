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
package com.jxva.tool.handler;

import java.io.File;

import com.jxva.dao.Jdbc;
import com.jxva.dao.entity.Environment;
import com.jxva.tool.generate.GenerateAction;
import com.jxva.tool.generate.GenerateDao;
import com.jxva.tool.generate.GenerateHibernateCfgXml;
import com.jxva.tool.generate.GenerateHibernateHbmXml;
import com.jxva.tool.generate.GenerateJxvaMvcXml;
import com.jxva.tool.generate.GenerateJxvaDaoPropertites;
import com.jxva.tool.generate.GenerateModel;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.tool.ui.CodeGenerateUI;
import com.jxva.tool.util.ToolUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:51:11 by Jxva
 */
public class CodeGenerateHandler {
	
	private CodeGenerateUI ui;
	private GenerateConfig gc;
	
	public CodeGenerateHandler(CodeGenerateUI ui,Jdbc jdbc,Environment env){
		this.ui=ui;
		init(jdbc,env);
	}
	
	private void init(Jdbc jdbc,Environment env){
		String rootPath=ToolUtil.getRealSavePath(ui.txtSavePath.getText(),"");
		String packagePath=ToolUtil.getRealSavePath(ui.txtSavePath.getText(),ui.txtPackageName.getText());
		File path = new File(packagePath);
		if (!path.exists()) {
			path.mkdirs();
		}
		gc=new GenerateConfig();
		gc.setJdbc(jdbc);
		gc.setPackagePath(packagePath);
		gc.setRootPath(rootPath);
		gc.setUi(ui);
		gc.setEnv(env);
	}
	
	/**
	 * generate Hibernate Configuration and Mapping File 
	 * files: /hibernate.cfg.xml and /hibernate.hbm.xml
	 */
	public void generateHibernate() throws Exception{
		if(ui.chkHibernate.getSelection()){
			new GenerateHibernateCfgXml().generate(gc);
			new GenerateHibernateHbmXml().generate(gc);
		}
	}
	
	/**
	 * generate Database Connection Configuration Properites File
	 * files: /jxva.properites
	 */
	public void generateJxvaProperites() throws Exception{
		if(ui.chkJxvaProperites.getSelection()){
			new GenerateJxvaDaoPropertites().generate(gc);
		}
	}
	
	/**
	 * generate Data Access Object File
	 * files: /package/dao/*DAO.java
	 */
	public void generateDao() throws Exception{
		if(ui.chkDAO.getSelection()){
			new GenerateDao().generate(gc);
		}
	}
	
	/**
	 * generate Action for MVC
	 * files: /package/action/*Action.java
	 */
	public void generateAction() throws Exception{
		if(ui.chkAction.getSelection()){
			new GenerateAction().generate(gc);
		}
	}
	
	/**
	 * generate Xml Configuration File for MVC's Actoin
	 * files: /jxva-mvc.xml
	 */
	public void generateJxvaMvcXml() throws Exception{
		if(ui.chkJxvaMvcXml.getSelection()){
			new GenerateJxvaMvcXml().generate(gc);
		}
	}
	
	/**
	 * generate Xml Configuration File for MVC's Actoin
	 * files: /package/model/*.java
	 */
	public void generateModel() throws Exception{
		new GenerateModel().generate(gc);
	}
}
