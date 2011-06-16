package demo.cache.reference;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import demo.cache.ConcurrentLinkedList;


public class UtilCache {
	public static String module = UtilCache.class.getName();

	/** A list of the elements order by Least Recent Use */
	public final ConcurrentLinkedList keyLRUList = new ConcurrentLinkedList();

	/**
	 * A hashtable containing a CacheLine object with a value and a loadTime for
	 * each element. for above jdk1.5
	 */
	public final Map<Object, CacheLine> cacheLineTable = new ConcurrentHashMap<Object, CacheLine>();

	/** A count of the number of cache hits */
	protected volatile long hitCount = 0;

	/** A count of the number of cache misses */
	protected volatile long missCount = 0;

	/**
	 * The maximum number of elements in the cache. If set to 0, there will be
	 * no limit on the number of elements in the cache.
	 */
	protected volatile long maxSize = 0;

	/**
	 * Specifies the amount of time since initial loading before an element will
	 * be reported as expired. If set to 0, elements will never expire.
	 */
	protected volatile long expireTime = 0;

	/**
	 * Specifies whether or not to use soft references for this cache, defaults
	 * to false
	 */
	protected volatile boolean useSoftReference = false;


	public UtilCache(int maxSize, long expireTime, boolean useSoftReference) {
		this.maxSize = maxSize;
		this.expireTime = expireTime;
		this.useSoftReference = useSoftReference;
	}



	/**
	 * Puts or loads the passed element into the cache
	 * 
	 * @param key
	 *            The key for the element, used to reference it in the hastables
	 *            and LRU linked list
	 * @param value
	 *            The value of the element
	 */
	public void put(Object key, Object value) {
		if ((key == null) || (value == null))
			return;

		try {
			if (maxSize > 0) {
				// when maxSize is changed, the setter will take care of filling
				// the
				// LRU list
				if (cacheLineTable.containsKey(key)) {
					keyLRUList.moveFirst(key);
				} else {
					keyLRUList.addFirst(key);
				}
			}

			if (expireTime > 0) {
				cacheLineTable.put(key, new CacheLine(value, useSoftReference, System.currentTimeMillis()));
			} else {
				cacheLineTable.put(key, new CacheLine(value, useSoftReference));
			}
			if (maxSize > 0 && cacheLineTable.size() > maxSize) {
				Object lastKey = keyLRUList.getLast();
				removeObject(lastKey);
			}
		} catch (Exception e) {

		} finally {
		}
		
	}

	/**
	 * Gets an element from the cache according to the specified key. If the
	 * requested element hasExpired, it is removed before it is looked up which
	 * causes the function to return null.
	 * 
	 * @param key
	 *            The key for the element, used to reference it in the hastables
	 *            and LRU linked list
	 * @return The value of the element specified by the key
	 */
	public Object get(final Object key) {
		if (key == null)
			return null;
		if (!cacheLineTable.containsKey(key))
			return null;
		CacheLine line = cacheLineTable.get(key);

		if (hasExpired(line)) {
			removeObject(key);
			line = null;
		}

		if (line == null) {
			missCount++;
			return null;
		}

		hitCount++;
		// double hitPercent = 100*(double)hitCount/(hitCount + missCount);
		// Debug.logVerbose("[JdonFramework]cache hit percent: " +
		// percentFormat.format(hitPercent)+"%", module);

		if (maxSize > 0) {
			keyLRUList.moveFirst(key);
		}

		return line.getValue();
	}

	/**
	 * Removes an element from the cache according to the specified key
	 * 
	 * @param key
	 *            The key for the element, used to reference it in the hastables
	 *            and LRU linked list
	 * @return The value of the removed element specified by the key
	 */
	public void remove(Object key) {
		removeObject(key);
	}

	private void removeObject(Object key) {
		if (key == null) {
			missCount++;
		}
		CacheLine line = cacheLineTable.remove(key);
		if (line != null) {
			if (maxSize > 0)
				keyLRUList.remove(key);
		} else {
			missCount++;
		}
	}

	public Set<Object> keySet() {
		return cacheLineTable.keySet();
	}

	public Collection<CacheLine> values() {
		return cacheLineTable.values();
	}

	/** Removes all elements from this cache */
	public void clear() {
		cacheLineTable.clear();
		keyLRUList.clear();
		clearCounters();
	}

	/** Removes all elements from this cache */
	public void clearAllCaches() {
		clear();
	}

	/**
	 * Returns the number of successful hits on the cache
	 * 
	 * @return The number of successful cache hits
	 */
	public long getHitCount() {
		return hitCount;
	}

	/**
	 * Returns the number of cache misses
	 * 
	 * @return The number of cache misses
	 */
	public long getMissCount() {
		return missCount;
	}

	/**
	 * Clears the hit and miss counters
	 */
	public void clearCounters() {
		hitCount = 0;
		missCount = 0;
	}

