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
package com.jxva.tool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.jxva.dao.Jdbc;
import com.jxva.dao.connection.ConnectionProviderFactory;
import com.jxva.dao.entity.Environment;
import com.jxva.dao.entry.TableEntry;
import com.jxva.dao.jdbc.ConnectionHolder;
import com.jxva.log.Logger;
import com.jxva.tool.ui.CodeGenerateUI;
import com.jxva.tool.util.DBConst;
import com.jxva.tool.util.JDBCUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:51:27 by Jxva
 */
public class JDBCWarper{

	private static final Logger log=Logger.getLogger(JDBCWarper.class);

	private Connection conn;
	private CodeGenerateUI ui;
	private Environment env;

	
	public JDBCWarper(CodeGenerateUI ui){
		this.ui=ui;
	}
	
	public Jdbc getJdbc() throws Exception{
		env=createEnvironment();
		Class.forName(env.getDriverClass());
		conn = DriverManager.getConnection(env.getUrl(),env.getUsername(),env.getPassword());
		ConnectionHolder.init(ConnectionProviderFactory.newConnectionProvider());
		return new Jdbc(ConnectionHolder.getConnection());
	}
	
	public boolean isConnectioned(){
		try{
			Jdbc jdbc=getJdbc();
			if(ui.cmbSchemas.getItemCount()==0){
				List<String> list=jdbc.getSchemas();
				String[] schemas=new String[list.size()];
				for(int i=0;i<list.size();i++){
					schemas[i]=list.get(i);
				}
				ui.cmbSchemas.setItems(schemas);
				ui.cmbSchemas.select(0);
			}
			
			String[] tableView=new String[2];
			tableView[0]=ui.chkTables.getSelection()?"TABLE":"";
			tableView[1]=ui.chkViews.getSelection()?"VIEW":"";
			List<TableEntry> list=jdbc.getTables(jdbc.getConnection().getCatalog(),ui.cmbSchemas.getText(),null,tableView);

			String[] tableViews=new String[list.size()];
			for(int i=0;i<list.size();i++){
				tableViews[i]=list.get(i).getTableName();
			}
			ui.listTableViews.setItems(new String[]{""});
			ui.listTableViews.setItems(tableViews);
			close();
			ui.txtTipInfo.setText("Database Connection Success.");
			log.info("Database Connection Success.");
			return true;
		}catch(Exception ex){
			log.trace(ex);
			ex.printStackTrace();
			ui.txtTipInfo.setText("Error:"+ex);
			return false;
		}
	}
	
	public void close() throws SQLException{
		conn.close();
	}
	public Environment getEnvironment(){
		if(env==null){
			env=createEnvironment();
		}
		return env;
	}
	
	private Environment createEnvironment(){
		Environment env=new JDBCEnvironment();
		env.setDbType(DBConst.DBMS_DIALECT.get(ui.cmbTypes.getText()));
		env.setDriverClass(DBConst.DBMS_DRIVER.get(ui.cmbTypes.getText()));
		env.setPassword(ui.txtPassword.getText());
		env.setUrl(JDBCUtil.getDriverUrl(ui));
		env.setUsername(ui.txtUsername.getText());
		env.setShowSql(true);
		env.setPool("jdbc");
		env.setMaxSize(10);
		env.setMinSize(2);
		env.setOvertime(3600L);
		return env;
	}
}
