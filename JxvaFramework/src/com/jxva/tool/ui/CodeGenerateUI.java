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
package com.jxva.tool.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.jxva.entity.Path;
import com.jxva.tool.controller.CodeGenerateController;
import com.jxva.tool.util.DBConst;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:52:49 by Jxva
 */
public class CodeGenerateUI extends Composite{
	
	//为了简化界面的调整,这里直接将全局变量设置为public
	//原则应该是通过model CodeGenerate来操作的
	public Text txtTipInfo;
	public Text txtHostname;
	public Text txtUsername;
	public Text txtPassword;
	public Text txtDatabaseName;
	public Text txtSavePath;
	public Text txtIgnorePrefix;
	public Text txtPackageName;
	
	public Combo cmbTypes;
	public Combo cmbSchemas;
	
	public Button chkHibernate;
	public Button chkJxvaProperites;
	public Button chkDAO;
	public Button chkAction;
	public Button chkJxvaMvcXml;
	
	public Button chkTables;
	public Button chkViews;
	public Button btnGenerate;
	
	public List listTableViews;
	


	
	private CodeGenerateController cgc;
		
	public CodeGenerateUI(Composite parent, int style) {
		super(parent, style);
		cgc=new CodeGenerateController(this);
		oneLineUI();
		twoLineUI();
		threeLineUI();
		fourLineUI();
		fiveLineUI();
		sixLineUI();
		
		//this.setTabList(new Control[] {combo_catalogs, combo_schemas, combo_dbTypes, text_hostName, text_dbAccount, text_dbPassword, text_dbName, checkbox_table, checkbox_view, button_connection, list_tableView, button_selectPath, text_prefix, text_savePath, text_packageName, text_tipInfo, checkbox_hibernate, checkbox_utf8, checkbox_dbconf, sqlExportButton, button_beanGen});
	}

	
	private void oneLineUI(){
		final Label labCatalogs = new Label(this, SWT.NONE);
		labCatalogs.setText("Catalogs:");
		labCatalogs.setBounds(5, 13, 50, 15);
		
		final Combo cmbCatalogs = new Combo(this, SWT.READ_ONLY);
		cmbCatalogs.setBounds(66, 10, 122, 21);

		final Label labSchemas= new Label(this, SWT.NONE);
		labSchemas.setText("Schemas:");
		labSchemas.setBounds(194, 13, 50, 15);

		cmbSchemas = new Combo(this, SWT.READ_ONLY);
		cmbSchemas.setBounds(250, 10, 113, 21);

		final Label labTypes = new Label(this, SWT.NONE);
		labTypes.setText("Types:");
		labTypes.setBounds(369, 13, 35, 15);

		cmbTypes = new Combo(this, SWT.READ_ONLY);		
		cmbTypes.setItems(DBConst.getDbms());
		cmbTypes.select(0);
		cmbTypes.setBounds(426, 10, 105, 21);
		cmbTypes.addSelectionListener(cgc.cmbTypesListener);
	}
	
	private void twoLineUI(){
		final Label labHostname = new Label(this, SWT.NONE);
		labHostname.setText("Hostname:");
		labHostname.setBounds(5, 44, 55, 15);

		txtHostname=new Text(this, SWT.BORDER);		
		txtHostname.setBounds(66, 41, 122, 21);
		txtHostname.addKeyListener(cgc.onchangeForCmbTypesListener);

		final Label labUsername = new Label(this, SWT.NONE);
		labUsername.setText("Username:");
		labUsername.setBounds(194, 44, 55, 15);

		txtUsername=new Text(this, SWT.BORDER);
		txtUsername.setBounds(250, 41, 113, 21);

		final Label labPassword = new Label(this, SWT.NONE);
		labPassword.setText("Password:");
		labPassword.setBounds(369, 44, 50, 15);

		txtPassword=new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setBounds(426, 41, 105, 21);
	}
	
