package demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


class Test1 extends Thread{
	void print(){
		System.out.println("test1 print");
	}
	void close(){
		System.out.println("test1 close");
	}
}

class Test2 extends Thread{
	void print(){
		System.out.println("test2 print");
	}
	void close(){
		System.out.println("test2 close");
	}
}

public class TestDAOLock extends Thread{
	
	//private static DAOLock lock=new DAOLock();
	private static ReentrantLock lock=new ReentrantLock();
	public static void main(String args[]){
		for(int i=0;i<2;i++){
			new TestDAOLock().start();
		}
	}
	
	public void run(){
		try{
			System.out.println("hehe");
			lock.tryLock(10,TimeUnit.SECONDS);
			System.out.println("     print");
			//Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//lock.unlock();
		}
	}
}
