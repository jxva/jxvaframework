package demo.thread;

/**
 * 同步的互斥类
 * @author Administrator
 *
 */
public class SynchronizedMutex {
    private Thread curOwner = null;
    public synchronized void acquire() throws InterruptedException {
        if (Thread.interrupted()) throw new InterruptedException();
        while (curOwner != null) 
            wait();
        curOwner = Thread.currentThread();
    }
    public synchronized void release() {
        if (curOwner == Thread.currentThread()) {
            curOwner = null;
            notify();
        } else
            throw new IllegalStateException("not owner of mutex");
    }
}
      