	private void threeLineUI(){
		final Label labDatabaseName= new Label(this, SWT.NONE);
		labDatabaseName.setText("Database name:");
		labDatabaseName.setBounds(5, 74, 80, 15);

		txtDatabaseName=new Text(this, SWT.BORDER);
		txtDatabaseName.setBounds(96, 70, 153, 21);
		txtDatabaseName.addKeyListener(cgc.onchangeForCmbTypesListener);
		
		chkTables = new Button(this, SWT.CHECK);
		chkTables.setSelection(true);
		chkTables.setText("tables");
		chkTables.setBounds(260, 71, 50, 20);

		chkViews = new Button(this, SWT.CHECK);
		chkViews.setSelection(true);
		chkViews.setText("views");
		chkViews.setBounds(316, 71, 50, 20);

		final Button btnConnection = new Button(this, SWT.NONE);
		btnConnection.setText("Connection Database");
		btnConnection.setBounds(369, 70, 162, 22);
		btnConnection.addSelectionListener(cgc.btnConnectionListener);
	}
	
	private void fourLineUI(){
		
		listTableViews = new List(this, SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		listTableViews.setDragDetect(false);
		listTableViews.setBounds(5, 96, 526, 160);
		
		txtSavePath= new Text(this, SWT.BORDER);
		txtSavePath.setEnabled(false);
		txtSavePath.setBounds(5, 263, 403, 21);
		txtSavePath.setText(Path.WEB_INF_PATH);
		
		final Button btnSavePath= new Button(this, SWT.NONE);
		btnSavePath.setText("Select save path...");
		btnSavePath.setBounds(414, 262,117, 22);
		btnSavePath.addSelectionListener(cgc.btnSavePathListener);
	}
	
	private void fiveLineUI(){
		final Label labIgnorePrefix = new Label(this, SWT.NONE);
		labIgnorePrefix.setText("Ignore prefix:");
		labIgnorePrefix.setBounds(5, 296, 70, 15);

		txtIgnorePrefix = new Text(this, SWT.BORDER);
		txtIgnorePrefix.setText("tbl_");
		txtIgnorePrefix.setBounds(85, 293, 78, 21);
		
		
		final Label labPackageName= new Label(this, SWT.NONE);
		labPackageName.setText("Package name:");
		labPackageName.setBounds(169, 296, 75, 15);
		
		txtPackageName = new Text(this, SWT.BORDER);
		txtPackageName.setBounds(250, 293, 281, 21);
		

		txtTipInfo=new Text(this, SWT.READ_ONLY | SWT.BORDER);
		txtTipInfo.setEditable(false);
		Font font=new Font(Display.getCurrent(),"Arial",9, SWT.BOLD);
		txtTipInfo.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		txtTipInfo.setFont(font);
		txtTipInfo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		txtTipInfo.setText("This is a tip information output console.");
		txtTipInfo.setBounds(5, 322, 526, 21);
	}
	
	private void sixLineUI(){
		chkHibernate = new Button(this, SWT.CHECK);
		chkHibernate.setText("Hibernate");
		chkHibernate.setBounds(5, 352, 70, 20);

		chkJxvaProperites = new Button(this, SWT.CHECK);
		chkJxvaProperites.setSelection(true);
		chkJxvaProperites.setText("jxva.properties");
		chkJxvaProperites.setBounds(85, 352, 95, 20);

		chkDAO = new Button(this, SWT.CHECK);
		chkDAO.setSelection(true);
		chkDAO.setText("DAO");
		chkDAO.setBounds(190, 352, 45, 20);

		chkAction = new Button(this, SWT.CHECK);
		chkAction.setText("Action");
		chkAction.setBounds(250, 352, 55, 20);
		
		chkJxvaMvcXml = new Button(this, SWT.CHECK);
		chkJxvaMvcXml.setText("jxva-mvc.xml");
		chkJxvaMvcXml.setBounds(319, 352, 85, 20);
		
		btnGenerate = new Button(this, SWT.NONE);
		btnGenerate.setText("Generate Code");
		btnGenerate.setBounds(426, 351, 105, 22);
		btnGenerate.setEnabled(false);
		btnGenerate.addSelectionListener(cgc.btnGenerateListener);
	}
}
