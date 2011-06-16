package study.pool;

public class ThreadPoolTest {

    
    private static Runnable createRunnable(final int id) {
        return new Runnable() {
            public void run() {
                System.out.println("线程" + id + ",运行 ");
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex) { }
                System.out.println("线程" + id + ",结束");
            }
        };
    }
    
    public static void main(String[]args){
        ThreadPool pool=ThreadPool.getInstance();
        pool.setDebug(true);
         for (int i=1; i<=10; i++) {
             //根据数值，设定不同优先级
             if(i%2==0){
                     pool.start(createRunnable(i), ThreadPool.PRIORITY_HIGH);
             }else{
                     pool.start(createRunnable(i), ThreadPool.PRIORITY_LOW);
             }
         }
        System.out.println("线程池测试中……");
        System.out.println("线程池线程总数:"+pool.getThreadsCount());
        pool.shutDown();
    }
}
