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
package com.jxva.dao.entity;

import java.util.ResourceBundle;

import com.jxva.log.Logger;
import com.jxva.util.StringUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:56:47 by Jxva
 */
public class Environment {

	private static final Logger log=Logger.getLogger(Environment.class);
	private String conf;	
	private ResourceBundle prop;
	
	//fot Automatic Generate Toolkit
	protected String dbType;
	protected String username;
	protected String password;
	protected String url;
	protected String driverClass;
	protected String pool;
	protected Integer maxSize;
	protected Integer minSize;
	protected Long overtime;
	protected Boolean showSql;

	
	private static Environment self;
	
	public Environment(){
		//throw new UnsupportedOperationException();
		self=this;
	}

	public Environment(String conf) {
		log.info("Database environment initializing...");
		this.conf=conf;
		prop = ResourceBundle.getBundle(conf);
		self=this;
	}
	
	public static Environment getEnvironment(){
		return self;
	}
	
	public String getAsia() {
		return prop.getString(Constant.ASIA).trim();
	}

	public String getConf(){
		return conf;
	}

	public String getDbType() {
		return prop.getString(Constant.DB_TYPE).trim();
	}

	public Boolean getShowSql() {
		return !(prop.getString(Constant.SHOW_SQL)==null||prop.getString(Constant.SHOW_SQL).trim().equals("false"));
	}

	public String getDriverClass() {
		return prop.getString(Constant.DRIVER_CLASS).trim();
	}

	public String getJndi(){
		return prop.getString(Constant.JNDI).trim();
	}

	public Integer getMaxSize() {
		return valueOf(prop.getString(Constant.MAX_SIZE).trim(),50);
	}

	public Integer getMinSize() {
		return valueOf(prop.getString(Constant.MIN_SIZE).trim(),10);
	}

	public String getPassword() {
		return prop.getString(Constant.PASSWORD).trim();
	}

	public String getPool() {
		return prop.getString(Constant.POOL).trim();
	}

	public ResourceBundle getProp() {
		return prop;
	}

	public String getSysTable() {
		return prop.getString(Constant.SYS_TABLE).trim();
	}

	public String getUrl() {
		return prop.getString(Constant.URL).trim();
	}
	
	public String getUsername() {
		return prop.getString(Constant.USERNAME).trim();
	}
	

	public String getPkgName() {
		return StringUtil.deleteWhitespace(prop.getString(Constant.PKG_NAME).trim());
	}
	
	public Long getOvertime() {
		Long overTime;
		try{
			overTime=Long.parseLong(prop.getString(Constant.OVERTIME).trim());
		}catch(NumberFormatException e){
			overTime=3600L;
		}
		return overTime*1000; 
	}

	
	
	private Integer valueOf(String string,Integer defaultValue){
		try{
			return Integer.parseInt(string);
		}catch(NumberFormatException e){
			return defaultValue;
		}
	}
	
	//fot Automatic Generate Toolkit

	public void setDbType(String dbType) {

	}

	public void setUsername(String username) {

	}

	public void setPassword(String password) {

	}

	public void setUrl(String url) {

	}

	public void setDriverClass(String driverClass) {

	}

	public void setPool(String pool) {

	}

	public void setMaxSize(Integer maxSize) {

	}

	public void setMinSize(Integer minSize) {

	}

	public void setOvertime(Long overtime) {

	}

	public void setShowSql(Boolean showSql) {

	}


}
