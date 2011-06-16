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
package study.pattern.observer.demo;

/**
 *行为模式，为实现此模式，应该具备这几个角色：被观察的主题，观察者。
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-16 15:11:10 by Jxva
 */
public class Client {

	  //客户端的 main() 如下：
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserver();
        Observer observer2 = new ConcreteObserver();
        subject.attach(observer1);
        subject.attach(observer2);
        subject.notifyObservers();
    }

}
