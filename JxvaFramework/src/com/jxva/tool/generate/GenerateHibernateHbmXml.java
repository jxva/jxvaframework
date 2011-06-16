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
import java.util.Arrays;
import java.util.List;

import com.jxva.dao.Jdbc;
import com.jxva.dao.jdbc.Column;
import com.jxva.dao.type.SqlType;
import com.jxva.entity.Encoding;
import com.jxva.log.Logger;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.util.ModelUtil;
import com.jxva.util.XmlUtil;
import com.jxva.xml.XmlSerializer;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:52:33 by Jxva
 */
public class GenerateHibernateHbmXml implements Generate{
	
	private static final Logger log=Logger.getLogger(GenerateHibernateHbmXml.class);
	
	public void generate(GenerateConfig gc)throws Exception{
		final String fileName=gc.getRootPath()+"hibernate.hbm.xml";
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		
        XmlSerializer hbmxml = XmlUtil.getXmlSerializer(file,Encoding.UTF_8);
        hbmxml.addDocType("hibernate-mapping",
				"-//Hibernate/Hibernate Mapping DTD//EN",
				"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
        hbmxml.startTag("hibernate-mapping");
                
        String[] tableViews=gc.getUi().listTableViews.getSelection();
        Jdbc jdbc=gc.getJdbc();
        String ignorePrefix=gc.getUi().txtIgnorePrefix.getText();
        String packageName=gc.getUi().txtPackageName.getText();
        
		for(int i=0;i<tableViews.length;i++){
			String tblName=(String)tableViews[i];
			List<Column> pks=jdbc.getPrimaryKeyColumns(tblName);
			if(pks.isEmpty()){
				//throw new Exception("don't exist primary key");
			}
			String[] pkNames=new String[pks.size()];
			for(int p=0;pks!=null&&p<pks.size();p++){
				pkNames[p]=pks.get(p).getName();
			}
			String clsName;
			if(ignorePrefix.equals("")){
				clsName=ModelUtil.getClassName(tblName);
			}else{
				clsName=ModelUtil.getClassName(tblName.replaceAll(ignorePrefix,""));
			}
			XmlSerializer cls=hbmxml.startTag("class");
								
			cls.addAttribute("name",packageName+"."+clsName);
			cls.addAttribute("table",tblName);
				
			if(pks!=null&&pks.size()==1){
				XmlSerializer pkE=cls.startTag("id");					
				addPubProperty(pkE,pks.get(0));
				pkE.startTag("generator").addAttribute("class","assigned").endTag("generator");
				pkE.endTag("id");
			}
				
			if(pks!=null&&pks.size()>1){
				XmlSerializer compPk=cls.startTag("composite-id");
				for(int p=0;p<pks.size();p++){
					XmlSerializer property=compPk.startTag("key-property");
					addPubProperty(property,pks.get(p));
					property.addAttribute("length",Integer.toString(pks.get(p).getLength()));
					property.endTag("key-property");
				}
				compPk.endTag("composite-id");
			}

			List<Column> cols=gc.getJdbc().getAllColumns(tblName);
			for(int j=0;j<cols.size();j++){
				Column col=(Column)cols.get(j);
				if(pkNames==null||!Arrays.asList(pkNames).contains(col.getName())){
					XmlSerializer property=cls.startTag("property");
					addPubProperty(property,col);
					property.addAttribute("not-null",Boolean.toString(!col.isNullable()));
					property.addAttribute("length",Integer.toString(col.getLength()));
					property.endTag("property");
				}
			}	
			cls.endTag("class");
		}
		hbmxml.endTag("hibernate-mapping");
		hbmxml.endDocument();
		
		log.info(fileName+" generate success.");
	}
	
	private void addPubProperty(XmlSerializer property,Column column)throws Exception{
		property.addAttribute("name",ModelUtil.getFieldName(column.getName()));
		property.addAttribute("type",SqlType.get(column.getType()).getName());		
		property.addAttribute("column",column.getName());
	}
}
