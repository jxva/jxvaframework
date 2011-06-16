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

import com.jxva.dao.Jdbc;
import com.jxva.dao.entity.Environment;
import com.jxva.tool.ui.CodeGenerateUI;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:51:56 by Jxva
 */
public class GenerateConfig {
	private Jdbc jdbc;
	private CodeGenerateUI ui;
	private Environment env;
	public Environment getEnv() {
		return env;
	}
	public void setEnv(Environment env) {
		this.env = env;
	}
	private String rootPath;
	private String packagePath;
	public Jdbc getJdbc() {
		return jdbc;
	}
	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}
	public CodeGenerateUI getUi() {
		return ui;
	}
	public void setUi(CodeGenerateUI ui) {
		this.ui = ui;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
}	
