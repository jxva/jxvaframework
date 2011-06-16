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
package com.jxva.tool.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jxva.dao.Jdbc;
import com.jxva.log.Logger;
import com.jxva.tool.handler.CodeGenerateHandler;
import com.jxva.tool.jdbc.JDBCWarper;
import com.jxva.tool.ui.CodeGenerateUI;
import com.jxva.tool.util.JDBCUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:50:48 by Jxva
 */
public class CodeGenerateController{
	
	private static final Logger log=Logger.getLogger(CodeGenerateController.class);

	private CodeGenerateUI ui;

	public CodeGenerateController(CodeGenerateUI ui){
		this.ui=ui;
	}
	
	public SelectionAdapter cmbTypesListener=new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if(!onChangeByCmbTypes()){
				return;
			}
		}
	};
		
	public SelectionAdapter btnSavePathListener=new SelectionAdapter() {
		public void widgetSelected(SelectionEvent arg0) {
			DirectoryDialog d	=new DirectoryDialog(new Shell(), SWT.OPEN);
			d.setFilterPath("C:/");
			String selected =d.open();
            if (selected!=null){
            	ui.txtSavePath.setText(selected);
            }else{
                return;
            }
		}
	};
	
	public KeyAdapter onchangeForCmbTypesListener= new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		}
		public void keyReleased(KeyEvent e) {
			if(!onChangeByCmbTypes()){
				return;
			}
		}
	};
	
	public SelectionAdapter btnConnectionListener=new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if(!isValid())return;
			if(!new JDBCWarper(ui).isConnectioned()){
				ui.btnGenerate.setEnabled(false);
				return;
			}else{
				ui.btnGenerate.setEnabled(true);
			}
		}
	};
	
	public SelectionAdapter btnGenerateListener=new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//if(!isValid())return;
			if(isEmpty(ui.txtSavePath,"Please select save path for source code file."))return;
			if(isEmpty(ui.txtPackageName,"Please input package name."))return;
			if(ui.listTableViews.getSelection().length==0){
				ui.txtTipInfo.setText("Select a table or view at lease.");
				ui.listTableViews.forceFocus();
				return;
			}
			JDBCWarper jdbcWarper=new JDBCWarper(ui);
			//if(!jdbcWarper.isConnectioned())return;
			try{
				long start=System.currentTimeMillis();
				Jdbc jdbc=jdbcWarper.getJdbc();
				CodeGenerateHandler handler=new CodeGenerateHandler(ui,jdbc,jdbcWarper.getEnvironment());
				handler.generateModel();
				handler.generateDao();
				handler.generateAction();
				handler.generateHibernate();
				handler.generateJxvaProperites();
				handler.generateJxvaMvcXml();
				jdbcWarper.close();
				long end=System.currentTimeMillis();
				ui.txtTipInfo.setText("Code generate success, cost time: "+(end-start)+" ms");
				ui.btnGenerate.setEnabled(false);
			}catch(Exception ex){
				ex.printStackTrace();
				log.trace(ex);
				ui.txtTipInfo.setText("Error:"+ex);
				return;
			}
		}
	};
	
	private boolean isValid(){
		ui.txtTipInfo.setText("Database Connectioning,please waiting...");
		if(!onChangeByCmbTypes())return false;
		if(isEmpty(ui.txtHostname,"Hostname is empty."))return false;
		if(isEmpty(ui.txtUsername,"Username is empty."))return false;
		if(isEmpty(ui.txtPassword,"Password is empty."))return false;	
		if(isEmpty(ui.txtDatabaseName,"Database Name is empty."))return false;	
		boolean b = !ui.chkTables.getSelection()&&!ui.chkViews.getSelection();
		if(b){
			ui.txtTipInfo.setText("Choose tables or views at lease.");
			ui.listTableViews.setItems(new String[]{""});
			ui.chkTables.forceFocus();
			return false;
		}
		return true;
	}
	
	private boolean isEmpty(Text text,String msg){
		if(text.getText().trim().equals("")){
			ui.txtTipInfo.setText(msg);
			text.forceFocus();
			return true;
		}
		return false;
	}
	
	private boolean onChangeByCmbTypes(){
		if(ui.cmbTypes.getSelectionIndex()==0){
			ui.cmbTypes.forceFocus();
			ui.txtTipInfo.setText("Please select a database type.");
			return false;
		}
		ui.txtTipInfo.setText(JDBCUtil.getDriverUrl(ui));
		return true;
	}
}
