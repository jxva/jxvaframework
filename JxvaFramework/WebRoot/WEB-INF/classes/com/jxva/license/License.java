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

/**
 * License Interface
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-11-28 15:56:39 by Jxva
 */
public interface License {

	/**
	 * License Expired Date
	 * @return License Expried Date,Format:yyyy-MM-dd
	 */
	public Date getExpired();

	/**
	 * License Id
	 * @return LicenseId
	 */
	public int getLicenseId();
	
	/**
	 * License Serial Number
	 * @return License 
	 */
	public String getSerialNo();

	/**
	 * 获取License签名
	 * @return License签名
	 */
	public String getSignature();

	/**
	 * License Edition Name
	 * @return License版本名称
	 */
	public String getEdition();
	
	/**
	 * 获取License是否已经过期
	 * @return Expired:true UnExpired: false
	 */
	public boolean isExpired();
	
	/**
	 * 获取License是否合法
	 * @return Valid:true UnValid:false
	 */
	public boolean isValid();
	
	public String getServerAddr();
}
