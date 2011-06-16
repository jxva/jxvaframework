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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:13:24 by Jxva
 */
public final class CacheFactory {

    private static final int STATUS_UNINITIALISED = 1;

    private static final int STATUS_ALIVE = 2;

    private static final int STATUS_SHUTDOWN = 3;

    private volatile static CacheFactory instance;
    
    private Configuration configuration;

    private Hashtable<String, CacheEntity> caches = new Hashtable<String, CacheEntity>();

    private CacheEntity defaultCacheEntity;
    
    private Cache cache;
    
    private String diskStorePath;

    private int status;


    public Cache createCache(String name) {
    	try{
    		if(!cacheExists(name)||cache==null){
		        CacheEntity ce=configuration.getDefaultCache();
		        ce=new CacheEntity(name,ce.getMaxElementsInMemory(),ce.isOverflowToDisk(),ce.isEternal(),ce.getTimeToLiveSeconds(),ce.getTimeToIdleSeconds());
		        addCacheNoCheck(ce);
		        System.out.println("Create cache name '"+name+"' successful...");
		        cache=new CacheImpl(ce);
    		}
    		return cache;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
	}

    private CacheFactory() throws CacheException {
        status = STATUS_UNINITIALISED;
        configure();
        status = STATUS_ALIVE;
    }

    /**
     * Loads configuration.Should only be called once.
     */
    private synchronized void configure() throws CacheException {
        if (defaultCacheEntity != null || diskStorePath != null || caches.size() != 0 || status == STATUS_SHUTDOWN) {
            throw new IllegalStateException("Attempt to reinitialise the Cache Factory");
        }
        configuration = new Configuration();
        try {
            Configurator configurator = new Configurator();
            configurator.configure(configuration,DefaultInputStream.getCacheInputStream());
            defaultCacheEntity =configuration.getDefaultCache();
        	System.out.println("Cache Factory initializing...");
        } catch (Exception e) {
            throw new CacheException("Cannot configure CacheManager: " + e.getMessage());
        }
        diskStorePath = configuration.getDiskCachePath();
        Set<String> configuredCacheKeys = configuration.getCacheKeySet();
        for (Iterator<String> iterator = configuredCacheKeys.iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            addCacheNoCheck(configuration.getCache(name));
        }
    }

    public static CacheFactory create() throws CacheException {
        if (instance == null) {//DCL
        	 synchronized (CacheFactory.class) {
        		 if(instance==null){
        			 instance = new CacheFactory();
        		 }
            }
         }
         return instance;
    }

    public static CacheFactory getInstance(){
    	try{
    		return CacheFactory.create();
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }


    public synchronized CacheEntity getCacheEntity(String name) throws IllegalStateException {
        checkStatus();
        return caches.get(name);
    }

   
    public synchronized void addCache(String cacheName) throws IllegalStateException,
            ObjectExistsException, CacheException {
        checkStatus();
        if (caches.get(cacheName) != null) {
            throw new ObjectExistsException("Cache " + cacheName + " already exists");
        }
        CacheEntity cache = null;
        try {
            cache = (CacheEntity) defaultCacheEntity.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Failure adding cache"+e.toString());
        }
        cache.setName(cacheName);
        addCache(cache);
    }

    
    public synchronized void addCache(CacheEntity cache){
    	try{
    		checkStatus();
    		addCacheNoCheck(cache);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    private synchronized void addCacheNoCheck(CacheEntity cache) throws IllegalStateException,
            ObjectExistsException, CacheException {
        if (caches.get(cache.getName()) != null) {
            throw new ObjectExistsException("Cache  '" + cache.getName() + "' already exists!");
        }
        cache.initialise(configuration);
        caches.put(cache.getName(), cache);
    }

    public synchronized boolean cacheExists(String cacheName) throws IllegalStateException {
        checkStatus();
        return (caches.get(cacheName) != null);
    }

    public synchronized void removeCache(String cacheName) throws IllegalStateException {
        checkStatus();
        CacheEntity cache = caches.remove(cacheName);
        if (cache != null) {
            cache.dispose();
        }
    }


    public void shutdown() {
        if (status == STATUS_SHUTDOWN) {
            // jlog.Warning("CacheManager already shutdown");
            return;
        }
        synchronized (this) {
            Enumeration<CacheEntity> allCaches = caches.elements();
            while (allCaches.hasMoreElements()) {
                CacheEntity cache = allCaches.nextElement();
                if (cache != null) {
                    cache.dispose();
                }
            }
            status = STATUS_SHUTDOWN;
        }
        synchronized (CacheFactory.class) {
            instance = null;
        }
    }


    public synchronized String[] getCacheNames() {
        checkStatus();
        String[] list = new String[caches.size()];
        return caches.keySet().toArray(list);
    }

    /**
     * Returns configuration to classes in this package.
     * Used for testing.
     */
    Configuration getConfiguration() throws IllegalStateException {
        checkStatus();
        return configuration;
    }

    private void checkStatus() {
        if (status != STATUS_ALIVE) {
            throw new IllegalStateException("The Cache Factory is not alive.");
        }
    }

    /**
     * Gets the status of the CacheManager.
     */
    public int getStatus() {
        return status;
    }
}