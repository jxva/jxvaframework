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
package com.jxva.tool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.jxva.tool.ui.CodeGenerateUI;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 11:47:27 by Jxva
 */
public class Toolkit extends Shell {

	private TabFolder tabFolder;

	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Toolkit tool = new Toolkit(display,SWT.SHELL_TRIM);
			Monitor primary = tool.getMonitor();
		       Rectangle bounds = primary.getBounds ();
		       Rectangle rect = tool.getBounds ();
		       int x = bounds.x + (bounds.width - rect.width) / 2;
		       int y = bounds.y + (bounds.height - rect.height) / 2;
		       if (x < 0)
		           x = 0;
		       if (y < 0)
		           y = 0;
		    tool.setLocation (x, y);
		    //通过函数Shell.setAlpha(int alpha)设置窗口的透明度,alpha取值0到255,0为全透明。
		    //透明窗口的实现需要所在系统的支持,在不支持的系统下setAlpha会被忽略。
		    //tool.setAlpha(240);
		    //全屏模式,Shell.setFullScreen(true)
		    tool.open();
			tool.layout();
			while (!tool.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Toolkit(Display display, int style) {
		super(display, style);		
		createContents();
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.LEADING).add(tabFolder, GroupLayout.PREFERRED_SIZE,536, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.LEADING).add(tabFolder, GroupLayout.PREFERRED_SIZE,382, Short.MAX_VALUE));
		setLayout(groupLayout);
		pack();
	}

	protected void createContents() {
		setText("Automatic Generate Toolkit for Jxva Framework");

		tabFolder = new TabFolder(this, SWT.NONE);

		final TabItem tabItemCodeGenerate = new TabItem(tabFolder, SWT.NONE);
		tabItemCodeGenerate.setToolTipText("VO Generator and SQL Export");
		tabItemCodeGenerate.setText("Code Generate");

		final CodeGenerateUI codeGenerateUI = new CodeGenerateUI(tabFolder, SWT.NONE);
		tabItemCodeGenerate.setControl(codeGenerateUI);
		
		
		
		//final TabItem tabItemLicenseGenerate = new TabItem(tabFolder, SWT.NONE);
		//tabItemLicenseGenerate.setText("License Generate");

		//final LicenseGenerateUI licenseGenerateUI = new LicenseGenerateUI(tabFolder, SWT.NONE);
		//tabItemLicenseGenerate.setControl(licenseGenerateUI);
		
		
		/**
		final TabItem sqlCommandTabItem = new TabItem(tabFolder, SWT.NONE);
		sqlCommandTabItem.setText("SQL Command");

		final SqlExecute composite_sqlexecute = new SqlExecute(tabFolder, SWT.NONE);
		sqlCommandTabItem.setControl(composite_sqlexecute);

		final TabItem hmacmd5TabItem = new TabItem(tabFolder, SWT.NONE);
		hmacmd5TabItem.setText("HmacMd5");

		final HmacMd5 composite_hmacmd5 = new HmacMd5(tabFolder, SWT.NONE);
		hmacmd5TabItem.setControl(composite_hmacmd5);
		
		*/
	}
	
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
