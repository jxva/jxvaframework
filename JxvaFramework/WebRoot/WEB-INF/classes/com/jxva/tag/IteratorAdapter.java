
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
 *
 */
package com.jxva.tag;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * 直接从Struts中抽取的
 * Utility method for converting Enumeration to an Iterator class.  If you
 * attempt to remove() an Object from the iterator, it will throw an
 * UnsupportedOperationException. Added for use by TagLib so Enumeration can
 * be supported
 *
 * @version $Rev: 421119 $ $Date: 2005-05-07 12:11:38 -0400 (Sat, 07 May 2005)
 *          $
 */
public class IteratorAdapter implements Iterator<Object> {
    private Enumeration<?> e;

    public IteratorAdapter(Enumeration<?> e) {
        this.e = e;
    }

    public boolean hasNext() {
        return e.hasMoreElements();
    }

    public Object next() {
        if (!e.hasMoreElements()) {
            throw new NoSuchElementException(
                "IteratorAdaptor.next() has no more elements");
        }

        return e.nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException(
            "Method IteratorAdaptor.remove() not implemented");
    }
}
