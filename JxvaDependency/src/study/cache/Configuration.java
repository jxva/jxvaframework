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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:14:02 by Jxva
 */
public class Configuration {

    private DiskStore diskStore;
    private DefaultCache defaultCache;
    private Map<String, Cache> caches = new HashMap<String, Cache>();

    public void addDiskStore(DiskStore diskStore) throws ObjectExistsException {
        if (this.diskStore != null) {
            throw new ObjectExistsException("The Disk Store has already been configured");
        }
        this.diskStore = diskStore;
    }

    public void addDefaultCache(DefaultCache defaultCache) throws ObjectExistsException {
        if (this.defaultCache != null) {
            throw new ObjectExistsException("The Default Cache has already been configured");
        }
        this.defaultCache = defaultCache;
    }

    public void addCache(Cache cache) throws ObjectExistsException {
        if (caches.get(cache.name) != null) {
            throw new ObjectExistsException("Cannot create cache: " + cache.name
                    + " with the same name as an existing one.");
        }
        if (cache.name.equalsIgnoreCase(study.cache.CacheEntity.DEFAULT_CACHE_NAME)) {
            throw new ObjectExistsException("The Default Cache has already been configured");
        }

        caches.put(cache.name, cache);
    }

    public String getDiskCachePath() {
        if (diskStore != null) {
            return diskStore.getPath();
        } else {
            return null;
        }
    }

    public study.cache.CacheEntity getDefaultCache()  throws CacheException {
        if (defaultCache == null) {
            throw new CacheException("Illegal configuration. No default cache is configured.");
        } else {
            return defaultCache.toCache();
        }
    }

    public Set<String> getCacheKeySet() {
        return caches.keySet();
    }

    public study.cache.CacheEntity getCache(String name) {
        Cache cache = caches.get(name);
        return cache.toCache();
    }


    public static class DiskStore {
        private String path;

        public void setPath(String path) {
            String translatedPath = null;
            if (path.equals("user.home")) {
                translatedPath = System.getProperty("user.home");
            } else if (path.equals("user.dir")) {
                translatedPath = System.getProperty("user.dir");
            } else if (path.equals("java.io.tmpdir")) {
                translatedPath = System.getProperty("java.io.tmpdir");
            } else {
                translatedPath = path;
            }
            this.path = translatedPath;
        }

        private String getPath() {
            return path;
        }
    }


    public static class Cache {

        protected String name;

        protected int maxElementsInMemory;

        protected boolean eternal;

        protected int timeToIdleSeconds;

        protected int timeToLiveSeconds;

        protected boolean overflowToDisk;

        protected boolean diskPersistent;

        protected long diskExpiryThreadIntervalSeconds;

        public void setName(String name) {
            this.name = name;
        }

        public void setMaxElementsInMemory(int maxElementsInMemory) {
            this.maxElementsInMemory = maxElementsInMemory;
        }

        public void setEternal(boolean eternal) {
            this.eternal = eternal;
        }

        public void setTimeToIdleSeconds(int timeToIdleSeconds) {
            this.timeToIdleSeconds = timeToIdleSeconds;
        }

        public void setTimeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public void setOverflowToDisk(boolean overflowToDisk) {
            this.overflowToDisk = overflowToDisk;
        }

        public void setDiskPersistent(boolean diskPersistent) {
            this.diskPersistent = diskPersistent;
        }

        public void setDiskExpiryThreadIntervalSeconds(int diskExpiryThreadIntervalSeconds) {
            this.diskExpiryThreadIntervalSeconds = diskExpiryThreadIntervalSeconds;
        }

        private study.cache.CacheEntity toCache() {
            return new study.cache.CacheEntity(name,
                    maxElementsInMemory,
                    overflowToDisk,
                    eternal,
                    timeToLiveSeconds,
                    timeToIdleSeconds,
                    diskPersistent,
                    diskExpiryThreadIntervalSeconds);
        }
    }

    public static class DefaultCache extends Cache {
        private study.cache.CacheEntity toCache() {
            return new study.cache.CacheEntity(study.cache.CacheEntity.DEFAULT_CACHE_NAME,
                    maxElementsInMemory,
                    overflowToDisk,
                    eternal,
                    timeToLiveSeconds,
                    timeToIdleSeconds,
                    diskPersistent,
                    diskExpiryThreadIntervalSeconds);
        }
    }
}
