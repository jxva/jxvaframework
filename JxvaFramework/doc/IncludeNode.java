/**
 * MVEL 2.0
 * Copyright (C) 2007 The Codehaus
 * Mike Brock, Dhanji Prasanna, John Graham, Mark Proctor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mvel2.templates.res;

import static org.mvel2.templates.util.TemplateTools.captureToEOS;
import static org.mvel2.util.ParseTools.subset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;
import org.mvel2.util.ExecutionStack;
import org.mvel2.util.StringAppender;

public class IncludeNode extends Node {
	
	private static final ConcurrentMap<String,String> includes=new ConcurrentHashMap<String,String>(); 
	
    private static final StackThreadLocal<ExecutionStack> relativePathStack;

    private char[] includeExpression;
    private char[] preExpression;

    private static class StackThreadLocal<T> extends ThreadLocal<T> {
        protected T initialValue() {
            ExecutionStack stk = new ExecutionStack();
            //stk.push(".");
            stk.push(Path.APP_PATH);
            return (T) stk;
        }
    }

    static {
        relativePathStack = new StackThreadLocal<ExecutionStack>();
    }

    public IncludeNode(int begin, String name, char[] template, int start, int end) {
        this.begin = begin;
        this.name = name;
        this.contents = subset(template, this.cStart = start, (this.end = this.cEnd = end) - start - 1);

        int mark;
        this.includeExpression = subset(contents, 0, mark = captureToEOS(contents, 0));
        if (mark != contents.length) this.preExpression = subset(contents, ++mark, contents.length - mark);
    }

    public IncludeNode(int begin, String name, char[] template, int start, int end, Node next) {
        this.name = name;
        this.begin = begin;
        this.contents = subset(template, this.cStart = start, (this.end = this.cEnd = end) - start - 1);
        this.next = next;
    }

    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        String file = MVEL.eval(includeExpression, ctx, factory, String.class);

        if (this.preExpression != null) {
            MVEL.eval(preExpression, ctx, factory);
        }

        if (next != null) {
            return next.eval(runtime, appender.append(String.valueOf(TemplateRuntime.eval(readInFile(file), ctx, factory))), ctx, factory);
        }
        else {
            return appender.append(String.valueOf(MVEL.eval(readInFile(file), ctx, factory)));
        }
    }

    public boolean demarcate(Node terminatingNode, char[] template) {
        return false;
    }

    private static Object peek() {
        return relativePathStack.get().peek();
    }

    public static Object pop() {
        return relativePathStack.get().pop();
    }

    private static void push(String path) {
        relativePathStack.get().push(path);
    }

    public static String readInFile(String fileName) {
    	synchronized(fileName){
			String o =includes.get(fileName);
	        if (o == null) {
		        File file = new File(String.valueOf(peek()) + fileName);
		        InputStreamReader input=null;
				BufferedReader br =null;
		        try {
		            push(file.getParent());
		            StringAppender appender = new StringAppender();
		            input = new InputStreamReader(new FileInputStream(file),"UTF-8");
					br = new BufferedReader(input);
					String line =null;
					while ((line = br.readLine()) != null) {
						appender.append(line).append('\n');
					}
		            pop();
		            o=appender.toString();
		            includes.put(fileName,o);
		        }
		        catch (FileNotFoundException e) {
		            throw new TemplateError("cannot include template '" + fileName + "': file not found.");
		        }catch (IOException e) {
		            throw new TemplateError("unknown I/O exception while including '" + fileName + "' (stacktrace nested)", e);
		        }finally{
					try {if(input!=null)input.close();} catch (IOException e) {}
					try {if(br!=null)br.close();} catch (IOException e) {}
				}
	        }
	        return o;
    	}
    }
}
