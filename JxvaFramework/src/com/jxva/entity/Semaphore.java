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
 *
 */
package com.jxva.entity;


/**
 * 
 * @author The Jxva Framework
 * @since 1.0
 * @version 2009-01-20 13:12:52 by Jxva
 */
public class Semaphore {

	private int count;

	public Semaphore(int count) {
		this.count = count;
	}

	public void acquire() {
		try {
			synchronized (this) {
				while (count == 0) {
					wait();
				}
				count--;
			}
		} catch (InterruptedException e) {
			//ignore
		}
	}

	public void release() {
		synchronized (this) {
			count++;
			notify();
		}
	}

}
