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
import com.jxva.log.Logger;
import com.jxva.tool.model.GenerateConfig;
import com.jxva.util.NIOUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:52:26 by Jxva
 */
public class GenerateJxvaDaoPropertites implements Generate{

	private static final Logger log=Logger.getLogger(GenerateJxvaDaoPropertites.class);
	
	public void generate(GenerateConfig gc) throws Exception {
		StringBuilder sb = new StringBuilder();
		Environment env=gc.getEnv();
		sb.append("#DB2|SQLSERVER|MYSQL|ORACLE|DERBY|HSQLDB\r\n");
		sb.append("jxva.datasource.db_type = " + env.getDbType() + "\r\n");
		sb.append("\r\n");
		sb.append("#debug output sql  true | false\r\n");
		sb.append("jxva.datasource.show_sql = "+env.getShowSql()+"\r\n");
		sb.append("\r\n");	
		sb.append("#defined increment table for saving user-defined increment field\r\n");
		sb.append("jxva.datasource.sys_table= tbl_increment\r\n");
		sb.append("\r\n");
		sb.append("#save models's package name\r\n");
		sb.append("jxva.datasource.pkg_name="+gc.getUi().txtPackageName.getText()+".model\r\n");
		sb.append("\r\n");
		sb.append("#connection pool overtime (unit:second)\r\n");
		sb.append("jxva.datasource.overtime="+env.getOvertime()+"\r\n");
		sb.append("\r\n");
		sb.append("#jdbc|proxool|c3p0|dbcp|jndi\r\n");
		sb.append("jxva.datasource.pool = "+env.getPool()+"\r\n");
		sb.append("\r\n");
		sb.append("jxva.datasource.username = " + env.getUsername() + "\r\n");
		sb.append("jxva.datasource.password = " + env.getPassword() + "\r\n");
		sb.append("\r\n");
		sb.append("jxva.datasource.max_size = "+env.getMaxSize()+"\r\n");
		sb.append("jxva.datasource.min_size = "+env.getMinSize()+"\r\n");
		sb.append("\r\n");
		sb.append("jxva.datasource.asia = www.jxva.com\r\n");
		sb.append("jxva.datasource.jndi = java:comp/env/jdbc/test\r\n");
		sb.append("\r\n");
		sb.append("jxva.datasource.driver_class = " + env.getDriverClass() + "\r\n");
		sb.append("jxva.datasource.url = "+ env.getUrl()+ "\r\n");
		sb.append("\r\n");
		sb.append("#----------------------------------------\r\n");
		sb.append("\r\n");
		sb.append("#DB2\r\n");
		sb.append("#jxva.datasource.driver_class = com.ibm.db2.jcc.DB2Driver\r\n");
		sb.append("#jxva.datasource.url = jdbc:db2://127.0.0.1:50000/test\r\n");
		sb.append("\r\n");
		sb.append("#MYSQL\r\n");
		sb.append("#jxva.datasource.driver_class = com.mysql.jdbc.Driver\r\n");
		sb.append("#jxva.datasource.url = jdbc:mysql://127.0.0.1:3306/test?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8\r\n");
		sb.append("\r\n");
		sb.append("#MSSQL\r\n");
		sb.append("#jxva.datasource.driver_class = com.microsoft.sqlserver.jdbc.SQLServerDriver\r\n");
		sb.append("#jxva.datasource.url = jdbc:sqlserver://127.0.0.1:1433;DatabaseName=test;SelectMethod=direct\r\n");
		sb.append("\r\n");
		sb.append("#ORACLE\r\n");
		sb.append("#jxva.datasource.driver_class = oracle.jdbc.driver.OracleDriver\r\n");
		sb.append("#jxva.datasource.url = jdbc:oracle:thin:@127.0.0.1:1521:test\r\n");
		sb.append("\r\n");
		sb.append("#DERBY\r\n");
		sb.append("#jxva.datasource.driver_class = org.apache.derby.jdbc.ClientDriver\r\n");
		sb.append("#jxva.datasource.url = jdbc:derby://127.0.0.1:1527/test;create=true\r\n");
		sb.append("\r\n");
		sb.append("#HSQLDB\r\n");
		sb.append("#jxva.datasource.driver_class = org.hsqldb.jdbcDriver\r\n");
		sb.append("#jxva.datasource.url = jdbc:hsqldb:hsql://127.0.0.1/test\r\n");
		
		final String fileName=gc.getRootPath() + "jxva-dao.properties";
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		NIOUtil.write(file,sb.toString());
		
		log.info(fileName+" generate success.");
	}

}
