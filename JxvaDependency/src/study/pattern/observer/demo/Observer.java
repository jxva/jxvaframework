package study.pattern.observer.demo;
/** 抽象观察者角色 */
    public interface Observer {
        /**
         * 调用这个方法会更新自己
         */
        void update();
    }

    