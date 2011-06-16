package demo.cache;

import study.cache.Cache;
import study.cache.CacheEntity;
import study.cache.CacheFactory;
import study.cache.CacheImpl;
import study.cache.Element;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CacheEntity ce=new CacheEntity("test", 1000, false, true, 0, 0);
		CacheFactory.getInstance().addCache(ce);
		Cache jc=new CacheImpl(ce);
		jc.put("name","fdsa");
		Element e=jc.getElement("name");
		String s=e.getValue().toString();
		System.out.println(s);
	}

}
