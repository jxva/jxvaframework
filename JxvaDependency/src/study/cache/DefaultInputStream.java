/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
package study.cache;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:14:20 by Jxva
 */
public final class DefaultInputStream {

	private DefaultInputStream(){
		 throw new UnsupportedOperationException();
	}
	
	public static final InputStream getCacheInputStream(){
		StringBuilder sb=new StringBuilder();
		sb.append("<ehcache>");
		sb.append("		<diskStore path=\"java.io.tmpdir\"/>");
		sb.append("		<defaultCache");
		sb.append("			maxElementsInMemory=\"1000\"");
		sb.append("			eternal=\"true\"");
		sb.append("			overflowToDisk=\"false\"");
		sb.append("			timeToIdleSeconds=\"0\"");
        sb.append("			timeToLiveSeconds=\"0\"");
        sb.append("			diskPersistent=\"true\"");
        sb.append("			diskExpiryThreadIntervalSeconds=\"3600\"");
        sb.append("		/>");
        sb.append("</ehcache>");
		return new ByteArrayInputStream(sb.toString().getBytes());
	}
}
