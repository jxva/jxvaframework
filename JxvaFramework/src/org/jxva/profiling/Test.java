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
package org.jxva.profiling;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-07-28 11:05:03 by Jxva
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		UtilTimerStack.setActive(true);
		UtilTimerStack.profile("purchaseItem: ",
				new UtilTimerStack.ProfilingBlock<String>() {
					public String doProfiling() {
						int j = 0;
						for (int i = 0; i < 10000000; i++) {
							j++;
						}
						return null;
					}
				});
	}

	public static void test() {
		String s = "ddd";
		UtilTimerStack.setActive(true);

		UtilTimerStack.push(s);
		int j = 0;
		for (int i = 0; i < 10000000; i++) {
			j++;
		}
		UtilTimerStack.pop(s);
	}

}
