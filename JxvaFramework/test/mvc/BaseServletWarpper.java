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
package mvc;

import junit.framework.TestCase;
import mock.RequestMock;
import mock.ResponseMock;
import mock.SessionMock;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-31 10:50:14 by Jxva
 */
public class BaseServletWarpper extends TestCase {

	protected RequestMock request;
	protected ResponseMock response;
	protected SessionMock session;

	public void setUp() {
		request = new RequestMock();
		response = new ResponseMock();
		session =request.getSession(true);
	}
}
