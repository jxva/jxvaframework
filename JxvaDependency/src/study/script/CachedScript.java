package study.script;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptException;

public class CachedScript {
    private Compilable scriptEngine;
    private File scriptFile;
    private CompiledScript compiledScript;
    private Date compiledDate;

    public CachedScript(Compilable scriptEngine, File scriptFile) {
        this.scriptEngine = scriptEngine;
        this.scriptFile = scriptFile;
    }
    
    public CompiledScript getCompiledScript()
            throws ScriptException, IOException {
        Date scriptDate = new Date(scriptFile.lastModified());
        if (compiledDate == null || scriptDate.after(compiledDate)) {
            Reader reader = new FileReader(scriptFile);
            try {
                compiledScript = scriptEngine.compile(reader);
                compiledDate = scriptDate;
            } finally {
                reader.close();
            }
        }
        return compiledScript;
    }

}