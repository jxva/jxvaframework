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

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-28 16:07:48 by Jxva
 */
public class LicenseManager {
	
	private static  LicenseManager licenseManager;
	private Configure configure;
	
	public Configure getConfigure() {
		return configure;
	}

	private LicenseManager(final String licenseFile){
		configure=new Configure(licenseFile);
	}
	
	public synchronized static LicenseManager getInstance(){
		return getInstance("license.lic");
	}
	
	
	public synchronized static LicenseManager getInstance(final String licenseFile){
		if(licenseManager==null){
			licenseManager=new LicenseManager(licenseFile);
		}
		return licenseManager;
	}
	
	public License getLicense() throws LicenseException{
		Edition edition=configure.getEdition();
		if(edition.equals(Edition.DEVELOPMENT)){
			return new DevelopmentLicense(configure);
		}else if(edition.equals(Edition.EVALUATION)){
			return new EvaluationLicense(configure);
		}else  if(edition.equals(Edition.PERSONAL)){
			return new PersonalLicense(configure);
		}else if(edition.equals(Edition.ENTERPRISE)){
			return new EnterpriseLicense(configure);
		}else{
			throw new LicenseException();
		}
	}	
}
