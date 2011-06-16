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

import com.jxva.dao.entity.Environment;
import com.jxva.entity.Encoding;
import com.jxva.log.Logger;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.tool.util.DBConst;
import com.jxva.util.XmlUtil;
import com.jxva.xml.XmlSerializer;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:52:14 by Jxva
 */
public class GenerateHibernateCfgXml implements Generate{
	
	private static final Logger log=Logger.getLogger(GenerateHibernateCfgXml.class);
	
	public void generate(GenerateConfig gc)throws Exception{
		String fileName=gc.getRootPath()+"hibernate.cfg.xml";
		
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		XmlSerializer cfgxml =XmlUtil.getXmlSerializer(file,Encoding.UTF_8);
		cfgxml.addDocType("hibernate-configuration",
		        "-//Hibernate/Hibernate Configuration DTD 3.0//EN",
		        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd");
		cfgxml.startTag("hibernate-configuration");
		Environment env=gc.getEnv();
		XmlSerializer sf=cfgxml.startTag("session-factory");
		(sf.startTag("property")).addAttribute("name","connection.driver_class").setText(env.getDriverClass()).endTag("property");
		(sf.startTag("property")).addAttribute("name","connection.url").setText(env.getUrl()).endTag("property");
		(sf.startTag("property")).addAttribute("name","connection.username").setText(env.getUsername()).endTag("property");
		(sf.startTag("property")).addAttribute("name","connection.password").setText(env.getPassword()).endTag("property");
		
		(sf.startTag("property")).addAttribute("name","connection.pool_size").setText(env.getMaxSize().toString()).endTag("property");
		(sf.startTag("property")).addAttribute("name","dialect").setText(DBConst.HIBERNATE_DIALECT.get(gc.getUi().cmbTypes.getText())).endTag("property");
		(sf.startTag("property")).addAttribute("name","current_session_context_class").setText("thread").endTag("property");
		(sf.startTag("property")).addAttribute("name","cache.provider_class").setText("org.hibernate.cache.NoCacheProvider").endTag("property");
		(sf.startTag("property")).addAttribute("name","show_sql").setText("true").endTag("property");
		
		(sf.startTag("mapping")).addAttribute("resource","/hibernate.hbm.xml").endTag("mapping");
		
		sf.endTag("session-factory");
		cfgxml.endTag("hibernate-configuration");
		cfgxml.endDocument();
		
		log.info(fileName+" generate success.");
	}
}
