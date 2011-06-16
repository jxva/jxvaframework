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
 */
package org.jxva.sso;

import java.sql.Timestamp;

import org.jxva.sso.model.SsoUser;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-08 14:59:55 by Jxva
 */
public class SSOData {
	private String ssoId;
	private SsoUser ssoUser;
	private String ssoInfo;
	private Timestamp loginTime;
	private Timestamp updateTime;
	private Integer visitCount;
	
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	public SsoUser getSsoUser() {
		return ssoUser;
	}
	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}
	public String getSsoInfo() {
		return ssoInfo;
	}
	public void setSsoInfo(String ssoInfo) {
		this.ssoInfo = ssoInfo;
	}
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
}
