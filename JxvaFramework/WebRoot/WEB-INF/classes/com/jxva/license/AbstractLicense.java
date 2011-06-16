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
package com.jxva.license;

import java.util.Date;

import com.jxva.util.SecurityUtil;

/**
 * License Abstract Class & All License Edition's Template(Pattern) Class
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-01 11:17:31 by Jxva
 */
public abstract class AbstractLicense implements License{
	
	private Configure configure;
	
	public AbstractLicense(Configure configure){
		this.configure=configure;
	}
	
	/* (non-Javadoc)
	 * @see com.jxva.license.License#getExpired()
	 */
	public Date getExpired() {
		return configure.getExpired();
	}

	/* (non-Javadoc)
	 * @see com.jxva.license.License#getLicenseId()
	 */
	public int getLicenseId() {
		return configure.getLicenseId();
	}

	/* (non-Javadoc)
	 * @see com.jxva.license.License#getSerialNo()
	 */
	public String getSerialNo() {
		return configure.getSerialNo();
	}

	/* (non-Javadoc)
	 * @see com.jxva.license.License#getSignature()
	 */
	public String getSignature() {
		return configure.getSignature();
	}

	/* (non-Javadoc)
	 * @see com.jxva.license.License#getEdition()
	 */
	public String getEdition() {
		return configure.getEdition().getName();
	}

	/* (non-Javadoc)
	 * @see com.jxva.license.License#isExpired()
	 */
	public boolean isExpired(){
		return getExpired().getTime()<System.currentTimeMillis();
	}
	
	/* (non-Javadoc)
	 * @see com.jxva.license.License#getServerAddr()
	 */
	public String getServerAddr() {
		return configure.getServerAddr();
	}
	
	/* (non-Javadoc)
	 * @see com.jxva.license.License#isValid()
	 */
	public boolean isValid() {
		return isValidSignature()&&!isExpired();
	}
	
	private boolean isValidSignature(){
		return SecurityUtil.checkSignature(PublicKey.getKey(),configure.getComposeText(),getSignature());
	}
}
