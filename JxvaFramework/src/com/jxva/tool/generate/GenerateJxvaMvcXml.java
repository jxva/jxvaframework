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

import com.jxva.entity.Encoding;
import com.jxva.log.Logger;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.util.ModelUtil;
import com.jxva.util.NIOUtil;
import com.jxva.util.XmlUtil;
import com.jxva.xml.XmlSerializer;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 16:58:39 by Jxva
 */
public class GenerateJxvaMvcXml implements Generate {
	
	private static final Logger log=Logger.getLogger(GenerateJxvaMvcXml.class);

	/* (non-Javadoc)
	 * @see com.jxva.tool.generate.Generate#generate(com.jxva.tool.model.GenerateConfig)
	 */
	public void generate(GenerateConfig gc) throws Exception {
		final String fileName=gc.getRootPath()+"jxva-mvc.xml";
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		
        String[] tableViews=gc.getUi().listTableViews.getSelection();

        String ignorePrefix=gc.getUi().txtIgnorePrefix.getText();
        String packageName=gc.getUi().txtPackageName.getText()+".action.";
		
        StringBuilder sb=new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
        sb.append("<jxva>\r\n\r\n");
        sb.append("\t<package namespace=\"default\">\r\n");
        
        
		for(int i=0;i<tableViews.length;i++){
			String tblName=(String)tableViews[i];
			String className=getClassName(ignorePrefix,tblName);
			String fieldName=ModelUtil.getFieldName(className);
			sb.append("\t\t<action name=\""+fieldName+"\" class=\""+packageName+className+"Action\" />\r\n");
		}
		sb.append("\t</package>\r\n\r\n");
		sb.append("</jxva>\r\n");
		NIOUtil.write(new File(fileName),sb.toString());
		log.info(fileName+" generate success.");
	}
	
	protected void generateByXml(GenerateConfig gc)throws Exception {
		final String fileName=gc.getRootPath()+"jxva-mvc.xml";
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		
        String[] tableViews=gc.getUi().listTableViews.getSelection();

        String ignorePrefix=gc.getUi().txtIgnorePrefix.getText();
        String packageName=gc.getUi().txtPackageName.getText()+".action.";
		
		XmlSerializer xml = XmlUtil.getXmlSerializer(file,Encoding.UTF_8);
        xml.startTag("jxva");        
        XmlSerializer packageNode=xml.startTag("package");
		packageNode.addAttribute("namespace","default");
        
		for(int i=0;i<tableViews.length;i++){
			String tblName=(String)tableViews[i];
			String className=getClassName(ignorePrefix,tblName);
			XmlSerializer action=packageNode.startTag("action");
			action.addAttribute("name",className);
			action.addAttribute("class",packageName+className+"Action");
			packageNode.endTag("action");
		}
		packageNode.endTag("package");
		xml.endTag("jxva");
		xml.endDocument();
		
		log.info(fileName+" generate success.");
	}
	
	private String getClassName(String ignorePrefix,String tblName){		
		if(!ignorePrefix.equals("")){
			tblName=tblName.replaceAll(ignorePrefix,"");
		}
		return ModelUtil.getClassName(tblName);
	}
}
