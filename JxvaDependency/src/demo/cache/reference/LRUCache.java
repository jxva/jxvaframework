
package demo.cache.reference;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LRUCache{

	private final UtilCache cache;

	public LRUCache(String configFileName) {
		cache = new UtilCache(1000,10000000,true);
	}
	
	public Object get(Object key) {
		return cache.get(key);
	}

	public void put(Object key, Object value) {
		cache.put(key, value);
	}

	public void remove(Object key) {
		cache.remove(key);
	}

	public long size() {
		return cache.size();
	}

	public void clear() {
		cache.clearAllCaches();
	}

	public boolean contain(Object key) {
		return cache.containsKey(key);
	}

	public Collection<Object> keySet() {
		return cache.keySet();
	}
	
//=========================for test===================================
	
	private static final long s=System.nanoTime();
	private static final AtomicInteger count=new AtomicInteger(0);
	private static final int MAX_THREAD=1000;
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<Integer> completionService =new ExecutorCompletionService<Integer>(exec);

		for (int index = 0; index < MAX_THREAD; index++) {
			final int NO=index;
			completionService.submit(new Callable<Integer>() {
				public Integer call() {
					try{
						LRUCache cache = new LRUCache("test");
						cache.put("test",NO);
						Thread.sleep(500L);
						if(NO!=(Integer)cache.get("test")){
							System.out.println(false);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					count.getAndIncrement();
					return count.intValue();
				}
			});
		};
		try {
			for(int i=0;i<MAX_THREAD;i++){
				if(MAX_THREAD==completionService.take().get()){
					System.out.println((System.nanoTime()-s)+" ns");
				}
			}
			System.out.println("---end---");
		} catch (Exception e) {
			e.printStackTrace();
		}
		exec.shutdown();
	}

}
