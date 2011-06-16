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

import com.jxva.tool.ui.CodeGenerateUI;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:53:41 by Jxva
 */
public class JDBCUtil {
	public static String getDriverUrl(CodeGenerateUI ui){
		String dbmsUrl=DBConst.DBMS_URL.get(ui.cmbTypes.getText());
		dbmsUrl=dbmsUrl.replaceAll("HOST",ui.txtHostname.getText());
		dbmsUrl=dbmsUrl.replaceAll("DATABASE",ui.txtDatabaseName.getText());
		return dbmsUrl;
	}
}
