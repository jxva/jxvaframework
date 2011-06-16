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

import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An implementation of a MemoryStore.
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:15:20 by Jxva
 */
public class MemoryStore implements Store {

    private Map<Serializable, Element> map;
    private CacheEntity cache;
    private DiskStore diskStore;
    private int status;
    
    protected MemoryStore() {
        return;
    }

    public MemoryStore(CacheEntity cache, DiskStore diskStore) {
        status = Store.STATUS_UNINITIALISED;
        this.cache = cache;
        this.diskStore = diskStore;

        try {
            map = loadMapInstance();
        } catch (CacheException e) {
        	System.out.println(cache.getName() + "Cache: Cannot start MemoryStore"+e.toString());
            return;
        }
        status = Store.STATUS_ALIVE;
    }

    public Map<Serializable, Element> loadMapInstance() throws CacheException {
        //First try to load java.util.LinkedHashMap, which is preferred.
        try {
            Class.forName("java.util.LinkedHashMap");
            Map<Serializable, Element> candidateMap = new SpoolingLinkedHashMap<Serializable, Element>();
            return candidateMap;
        } catch (Exception e) {
        }
        //Secondly, try and load com.jxva.cache.LRUMap
        try {
            Class.forName("com.jxva.cache.LRUMap");
            Map<Serializable, Element> candidateMap =new SpoolingLRUMap<Serializable,Element>();
            return candidateMap;
        } catch (Exception e) { //Give up
            throw new CacheException(cache.getName()+ "Cache: Cannot find candidate map.");
        }
    }

    public synchronized void put(Element element) {
        map.put(element.getKey(), element);
    }

    public synchronized void removeAll() {
        map.clear();
    }

    public synchronized Element get(Serializable key) {
        Element cacheElement = map.get(key);
        if (cacheElement != null) {
            cacheElement.updateAccessStatistics();
            //jlog.Debug(cache.getName() + "Cache: Quiet MemoryStore hit for " + key);
        } else{
    	  //jlog.Debug(cache.getName() + "Cache: Quiet MemoryStore miss for " + key);
        }
        return cacheElement;
    }

    /**
     * Gets an item from the cache, without updating Element statistics
     * The last access time in {@link com.jxva.cache.Element} is updated.
     * @param key the cache key
     * @return the element, or null if there was no match for the key
     */
    public synchronized Element getQuiet(Serializable key) {
        Element cacheElement = map.get(key);
        if (cacheElement != null) {
            //cacheElement.updateAccessStatistics();// Don't update statistics
            //jlog.Debug(cache.getName() + "Cache: Quiet MemoryStore hit for " + key);
        } else{
        	  //jlog.Debug(cache.getName() + "Cache: Quiet MemoryStore miss for " + key);
        }
        return cacheElement;
    }

    public synchronized boolean remove(Serializable key) {
        boolean removed = false;
        if (map.remove(key) != null) {
            removed = true;
        }
        return removed;
    }

    /**
     * Gets an Array of the keys for all elements in the memory cache
     * Does not check for expired entries
     */
    public synchronized Object[] getKeyArray() {
        return map.keySet().toArray();
    }

    /**
     * Returns the current cache size.
     */
    public int getSize() {
        return map.size();
    }


    /**
     * Gets the cache that the MemoryStore is used by
     */
    public CacheEntity getCache() {
        return cache;
    }

    /**
     * Gets the status of the MemoryStore.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the cache type.
     */
    public int getCacheType() {
        return Store.CACHE_HUB;
    }

    public String getName() {
        return cache.getName();
    }

    /**
     * Prepares for shutdown.
     */
    public synchronized void dispose() {
        if (status == STATUS_DISPOSED) {
            return;
        }
        status = STATUS_DISPOSED;
        if (cache.isDiskPersistent()) {
            //jlog.Debug(cache.getName() + " is persistent. Spooling " + map.size() + " elements to the disk store.");
            spoolAllToDisk();
        }
        map.clear();
        cache = null;
    }

