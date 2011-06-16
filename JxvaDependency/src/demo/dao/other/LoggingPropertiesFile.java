package demo.dao.other;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class LoggingPropertiesFile{
   public static void main(String[] args)throws Exception{

	LogManager lm = LogManager.getLogManager();
	 FileInputStream fi = new FileInputStream(new File("D:/eclipse/workspace/JxvaTest/src/com/jxva/demo/logging.properties"));
	 lm.readConfiguration(fi);
     Logger log = Logger.getLogger("loggingTest");
     lm.addLogger(log);

     //log.setLevel(Level.INFO);
     
     //ConsoleHandler consoleHandler = new ConsoleHandler();
     //log.addHandler(consoleHandler);
     
     //FileHandler myFileHandler = new FileHandler("SimpleLogger.log",50000,1,true);
    // myFileHandler.setEncoding("UTF-8");
     //myFileHandler.setFormatter(new SimpleFormatter());
     //log.addHandler(myFileHandler);

     log.severe("严重dddd的信dddd息");
     log.warning("警dd告信息");
     log.info("一般信息");
     log.config("设定方面的信息");
     log.fine("细微的信息");
     log.finer("更细微的信息");
     log.finest("最细微的信息");
    }
} 