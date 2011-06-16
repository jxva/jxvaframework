package demo.log;

import com.jxva.log.Logger;

/**
 * 100个并发,总共10000次调用
 * @author jxva
 * @since 1.0.0
 *
 */
public class TestThreadLog implements Runnable{
	Logger l=Logger.getLogger(Test.class);
	public static void main(String[] args){
		for(int i=0;i<100;i++){
			new Thread(new TestThreadLog()).start();
		}
     }

	public void run() {
		for(int i=0;i<100;i++){
	    	 l.fatal("fatal");
	      	 l.error("error");
	    	 l.warn("warn");
	    	 l.info("info");
	    	 l.debug("debug");
	    	 l.trace("trace");
		}
	}
}
