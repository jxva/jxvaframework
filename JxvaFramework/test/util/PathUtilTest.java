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
package util;

import junit.framework.TestCase;

import com.jxva.entity.Path;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-01 17:27:52 by Jxva
 */
public class PathUtilTest  extends TestCase {
	
	public void test(){
		String classPath=Path.class.getClassLoader().getResource(".").getPath().substring(1);
		assertEquals(Path.CLASS_PATH,classPath);
		
		assertTrue(classPath.startsWith(Path.APP_PATH));
		assertTrue(classPath.startsWith(Path.ROOT_PATH));
		assertTrue(classPath.startsWith(Path.WEB_INF_PATH));
	}
}
