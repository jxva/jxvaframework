package study.script;

import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class InvDemo {

    public static void main(String args[]) throws Exception {
        // Get the JavaScript engine
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        // Run InvScript.js
        Reader scriptReader = new InputStreamReader(
            InvDemo.class.getResourceAsStream("InvScript.js"));
        try {
            engine.eval(scriptReader);
        } finally {
            scriptReader.close();
        }
        
        // Invoke a JavaScript function
        if (engine instanceof Invocable) {
            Invocable invEngine = (Invocable) engine;
            Object result = invEngine.invokeFunction("demoFunction", 1, 2.3);
            System.out.println("[Java] result: " + result);
            System.out.println("    Java object: "
                    + result.getClass().getName());
            System.out.println();
        } else
            System.out.println("NOT Invocable");
    }
    
}