    /**
     * Measures the size of the memory store by measuring the serialized
     * size of all elements.
     */
    public synchronized long getSizeInBytes() throws CacheException {
        long sizeInBytes = 0;
        for (Iterator<Element> iterator = map.values().iterator(); iterator.hasNext();) {
            Element element = iterator.next();
            if (element != null) {
                sizeInBytes += element.getSerializedSize();
            }
        }
        return sizeInBytes;
    }

    private boolean removeLeastRecentlyUsedElement(Element element) {
        if (cache.isExpired(element)) {
            return true;
        }

        if (map.size() <= cache.getMaxElementsInMemory()) {
            return false;
        } else {
            //jlog.Debug("Memory Store maximum size of " + cache.getMaxElementsInMemory()+ " reached. About to spool element with key \"" + element.getKey() + "\" to Disk Store");
        }
        if (cache.isOverflowToDisk()) {
            spoolToDisk(element);
        }
        //jlog.Debug("Memory Store size now: " + map.size());
        return true;
    }

    /**
     * Spools all elements to disk, in preparation for shutdown
     */
    private void spoolAllToDisk() {
        Collection<Element> values = map.values();
        for (Iterator<Element> iterator = values.iterator(); iterator.hasNext();) {
            Element element = iterator.next();
            spoolToDisk(element);
        }
    }


    /**
     * Puts the element in the DiskStore
     * Should only be called if {@link CacheEntity#isOverflowToDisk} is true
     * @param element The Element
     */
    private void spoolToDisk(Element element) {
        try {
            diskStore.put(element);
        } catch (IOException e) {
        	System.out.println(e.getMessage()+"");
            throw new IllegalStateException(e.getMessage());
        }
       // jlog.Debug(cache.getName() + "Cache: spool to disk done for: " + element.getKey());
    }


    /**
     * An LRU Map implementation based on Apache Commons LRUMap.
     * <p/>
     * This is used if {@link java.util.LinkedHashMap} is not found in the classpath.
     * LinkedHashMap is part of JDK
     */
    public class SpoolingLRUMap<K,V> extends LRUMap<K,V> implements Externalizable {

		private static final long serialVersionUID = 1L;

        public SpoolingLRUMap() {
            setMaximumSize(cache.getMaxElementsInMemory());
        }

        protected void processRemovedLRU(Object key, Object value) {
            Element element = (Element) value;
            removeLeastRecentlyUsedElement(element);
        }
    }

    /**
     * An extension of LinkedHashMap which overrides {@link #removeEldestEntry}
     * to persist cache entries to the auxiliary cache before they are removed.
     */
    public class SpoolingLinkedHashMap<K,V> extends LinkedHashMap<K,V> {

		private static final long serialVersionUID = 1L;
		private static final int INITIAL_CAPACITY = 100;
        private static final float GROWTH_FACTOR = .75F;

        public SpoolingLinkedHashMap() {
            super(INITIAL_CAPACITY, GROWTH_FACTOR, true);
        }

        /**
         * Returns <tt>true</tt> if this map should remove its eldest entry.
         * This method is invoked by <tt>put</tt> and <tt>putAll</tt> after
         * inserting a new entry into the map.  It provides the implementer
         * with the opportunity to remove the eldest entry each time a new one
         * is added.  This is useful if the map represents a cache: it allows
         * the map to reduce memory consumption by deleting stale entries.
         * <p/>
         * Will return true if:
         * <ol>
         * <li> the element has expired
         * <li> the cache size is greater than the in-memory actual.
         * In this case we spool to disk before returning.
         * </ol>
         *
         * @param eldest The least recently inserted entry in the map, or if
         *               this is an access-ordered map, the least recently accessed
         *               entry.  This is the entry that will be removed it this
         *               method returns <tt>true</tt>.  If the map was empty prior
         *               to the <tt>put</tt> or <tt>putAll</tt> invocation resulting
         *               in this invocation, this will be the entry that was just
         *               inserted; in other words, if the map contains a single
         *               entry, the eldest entry is also the newest.
         * @return <tt>true</tt> if the eldest entry should be removed
         *         from the map; <tt>false</t> if it should be retained.
         */
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
            Element element = (Element) eldest.getValue();
            return removeLeastRecentlyUsedElement(element);
        }
    }
}

