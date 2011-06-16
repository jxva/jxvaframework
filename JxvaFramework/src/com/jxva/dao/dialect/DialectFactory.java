
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
package com.jxva.dao.dialect;

import com.jxva.dao.Dialect;
import com.jxva.dao.DialectException;
import com.jxva.dao.entity.DBType;
import com.jxva.dao.entity.Environment;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:54:10 by Jxva
 */
public final class DialectFactory {
	public static Dialect createDialect(Environment env){
		String dbtype=env.getDbType();
		if(dbtype.equalsIgnoreCase(DBType.MYSQL.toString())){
			return new MySQLDialect();
		}else if(dbtype.equalsIgnoreCase(DBType.SQLSERVER.toString())){
			return new SQLServerDialect();
		}else if(dbtype.equalsIgnoreCase(DBType.ORACLE.toString())){
			return new OracleDialect();
		}else if(dbtype.equalsIgnoreCase(DBType.DB2.toString())){
			return new DB2Dialect();
		}else if(dbtype.equalsIgnoreCase(DBType.DERBY.toString())){
			return new DerbyDialect();
		}else if(dbtype.equalsIgnoreCase(DBType.HSQLDB.toString())){
			return new HSQLDialect();
		}else{
			throw new DialectException("undefine or unsupport database dbtype.");
		}
	}
}
