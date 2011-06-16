package demo.thread;
public class DAOLock {  
    protected Thread busyFlag = null;  
  
    protected int busyCount = 0;  
  
    public synchronized void lock() throws InterruptedException {  
        while (tryLock() == false) {  
            wait();  
        }  
    }  
  
    public synchronized void unlock() {  
        if (busyFlag == Thread.currentThread()) {  
            busyCount--;  
            if (busyCount == 0) {  
                busyFlag = null;  
                notify();  
            }  
        }  
    }  
  
    public boolean tryLock() {  
        if (busyFlag == null) {  
            busyFlag = Thread.currentThread();  
            busyCount = 1;  
            return true;  
        }  
        if (busyFlag == Thread.currentThread()) {  
            busyCount++;  
            return true;  
        }  
        return false;  
    }  
}  