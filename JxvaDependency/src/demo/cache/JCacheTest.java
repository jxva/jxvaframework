package demo.cache;

import java.util.List;

import study.cache.Cache;
import study.cache.CacheEntity;
import study.cache.CacheFactory;
import study.cache.CacheImpl;
import study.cache.Element;


public class JCacheTest implements Runnable{

	private static Cache c=null;
	
	public static void main(String[] args) {
		CacheEntity ce=new CacheEntity("test", 1000, false, true, 0, 0);
		CacheFactory.getInstance().addCache(ce);
		c=new CacheImpl(ce);
		
		System.out.println(c.getName());
		


		for(int i=0;i<1;i++){
			JCacheTest t=new JCacheTest();
			Thread th=new Thread(t);
			th.start();
		}

	}

	public void run() {
		while(true){
			try{		
				c.put("name","蒋赞");
				Thread.sleep(500);
				c.put("sex","男");
				Thread.sleep(500);
				c.put("age","24");
				
				Thread.sleep(500);
				c.get("age");
				Thread.sleep(500);
				c.get("age");
				Thread.sleep(500);
				c.get("age");
				Thread.sleep(500);
				c.get("sex");
				Thread.sleep(500);
				c.get("name");
				Thread.sleep(500);
				
				List<Object> list=c.getKeys();
				for(int i=0;i<list.size();i++){
					Element e=c.getElement(list.get(i));
					System.out.println(e.getKey()+":"+e.getValue()+":"+e.getHitCount()+":"+e.getSerializedSize());
				}
				Thread.sleep(500);
				c.put("height","168cm");
				Thread.sleep(500);
				System.out.println("");
				
				list=c.getKeys();
				for(int i=0;i<list.size();i++){
					Element e=c.getElement(list.get(i));
					System.out.println(e.getKey()+":"+e.getValue()+":"+e.getHitCount()+":"+e.getSerializedSize());
				}
				
				System.out.println(c.containsKey("age"));
				System.out.println(c.containsKey("height"));
				System.out.println(c.containsKey("name"));
				System.out.println(c.containsKey("fdsafsa"));
				Thread.sleep(500);
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
			try{Thread.sleep(500);}catch(Exception e){}
			break;
		}
	}
}
