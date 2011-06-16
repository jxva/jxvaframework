package study.pool;


import java.util.LinkedList;
import java.util.List;

public class ThreadPool{

    private static ThreadPool instance = null;

    // 优先级低
    public static final int PRIORITY_LOW = 0;

    // 普通
    public static final int PRIORITY_NORMAL = 1;

    // 高
    public static final int PRIORITY_HIGH = 2;

    // 用以保存空闲连接
    private List[] _idxThreads;

    // 关闭
    private boolean _shutdown = false;

    // 线程数量
    private int _threadCount = 0;

    // debug信息是否输出
    private boolean _debug = false;

    /** *//**
     * 返回ThreadPool实例
     * 
     * @return
     */
    public static ThreadPool getInstance(){
        if (instance == null) {
                    instance = new ThreadPool();
            }
        return instance;
    }

    // 初始化线程list
    private ThreadPool() {
        this._idxThreads = new List[] { 
        		new LinkedList(), new LinkedList(),
                new LinkedList()};
        this._threadCount=0;
    }

    /** *//**
     * 同步方法，完成任务后将资源放回线程池中
     * @param repool
     */
    protected synchronized void repool(Pooled repool) {
        if (this._shutdown) {
            if (this._debug) {
                System.out.println("ThreadPool.repool():重设中……");
            }
            // 优先级别判定
            switch (repool.getPriority()) {
            case Thread.MIN_PRIORITY:
                this._idxThreads[PRIORITY_LOW].add(repool);
                break;
            case Thread.NORM_PRIORITY:
                this._idxThreads[PRIORITY_NORMAL].add(repool);
                break;
            case Thread.MAX_PRIORITY:
                this._idxThreads[PRIORITY_HIGH].add(repool);
                break;
            default:
                throw new IllegalStateException("没有此种级别");
            }
            // 通知所有线程
            notifyAll();

        } else{
            if (this._debug) {
                System.out.println("ThreadPool.repool():注销中……");
            }
            repool.shutDown();
        }
        if(this._debug){
            System.out.println("ThreadPool.repool():完成");
        }
    }
    public void setDebug(boolean debug){
        this._debug=debug;
    }
    public synchronized  void shutDown(){
        this._shutdown=true;
        if(this._debug){
            System.out.println("ThreadPool.shutDown():关闭中……");
        }
        for(int index=0;index<=PRIORITY_NORMAL;index++){
            List threads=this._idxThreads[index];
            for(int threadIndex=0;threadIndex<threads.size();threadIndex++){
                Pooled idleThread=(Pooled)threads.get(threadIndex);
                idleThread.shutDown();
            }
        }
        notifyAll();
    }
    
    /** *//**
     * 以指定的优先级启动线程
     * @param target
     * @param priority
     */
    public synchronized void start(Runnable target,int priority){
        Pooled thread=null;
        List idleList=this._idxThreads[priority];
        int idleSize=idleList.size();

        if(idleSize>0){
            int lastIndex=idleSize-1;
            thread=(Pooled)idleList.get(lastIndex);
            idleList.remove(idleList);
            thread.setTarget(target);
        }else{
            this._threadCount++;
            thread=new Pooled(target,"Pooled->"+this._threadCount,this);
            switch(priority){
            
            case PRIORITY_LOW:
                thread.setPriority(Thread.MIN_PRIORITY);
                break;
            case PRIORITY_NORMAL:
                thread.setPriority(Thread.NORM_PRIORITY);
                break;
            case PRIORITY_HIGH:
                thread.setPriority(Thread.MAX_PRIORITY);
                break;
                default:
                    thread.setPriority(Thread.NORM_PRIORITY);
            }
            //启动
            thread.start();
    
            }
        
        
    }

    /** *//**
     * 返回线程数量
     * 
     * @return
     */
    public int getThreadsCount() {
        return this._threadCount;
    }

}