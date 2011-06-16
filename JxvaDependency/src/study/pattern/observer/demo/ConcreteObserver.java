package study.pattern.observer.demo;
/** 具体观察者角色 */
    public class ConcreteObserver implements Observer {
        /**
         * 调用这个方法会更新自己
         */
        public void update() {
            System.out.println(this + "   I am notified");
        }
    }

  
