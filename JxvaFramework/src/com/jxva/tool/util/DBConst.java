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
package com.jxva.tool.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jxva.dao.entity.DBType;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:53:35 by Jxva
 */
public class DBConst {
	
	public static final String IBM_DB2_JCC	="IBM DB2 (jcc)";
	public static final String IBM_DB2_NET	="IBM DB2 (net)";
	public static final String IBM_DB2_APP	="IBM DB2 (app)";
	public static final String ORACLE		="ORACLE";
	public static final String SQLSERVER	="SQL Server";
	public static final String MYSQL		="MySQL";
	public static final String DERBY		="Derby(JavaDB)";
	public static final String HSQLDB		="HSQLDB";
	public static final String H2			="H2";
	
	public static final Map<String, String> DBMS_DRIVER = new HashMap<String, String>();
	public static final Map<String, String> DBMS_URL = new HashMap<String, String>();
	public static final Map<String, String> DBMS_DIALECT = new LinkedHashMap<String, String>();
	public static final Map<String, String> HIBERNATE_DIALECT=new HashMap<String, String>();
	static {
		DBMS_DRIVER.put(IBM_DB2_JCC, "com.ibm.db2.jcc.DB2Driver");
		DBMS_DRIVER.put(IBM_DB2_NET, "COM.ibm.db2.jdbc.net.DB2Driver");
		DBMS_DRIVER.put(IBM_DB2_APP, "COM.ibm.db2.jdbc.app.DB2Driver");
		DBMS_DRIVER.put(ORACLE, "oracle.jdbc.driver.OracleDriver");
		DBMS_DRIVER.put(SQLSERVER,"com.microsoft.sqlserver.jdbc.SQLServerDriver");
		DBMS_DRIVER.put(MYSQL, "com.mysql.jdbc.Driver");
		DBMS_DRIVER.put(DERBY, "org.apache.derby.jdbc.ClientDriver");
		DBMS_DRIVER.put(HSQLDB, "org.hsqldb.jdbcDriver");
		DBMS_DRIVER.put(H2,"org.h2.Driver");

		DBMS_URL.put(IBM_DB2_JCC, "jdbc:db2://HOST:50000/DATABASE");
		DBMS_URL.put(IBM_DB2_NET, "jdbc:db2://HOST:6789/DATABASE");
		DBMS_URL.put(IBM_DB2_APP, "jdbc:db2:DATABASE");
		DBMS_URL.put(ORACLE, "jdbc:oracle:thin:@HOST:1521:DATABASE");
		DBMS_URL.put(SQLSERVER,"jdbc:sqlserver://HOST:1433;DatabaseName=DATABASE");
		DBMS_URL.put(MYSQL, "jdbc:mysql://HOST:3306/DATABASE?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8");
		DBMS_URL.put(DERBY, "jdbc:derby://HOST:1527/DATABASE;create=true");
		DBMS_URL.put(HSQLDB, "jdbc:hsqldb:hsql://HOST/DATABASE");
		DBMS_URL.put(H2,"jdbc:h2:tcp://HOST:9101/~/DATABASE");
		
		DBMS_DIALECT.put("Select DBMS", "");
		DBMS_DIALECT.put(IBM_DB2_JCC, DBType.DB2.name());
		DBMS_DIALECT.put(IBM_DB2_NET, DBType.DB2.name());
		DBMS_DIALECT.put(IBM_DB2_APP, DBType.DB2.name());
		DBMS_DIALECT.put(ORACLE, DBType.ORACLE.name());
		DBMS_DIALECT.put(SQLSERVER, DBType.SQLSERVER.name());
		DBMS_DIALECT.put(MYSQL, DBType.MYSQL.name());
		DBMS_DIALECT.put(DERBY, DBType.DERBY.name());
		DBMS_DIALECT.put(HSQLDB, DBType.HSQLDB.name());
		DBMS_DIALECT.put(H2, DBType.H2.name());
		
		HIBERNATE_DIALECT.put(IBM_DB2_JCC, "org.hibernate.dialect.DB2Dialect");
		HIBERNATE_DIALECT.put(IBM_DB2_NET, "org.hibernate.dialect.DB2Dialect");
		HIBERNATE_DIALECT.put(IBM_DB2_APP, "org.hibernate.dialect.DB2Dialect");
		HIBERNATE_DIALECT.put(ORACLE, "org.hibernate.dialect.OracleDialect");
		HIBERNATE_DIALECT.put(SQLSERVER, "org.hibernate.dialect.SQLServerDialect");
		HIBERNATE_DIALECT.put(MYSQL, "org.hibernate.dialect.MySQLDialect");
		HIBERNATE_DIALECT.put(DERBY, "org.hibernate.dialect.DerbyDialect");
		HIBERNATE_DIALECT.put(HSQLDB, "org.hibernate.dialect.HSqlDBDialect");
		HIBERNATE_DIALECT.put(H2, "org.hibernate.dialect.H2");
	}

	public static String[] getDbms() {
		Object[] objs = DBMS_DIALECT.keySet().toArray();
		String[] s = new String[objs.length];
		for (int i = 0; i < objs.length; i++) {
			s[i] = (String) objs[i];
		}
		return s;
	}
}
