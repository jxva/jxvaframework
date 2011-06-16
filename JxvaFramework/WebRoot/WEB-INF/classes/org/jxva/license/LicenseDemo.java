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
package org.jxva.license;

import java.util.Date;

import com.jxva.entity.Path;
import com.jxva.license.Configure;
import com.jxva.license.License;
import com.jxva.license.LicenseManager;
import com.jxva.license.PrivateKey;
import com.jxva.util.DateUtil;
import com.jxva.util.SecurityUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-01 11:30:10 by Jxva
 */
public class LicenseDemo {

	public static Date getExpired(){
		return DateUtil.parse(DateUtil.DATE_YMD,"2008-12-01");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LicenseManager manager=LicenseManager.getInstance(Path.getPath(LicenseDemo.class)+"license.lic");
		Configure configure=manager.getConfigure();
		License license=manager.getLicense();
		for(int i=0;i<10;i++){
			String sign=SecurityUtil.getSignature(PrivateKey.getKey(),configure.getComposeText());
			System.out.println(sign);
		}
		System.out.println(license.isValid());
	}

}
