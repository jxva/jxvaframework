package demo.log;

import java.util.logging.Logger;

import com.jxva.entity.Path;

public class Test{

     public static void main(String[] args){
    	 
    	 test2();
    	// test();

     }
     public static void test2(){
    	 com.jxva.log.Logger l=com.jxva.log.Logger.getLogger(Test.class);
    	 l.fatal("fatal");
      	 l.error("error");
    	 l.warn("warn");
    	 l.info("info");
    	 l.debug("debug");
    	 l.trace("trace");
     }
     
     
     public static void test(){
		System.setProperty("java.util.logging.config.file",Path.CLASS_PATH+"logging.properties");
		Logger log = Logger.getLogger("com.jxva.log");
        
     	//StreamHandler sh = new StreamHandler(System.out, new SimpleFormatter());    	
        //log.addHandler(sh);
        log.info("Hello World");
     }
}