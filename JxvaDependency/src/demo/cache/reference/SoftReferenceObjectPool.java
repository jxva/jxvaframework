package demo.cache.reference;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SoftReferenceObjectPool {

	private Map<Object, List<SoftReference<Object>>> _pool = new HashMap<Object, List<SoftReference<Object>>>();

	public synchronized Object get(Object key) {
		List<SoftReference<Object>> pooled = _pool.get(key);
		if (pooled == null || pooled.isEmpty())
			return null;

		SoftReference<Object> reference = pooled.remove(0);
		// returns null if has been cleared by GC same as pool where empty
		return reference.get();
	}

	public synchronized void store(Object key, Object value) {
		List<SoftReference<Object>> pooled = _pool.get(key);
		if (pooled == null) {
			pooled = new LinkedList<SoftReference<Object>>();
			_pool.put(key, pooled);
		}
		SoftReference<Object> reference = new SoftReference<Object>(value);
		pooled.add(reference);
	}
}
