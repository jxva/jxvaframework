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
package study.jcache;

import java.util.List;

public class Test implements Runnable{
	private static CacheImpl c=null;
	
	public static void main(String[] args)throws Exception{
		//c=CacheManager.createCacheInDisk("110","c:/temp.data",Cache.ARITHMETIC_LEAST_USED,3);
		c=new CacheImpl(3);
		for(int i=0;i<1;i++){
			Test t=new Test();
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
				c.put("age","26");
				Thread.sleep(500);
				c.put("dd",33);
				c.get("age");
				Thread.sleep(500);
				c.get("age");
				Thread.sleep(500);
				c.get("age");
				Thread.sleep(500);
				c.get("sex");
				Thread.sleep(500);
				c.get("name");
				c.get("dd");
				Thread.sleep(500);
				List<Object> es=c.getElements();
				for(int i=0;i<es.size();i++){
					Element e=(Element)es.get(i);
					System.out.println(e.getKey()+":"+e.getValue()+":"+e.getHits()+":"+e.getIdle());
				}
				Thread.sleep(500);
				c.put("height","160cm");
				Thread.sleep(500);
				System.out.println("");
				es=c.getElements();
				
				for(int i=0;i<es.size();i++){
					Element e=(Element)es.get(i);
					System.out.println(e.getKey()+":"+e.getValue()+":"+e.getHits()+":"+e.getIdle());
				}	
				
				System.out.println(c.containsKey("age"));
				System.out.println(c.containsKey("height"));
				System.out.println(c.containsKey("name"));
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
