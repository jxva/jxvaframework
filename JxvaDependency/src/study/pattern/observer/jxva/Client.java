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
 * @version 2008-12-16 15:48:39 by Jxva
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Subject s=new Subject();
		Observer o1=new ConcreteObserver("test1");
		Observer o2=new ConcreteObserver("test2");
		s.add(o1);
		s.add(o2);
		s.notifyObservers();
	}

}