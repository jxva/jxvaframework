package net.jxva.mvc;

import com.jxva.entity.Encoding;
import com.jxva.http.HttpTransfer;


class Test1 extends Thread{
	public void run(){
		//for(int i=0;i<10;i++){
			new HttpTransfer("http://127.0.0.1:8080/ztemusic/news!listNews.zte").get(Encoding.UTF_8);
		//}
	}
}

class Test2 extends Thread{
	public void run(){
		//for(int i=0;i<10;i++){
			new HttpTransfer("http://127.0.0.1:8080/ztemusic/news!listNews.zte").get(Encoding.UTF_8);
		//}
	}
}

public class HttpThread{
	public static void main(String[] args) throws InterruptedException{
		for(int i=0;i<1000;i++){
			new Test1().start();
			new Test2().start();
			//System.out.println("start "+i);
		}
	}
}
