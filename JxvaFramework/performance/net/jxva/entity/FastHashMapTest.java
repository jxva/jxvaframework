/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
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
 */
package net.jxva.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-22 17:25:43 by Jxva
 */
public class FastHashMapTest {

	/**
	 * @param args
	 */
	 public static void main(String[] args){
         int N = 10000;
         long start = System.nanoTime();
        Map<Long,String> hm = new HashMap<Long,String>(N);
        for(int i = 0; i < N ; i++){
              hm.put(new Long(i),"HashMap " + i);
        }
        long end = System.nanoTime() - start;
    System.out.println("HashMap put " + N + " Object using " + end + " ns");
        start = System.nanoTime();
         ConcurrentHashMap<Long,String> fhm = new ConcurrentHashMap<Long,String>(N);
         //fhm.setFast(false);
        for(int i = 0; i < N ; i++){
            fhm.put(new Long(i),"FastHashMap " + i);
         }
        end = System.nanoTime() - start;
        System.out.println("FastHashMap put " + N + " Object using " + end + " ns");
        
        
         start = System.nanoTime();
         for(int i = 0; i < N ; i++){
            hm.get(new Long(i));
         }
         end = System.nanoTime() - start;
    System.out.println("HashMap get " + N + " Object using " + end + " ns");
        start = System.nanoTime();
         for(int i = 0; i < N ; i++){
            fhm.get(new Long(i));
        }
         end = System.nanoTime() - start;
        System.out.println("FastHashMap get " + N + " Object using " + end + " ns");
        
     }
}