	/**
	 * Sets the maximum number of elements in the cache. If 0, there is no
	 * maximum.
	 * 
	 * @param maxSize
	 *            The maximum number of elements in the cache
	 */
	public void setMaxSize(long maxSize) {
		// if the new maxSize is <= 0, clear keyLRUList
		if (maxSize <= 0) {
			keyLRUList.clear();
		} else if (maxSize > 0 && this.maxSize <= 0) {
			// if the new maxSize > 0 and the old is <= 0, fill in LRU list -
			// order will be meaningless for now
			Iterator<Object> keys = cacheLineTable.keySet().iterator();

			while (keys.hasNext()) {
				keyLRUList.add(keys.next());
			}
		}

		// if the new maxSize is less than the current cache size, shrink the
		// cache.
		if (maxSize > 0 && cacheLineTable.size() > maxSize) {
			while (cacheLineTable.size() > maxSize) {
				Object lastKey = keyLRUList.getLast();

				removeObject(lastKey);
			}
		}

		this.maxSize = maxSize;
	}

	/**
	 * Returns the current maximum number of elements in the cache
	 * 
	 * @return The maximum number of elements in the cache
	 */
	public long getMaxSize() {
		return maxSize;
	}

	/**
	 * Sets the expire time for the cache elements. If 0, elements never expire.
	 * 
	 * @param expireTime
	 *            The expire time for the cache elements
	 */
	public void setExpireTime(long expireTime) {
		// if expire time was <= 0 and is now greater, fill expire table now
		if (this.expireTime <= 0 && expireTime > 0) {
			long currentTime = System.currentTimeMillis();
			Iterator<CacheLine> values = cacheLineTable.values().iterator();

			while (values.hasNext()) {
				CacheLine line = values.next();

				line.setLoadTime(currentTime);
			}
		} else if (this.expireTime <= 0 && expireTime > 0) {// if expire time
			// was > 0 and is
			// now <=, do
			// nothing, just
			// leave the load
			// times in place,
			// won't hurt
			// anything...
		}

		this.expireTime = expireTime;
	}

	/**
	 * return the current expire time for the cache elements
	 * 
	 * @return The expire time for the cache elements
	 */
	public long getExpireTime() {
		return expireTime;
	}

	/**
	 * Return whether or not the cache lines should use a soft reference to the
	 * data
	 */
	public boolean getUseSoftReference() {
		return this.useSoftReference;
	}

	/**
	 * Returns the number of elements currently in the cache
	 * 
	 * @return The number of elements currently in the cache
	 */
	public long size() {
		return cacheLineTable.size();
	}

	/**
	 * Returns a boolean specifying whether or not an element with the specified
	 * key is in the cache. If the requested element hasExpired, it is removed
	 * before it is looked up which causes the function to return false.
	 * 
	 * @param key
	 *            The key for the element, used to reference it in the hastables
	 *            and LRU linked list
	 * @return True is the cache contains an element corresponding to the
	 *         specified key, otherwise false
	 */
	public boolean containsKey(Object key) {
		CacheLine line = cacheLineTable.get(key);

		if (hasExpired(line)) {
			removeObject(key);
			line = null;
		}
		if (line != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a boolean specifying whether or not the element corresponding to
	 * the key has expired. Only returns true if element is in cache and has
	 * expired. Error conditions return false, if no expireTable entry, returns
	 * true. Always returns false if expireTime <= 0. Also, if SoftReference in
	 * the CacheLine object has been cleared by the gc return true.
	 * 
	 * @param key
	 *            The key for the element, used to reference it in the hastables
	 *            and LRU linked list
	 * @return True is the element corresponding to the specified key has
	 *         expired, otherwise false
	 */
	public boolean hasExpired(Object key) {
		if (key == null)
			return false;

		CacheLine line = cacheLineTable.get(key);

		return hasExpired(line);
	}

	protected boolean hasExpired(CacheLine line) {
		if (line == null)
			return false;
		// check this BEFORE checking to see if expireTime <= 0, ie if time
		// expiration is enabled
		// check to see if we are using softReference first, slight performance
		// increase
		if (this.useSoftReference && line.getValue() == null) {
			System.out.println("SoftReference getValue()  == null GC has done it. ");
			return true;
		}

		if (expireTime <= 0)
			return false;

		if (line.getLoadTime() <= 0)
			return true;
		if ((line.getLoadTime() + expireTime) < System.currentTimeMillis()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Clears all expired cache entries; also clear any cache entries where the
	 * SoftReference in the CacheLine object has been cleared by the gc
	 */
	public void clearExpired() {
		Iterator<Object> keys = cacheLineTable.keySet().iterator();

		while (keys.hasNext()) {
			Object key = keys.next();

			if (hasExpired(key)) {
				removeObject(key);
			}
		}
	}

}
