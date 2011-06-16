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
package org.jxva;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jxva.MVELThreadTest.Bean;
import org.mvel2.MVEL;
import org.mvel2.optimizers.OptimizerFactory;

import com.jxva.entity.Encoding;
import com.jxva.http.HttpTransfer;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-23 10:42:42 by Jxva
 */
public class MultiTest {
	static final HttpTransfer http1=new HttpTransfer("http://127.0.0.1/");
	static final HttpTransfer http2=new HttpTransfer("http://127.0.0.1:8080/video/story!showStory.jv?storyId=463");
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		for(int i=0;i<1;i++){
			//Thread.sleep(3000);
			test();
		}
	}
	
	private static void test1(){
		//Create two of the same expressions
        final String expression = "firstname";
        final String expression2 = "lastname";

        //Create a bean to run expressions against
        final Bean bean = new Bean();

        //Use reflection mode
        OptimizerFactory.setDefaultOptimizer(OptimizerFactory.SAFE_REFLECTIVE);

        //Compile the expressions
        final Serializable mvelExpr1 = MVEL.compileExpression(expression);
        final Serializable mvelExpr2 = MVEL.compileExpression(expression2);
		
		ExecutorService pool = Executors.newFixedThreadPool(20);
		for (int i = 0; i <20; i++) {
			pool.execute(new Runnable() {
				public void run() {
					MVEL.executeExpression(mvelExpr1, bean);
		            MVEL.executeExpression(mvelExpr2, bean);
				}
			});
		}
		pool.shutdown();
	}
	
	private static void test(){
		ExecutorService pool = Executors.newFixedThreadPool(400);
		for (int i = 0; i <400; i++) {
			pool.execute(new Runnable() {
				public void run() {
					//System.out.println(Thread.currentThread().getName());
					http1.get(Encoding.UTF_8);
				}
			});
		}
		pool.shutdown();
		System.out.println("SS");
	}
	

    /**
     * Bean
     */
    public static class Bean {
        private String firstname;

        private String lastname;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
    }
}
