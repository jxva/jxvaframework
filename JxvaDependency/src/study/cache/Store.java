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

import java.io.IOException;
import java.io.Serializable;

/**
 * This is the interface for all stores. A store is a physical counterpart to a cache, which
 * is a logical concept.
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:16:34 by Jxva
 */
public interface Store {
	
    public static final int CACHE_HUB = 1;

    public static final int DISK_CACHE = 2;

    public static final int STATUS_UNINITIALISED = 1;

    public static final int STATUS_ALIVE = 2;

    public static final int STATUS_DISPOSED = 3;

    public static final int STATUS_ERROR = 4;

    public void put(Element element) throws IOException;

    public Element get(Serializable key) throws IOException;

    public boolean remove(Serializable key) throws IOException;

    public void removeAll() throws IOException;

    public void dispose() throws IOException;

    public int getSize();

    public int getStatus();

    public String getName();

}
