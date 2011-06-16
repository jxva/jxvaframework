/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package study.pattern.observer.jxva;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-16 15:47:18 by Jxva
 */
public class ConcreteObserver implements Observer {

	private String name;
	
	public ConcreteObserver(String name){
		this.name=name;
	}
	
	/* (non-Javadoc)
	 * @see study.pattern.observer.jxva.Observer#update()
	 */
	@Override
	public void update() {
		System.out.println(" notify "+name);
	}

}
