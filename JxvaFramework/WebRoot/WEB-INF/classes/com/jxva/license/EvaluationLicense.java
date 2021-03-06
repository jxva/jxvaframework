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
 * License Evaluation Edition
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-01 14:36:21 by Jxva
 */
public class EvaluationLicense extends AbstractLicense {

	/**
	 * @param configure
	 */
	public EvaluationLicense(Configure configure) {
		super(configure);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.jxva.license.License#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
