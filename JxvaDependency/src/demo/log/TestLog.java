package demo.log;

import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.jxva.entity.Path;

 class TestFormatter extends Formatter {

    public String format(LogRecord record) {

        return "INFO MESSAGE:" + record.getMessage();

    }

}







 class TestFilter implements Filter {

    public boolean isLoggable(LogRecord record) {

        if (record.getLevel() == Level.INFO)

             return true;

        else

             return false;

    }

}







 class TestHandler extends Handler {

    public void publish(LogRecord r) {

        if (!isLoggable(r))

             return;

        System.out.println(getFormatter().format(r));

    }

    public void close() throws SecurityException {}

    public void flush() {}

}







 
public class TestLog {

    public static void main(String[] args) {

    	System.setProperty("java.util.loggin.config.file",Path.CLASS_PATH+"logging.properties");
    	
        Logger log = Logger.getLogger("Test");

        //log.setLevel(Level.ALL);     
        
        /**
         * 说明:在主程序里面,我们调用了setUseParentHandlers(false)方法,这样做是为了禁止当前

的Logger调用其父类Logger,默认情况下该值为true。
         */

        log.setUseParentHandlers(false);

        TestHandler th = new TestHandler();

        th.setFilter(new TestFilter());

        th.setFormatter(new TestFormatter());

        log.addHandler(th);

        log.info("info");

        log.fine("fine");

    }

}