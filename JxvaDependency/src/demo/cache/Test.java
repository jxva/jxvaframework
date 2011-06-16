package demo.cache;

import java.io.Serializable;
import java.util.List;

import study.cache.CacheEntity;
import study.cache.CacheFactory;
import study.cache.Element;

/**
 * 
 * @author Jxva
 *
 */
public class Test
{
	private static CacheEntity c=null;
	
	public static void main(String[] args)throws Exception{
		c=new CacheEntity("test", 1000, false, true, 0, 0);
		CacheFactory cm=CacheFactory.getInstance();
		cm.addCache(c);

		
		c.put(new Element((Serializable)"name", (Serializable)"蒋赞"));
		c.put(new Element((Serializable)"gander", (Serializable)"男"));
		c.put(new Element((Serializable)"age", (Serializable)"24"));
		
		//获得所有的缓存名称
		//String[] cmn=cm.getCacheNames();
		//通过对象名获得缓存的对象值
		Element el=c.get((Serializable)"name");
		//输出缓存元素的值
		System.out.println(el.getValue());
		//删除所有缓存
		//cm.removeCache("test");

		if(c.isExpired(el))
		{
			System.out.println("已经过期了");
		}
		
		List<Object> list=c.getKeys();
		for(int i=0;i<list.size();i++)
		{
			Element e=c.get((Serializable)list.get(i));
			//System.out.println(e.getValue());
			System.out.println(e.getKey()+":"+e.getValue()+":"+e.getHitCount()+":"+e.getSerializedSize());
		}			
	}
}
