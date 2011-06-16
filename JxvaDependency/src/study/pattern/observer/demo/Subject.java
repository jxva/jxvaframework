package study.pattern.observer.demo;
 /** 抽象主题角色，属于被观察者 */
    public interface Subject {
        /**
         * 登记一个新的观察者对象
         */
        public void attach(Observer observer);
        /**
         * 删除一个观察者对象
         */
        public void detach(Observer observer);
        /**
         * 通知所有登记过的观察者对象
         */
        void notifyObservers();
    }

    