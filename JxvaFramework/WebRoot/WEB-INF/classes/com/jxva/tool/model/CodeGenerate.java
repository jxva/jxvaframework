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
package com.jxva.tool.model;

import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:51:46 by Jxva
 */
public class CodeGenerate {
	
	private Text txtTipInfo;
	private	Text txtHostname;
	private Text txtUsername;
	private Text txtPassword;
	private Text txtDatabaseName;
	
	public Text getTxtTipInfo() {
		return txtTipInfo;
	}

	public void setTxtTipInfo(Text txtTipInfo) {
		this.txtTipInfo = txtTipInfo;
	}

	public Text getTxtHostname() {
		return txtHostname;
	}

	public void setTxtHostname(Text txtHostname) {
		this.txtHostname = txtHostname;
	}

	public Text getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(Text txtUsername) {
		this.txtUsername = txtUsername;
	}

	public Text getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(Text txtPassword) {
		this.txtPassword = txtPassword;
	}

	public Text getTxtDatabaseName() {
		return txtDatabaseName;
	}

	public void setTxtDatabaseName(Text txtDatabaseName) {
		this.txtDatabaseName = txtDatabaseName;
	}
}
