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

import java.io.File;
import java.util.Date;

import com.jxva.entity.Encoding;
import com.jxva.util.DateUtil;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-01 11:36:56 by Jxva
 */
public class Configure {
	
	private Element root;
	
	public Configure(final String licenseFile){
		root=XmlUtil.getDocument(new File(licenseFile),Encoding.UTF_8).getRootElement();
    }
		
	public Edition getEdition() throws LicenseException{
		return Edition.parse(root.getElement(LicenseConst.EDITION).getText());
	}
	
	public int getLicenseId(){
		return Integer.valueOf(root.getElement(LicenseConst.LICENSEID).getText());
	}
	
	public Date getExpired(){
		return DateUtil.parse(DateUtil.DATE_YMD,root.getElement(LicenseConst.EXPIRED).getText());
	}
	
	public String getSerialNo(){
		return root.getElement(LicenseConst.SERIALNO).getText();
	}

	public String getSignature(){
		return root.getElement(LicenseConst.SIGNATURE).getText();
	}
	
	public String getServerAddr(){
		return root.getElement(LicenseConst.SERVERADDR).getText();
	}
	
	/**
	 * 获取license.lic中组合的字符串
	 * @return
	 */
	public String getComposeText(){
		StringBuilder sb = new StringBuilder();
		sb.append(LicenseConst.EDITION).append('=').append(getEdition()).append('&');
		sb.append(LicenseConst.LICENSEID).append('=').append(getLicenseId()).append('&');
		sb.append(LicenseConst.SERIALNO).append('=').append(getSerialNo()).append('&');
		sb.append(LicenseConst.EXPIRED).append('=').append(root.getElement(LicenseConst.EXPIRED).getText()).append('&');
		sb.append(LicenseConst.SERVERADDR).append('=').append(getServerAddr());
		return sb.toString();
	}
}
