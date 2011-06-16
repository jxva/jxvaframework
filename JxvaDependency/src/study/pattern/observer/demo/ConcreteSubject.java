package study.pattern.observer.demo;

import java.util.Iterator;
import java.util.Vector;

/** 具体主题角色，属于被观察者 */
    public class ConcreteSubject implements Subject {    
    	
        private Vector<Observer> observersVector = new Vector<Observer>();
        /** 这里省略了几个方法的实现，无非是往向量中添加 Subject, 没什么好写的 */  
        /**
         * 通知所有登记过的观察者对象
         * 这里和书有一点点的不一样，我用的是 Iterator 接口来实现
         * 书上的是 Enumeration ，但大同小异。
         */
        public void notifyObservers() {
            Iterator<Observer> it = observersVector.iterator();
            while(it.hasNext()) {
                it.next().update();
            }
        }
		/* (non-Javadoc)
		 * @see study.pattern.observer.Subject#attach(study.pattern.observer.Observer)
		 */
		@Override
		public void attach(Observer observer) {
			observersVector.add(observer);	
		}
		/* (non-Javadoc)
		 * @see study.pattern.observer.Subject#detach(study.pattern.observer.Observer)
		 */
		@Override
		public void detach(Observer observer) {
			observersVector.remove(observer);			
		}
    }

    