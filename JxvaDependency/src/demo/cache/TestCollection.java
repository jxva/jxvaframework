package demo.cache;

import java.util.ArrayList;
import java.util.List;

import study.cache.Cache;
import study.cache.CacheEntity;
import study.cache.CacheFactory;
import study.cache.CacheImpl;

public class TestCollection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
		CacheEntity ce=new CacheEntity("test", 1000, false, true, 0, 0);
		CacheFactory.getInstance().addCache(ce);
		Cache jc=new CacheImpl(ce);
		List<String> list=new ArrayList<String>();
		list.add("ffff");
		list.add("fdfasfas");
		jc.put("l",list);
		
		if(jc.containsKey("l"))
		{
			System.out.println(jc.get("l"));
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
