package demo.dao.other;

import com.jxva.entity.Encoding;
import com.jxva.http.HttpTransfer;


class Test1 extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			new HttpTransfer("http://passport.ztemc.com/SsoServer!login.zte?username=admin&password=adminfdsa").get(Encoding.UTF_8);
		}
	}
}

class Test2 extends Thread{
	public void run(){
		for(int i=0;i<40;i++){
			new HttpTransfer("http://passport.ztemc.com/SsoServer!login.zte?username=admin&password=adminfdsa").get(Encoding.UTF_8);
		}
	}
}

public class HttpThread{
	public static void main(String[] args){
		for(int i=0;i<50;i++){
			new Test1().start();
			new Test2().start();
		}
	}
}
