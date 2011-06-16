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
package com.jxva.cache;

import java.util.HashMap;
import java.util.Iterator;



/**
 * Least Frequently Used/Most Frequently Used
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-23 09:53:55 by Jxva
 */
public class LFUCache extends AbstractCache  {

	private static final long serialVersionUID = 1L;

	public LFUCache(String name){
		this(name,DEFAULT_MAX_SIZE);
	}
	
	public LFUCache(String name,int maxSize){
		this.name=name;
		this.maxSize=maxSize;
		this.map=new HashMap<Object,Element>();
	}


    public final synchronized void doPut(Element elementJustAdded) {
        if (isFull()) {
            removeLfuElement(elementJustAdded);
        }
    }

    private void removeLfuElement(Element elementJustAdded) {
        // First element of the sorted list is the candidate for the removal
       Element element = findRelativelyUnused(elementJustAdded);
        remove(element.getKey());
    }

    /**
     * Find a "relatively" unused element, but not the element just added.
     */
    final Element findRelativelyUnused(Element elementJustAdded) {
        LFUPolicy.Metadata[] elements = sampleElements(map.size());
        LFUPolicy.Metadata metadata = LFUPolicy.leastHit(elements, new ElementMetadata(elementJustAdded));
        return map.get(metadata.getKey());
    }

    /**
     * Uses random numbers to sample the entire map.
     * @return an array of sampled elements
     */
     LFUPolicy.Metadata[] sampleElements(int size) {
        int[] offsets = LFUPolicy.generateRandomSample(size);
        ElementMetadata[] elements = new ElementMetadata[offsets.length];
        Iterator<Element> iterator = map.values().iterator();
        for (int i = 0; i < offsets.length; i++) {
            for (int j = 0; j < offsets[i]; j++) {
                iterator.next();
            }
            elements[i] = new ElementMetadata(iterator.next());
        }
        return elements;
    }


    /**
     * A Metadata wrapper for Element
     */
    private class ElementMetadata implements LFUPolicy.Metadata {
        private Element element;
        public ElementMetadata(Element element) {
            this.element = element;
        }
        public Object getKey() {
            return element.getKey();
        }
        public long getHitCount() {
            return element.getHitCount();
        }
        public int hashCode() {
            if (element != null) {
                return element.getKey().hashCode();
            } else {
                return 0;
            }
        }
        public boolean equals(Object object) {
            if (object != null && object instanceof LFUPolicy.Metadata) {
                LFUPolicy.Metadata metadata = (LFUPolicy.Metadata) object;
                return this.getKey().equals(metadata.getKey());
            } else {
                return false;
            }
        }
    }
}
