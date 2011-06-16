package demo.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import study.cache.Cache;
import study.cache.CacheEntity;
import study.cache.CacheFactory;
import study.cache.CacheImpl;


/**
 * 
 *   @author Jxva
 *
 */
public class TestObject
{
	private static Cache jc=null;
	
	public static void main(String[] args)throws Exception{
		CacheEntity ce=new CacheEntity("test", 1000, false, true, 0, 0);
		CacheFactory.getInstance().addCache(ce);
		jc=new CacheImpl(ce);

		Bean b=new Bean();
		
		b.setMsgid(58787685);
		b.setRealname("蒋赞");
		b.setFactoryno("65435345");
		
		//缓存对象
		jc.put("b",b);
		//缓存字符串
		jc.put("s","fdsfsafsaf");
		//缓存字符
		jc.put("c",'d');
		//缓存整形
		jc.put("i", 543);
		//缓存List
		List<String> list=new ArrayList<String>();
		list.add("fdfsad");
		list.add("fdfdfff");
		jc.put("l",list);
		
		//获取所有缓存数据
		List<Object> li=jc.getKeys();
		Iterator<Object> it=li.iterator();
		while(it.hasNext())
		{
			Object obj=jc.get(it.next());
			System.out.println(obj.toString());
		}
		
		for(int i=0;i<li.size();i++)
		{
			Object obj=jc.get(li.get(i));
			
			//if(obj.getClass().toString().contains("java"))
			//{
				System.out.println(obj.toString());
			//}			
		}
		
		System.out.println("==========================================");
		
		if(jc.containsKey("b"))
		{
			System.out.println(jc.get("b"));
			//Bean tmp=(Bean)jc.get("b");
			//System.out.println(tmp.getMsgid());
			//System.out.println(tmp.getRealname());
		}
		
		System.out.println(jc.get("s"));
		System.out.println(jc.get("c"));
		System.out.println(jc.get("i"));
		//System.out.println(jc.get("l")+"--"+((List)jc.get("l")).get(0));
	}
}
