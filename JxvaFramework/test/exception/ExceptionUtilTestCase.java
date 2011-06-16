/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.jxva.exception.ExceptionUtil;
import com.jxva.exception.NestableException;
import com.jxva.exception.NestableRuntimeException;
import com.jxva.util.SystemUtil;

/**
 * Tests {@link com.jxva.exception.ExceptionUtils}.
 * 
 * <h3>Notes</h3>
 * <p>
 * Make sure this exception code does not depend on Java 1.4 nested exceptions. SVN revision 38990 does not compile with
 * Java 1.3.1.
 * </p>
 * <ul>
 * <li>Compiled with Sun Java 1.3.1_15</li>
 * <li>Tested with Sun Java 1.3.1_15</li>
 * <li>Tested with Sun Java 1.4.2_12</li>
 * <li>Tested with Sun Java 1.5.0_08</li>
 * <li>All of the above on Windows XP SP2 + patches.</li>
 * </ul>
 * <p>
 * Gary Gregory; August 16, 2006.
 * </p>
 * 
 * @author Daniel L. Rall
 * @author <a href="mailto:steven@caswell.name">Steven Caswell</a>
 * @author Stephen Colebourne
 * @author <a href="mailto:ggregory@seagullsw.com">Gary Gregory</a>
 * @since 1.0
 */
public class ExceptionUtilTestCase extends junit.framework.TestCase {
    
    private NestableException nested;
    private Throwable withCause;
    private Throwable withoutCause;
    private Throwable jdkNoCause;
    private ExceptionWithCause selfCause;
    private ExceptionWithCause cyclicCause;

    public ExceptionUtilTestCase(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ExceptionUtilTestCase.class);
    }

    public void setUp() {
        withoutCause = createExceptionWithoutCause();
        nested = new NestableException(withoutCause);
        withCause = new ExceptionWithCause(nested);
        jdkNoCause = new NullPointerException();
        selfCause = new ExceptionWithCause(null);
        selfCause.setCause(selfCause);
        ExceptionWithCause a = new ExceptionWithCause(null);
        ExceptionWithCause b = new ExceptionWithCause(a);
        a.setCause(b);
        cyclicCause = new ExceptionWithCause(a);
    }

    protected void tearDown() throws Exception {
        withoutCause = null;
        nested = null;
        withCause = null;
        jdkNoCause = null;
        selfCause = null;
        cyclicCause = null;
    }

    //-----------------------------------------------------------------------
    private Throwable createExceptionWithoutCause() {
        try {
            throw new ExceptionWithoutCause();
        } catch (Throwable t) {
            return t;
        }
    }

    private Throwable createExceptionWithCause() {
        try {
            try {
                throw new ExceptionWithCause(createExceptionWithoutCause());
            } catch (Throwable t) {
                throw new ExceptionWithCause(t);
            }
        } catch (Throwable t) {
            return t;
        }
    }

    //-----------------------------------------------------------------------
    
    public void testConstructor() {
        assertNotNull(new ExceptionUtil());
        Constructor[] cons = ExceptionUtil.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertEquals(true, Modifier.isPublic(cons[0].getModifiers()));
        assertEquals(true, Modifier.isPublic(ExceptionUtil.class.getModifiers()));
        assertEquals(false, Modifier.isFinal(ExceptionUtil.class.getModifiers()));
    }
    
    //-----------------------------------------------------------------------
    
    public void testCauseMethodNameOps() {
        this.testCauseMethodNameOps(null);
        this.testCauseMethodNameOps("");
        this.testCauseMethodNameOps(" ");
        this.testCauseMethodNameOps("\t\r\n\t");
        this.testCauseMethodNameOps("testMethodName");
    }
    
    void testCauseMethodNameOps(String name) {
        String methodName = "testMethodName";
        try {
            Assert.assertFalse(ExceptionUtil.isCauseMethodName(methodName));
            ExceptionUtil.addCauseMethodName(methodName);            
            ExceptionUtil.addCauseMethodName(methodName);            
            Assert.assertTrue(ExceptionUtil.isCauseMethodName(methodName));
        } finally {
            ExceptionUtil.removeCauseMethodName(methodName);
            Assert.assertFalse(
                    "The method name " + methodName + " should not be in the array", 
                    ExceptionUtil.isCauseMethodName(methodName));
        }
    }
    
    public void testGetCause_Throwable() {
        assertSame(null, ExceptionUtil.getCause(null));
        assertSame(null, ExceptionUtil.getCause(withoutCause));
        assertSame(withoutCause, ExceptionUtil.getCause(nested));
        assertSame(nested, ExceptionUtil.getCause(withCause));
        assertSame(null, ExceptionUtil.getCause(jdkNoCause));
        assertSame(selfCause, ExceptionUtil.getCause(selfCause));
        assertSame(cyclicCause.getCause(), ExceptionUtil.getCause(cyclicCause));
        assertSame(((ExceptionWithCause) cyclicCause.getCause()).getCause(), ExceptionUtil.getCause(cyclicCause.getCause()));
        assertSame(cyclicCause.getCause(), ExceptionUtil.getCause(((ExceptionWithCause) cyclicCause.getCause()).getCause()));
    }

    public void testGetCause_ThrowableArray() {
        assertSame(null, ExceptionUtil.getCause(null, null));
        assertSame(null, ExceptionUtil.getCause(null, new String[0]));

        // match because known type        
        assertSame(withoutCause, ExceptionUtil.getCause(nested, null));
        assertSame(withoutCause, ExceptionUtil.getCause(nested, new String[0]));
        assertSame(withoutCause, ExceptionUtil.getCause(nested, new String[] {"getCause"}));
        
        // not known type, so match on supplied method names
        assertSame(nested, ExceptionUtil.getCause(withCause, null));  // default names
        assertSame(null, ExceptionUtil.getCause(withCause, new String[0]));
        assertSame(null, ExceptionUtil.getCause(withCause, new String[] {null}));
        assertSame(nested, ExceptionUtil.getCause(withCause, new String[] {"getCause"}));
        
        // not known type, so match on supplied method names
        assertSame(null, ExceptionUtil.getCause(withoutCause, null));
        assertSame(null, ExceptionUtil.getCause(withoutCause, new String[0]));
        assertSame(null, ExceptionUtil.getCause(withoutCause, new String[] {null}));
        assertSame(null, ExceptionUtil.getCause(withoutCause, new String[] {"getCause"}));
        assertSame(null, ExceptionUtil.getCause(withoutCause, new String[] {"getTargetException"}));
    }

    public void testGetRootCause_Throwable() {
        assertSame(null, ExceptionUtil.getRootCause(null));
        assertSame(null, ExceptionUtil.getRootCause(withoutCause));
        assertSame(withoutCause, ExceptionUtil.getRootCause(nested));
        assertSame(withoutCause, ExceptionUtil.getRootCause(withCause));
        assertSame(null, ExceptionUtil.getRootCause(jdkNoCause));
        assertSame(null, ExceptionUtil.getRootCause(selfCause));
        assertSame(((ExceptionWithCause) cyclicCause.getCause()).getCause(), ExceptionUtil.getRootCause(cyclicCause));
    }

    public void testSetCause() {
        Exception cause = new ExceptionWithoutCause();
        assertEquals(true, ExceptionUtil.setCause(new ExceptionWithCause(null), cause));
        if (SystemUtil.isJavaVersionAtLeast(140)) {
            assertEquals(true, ExceptionUtil.setCause(new ExceptionWithoutCause(), cause));
        }
    }

    /**
     * Tests overriding a cause to <code>null</code>.
     */
    public void testSetCauseToNull() {
        Exception ex = new ExceptionWithCause(new IOException());
        assertEquals(true, ExceptionUtil.setCause(ex, new IllegalStateException()));
        assertNotNull(ExceptionUtil.getCause(ex));
        assertEquals(true, ExceptionUtil.setCause(ex, null));
        assertNull(ExceptionUtil.getCause(ex));
    }

    //-----------------------------------------------------------------------
    public void testIsThrowableNested() {
        if (SystemUtil.isJavaVersionAtLeast(140)) {
            assertEquals(true, ExceptionUtil.isThrowableNested());
        } else {
            assertEquals(false, ExceptionUtil.isThrowableNested());
        }
    }
    
    public void testIsNestedThrowable_Throwable() {
        assertEquals(true, ExceptionUtil.isNestedThrowable(new SQLException()));
        assertEquals(true, ExceptionUtil.isNestedThrowable(new InvocationTargetException(new Exception())));
        assertEquals(true, ExceptionUtil.isNestedThrowable(new NestableRuntimeException()));
        assertEquals(true, ExceptionUtil.isNestedThrowable(withCause));
        assertEquals(true, ExceptionUtil.isNestedThrowable(nested));
        if (SystemUtil.isJavaVersionAtLeast(140)) {
            assertEquals(true, ExceptionUtil.isNestedThrowable(withoutCause));
            assertEquals(true, ExceptionUtil.isNestedThrowable(new Throwable()));
        } else {
            assertEquals(false, ExceptionUtil.isNestedThrowable(withoutCause));
            assertEquals(false, ExceptionUtil.isNestedThrowable(new Throwable()));
        }
    }

    //-----------------------------------------------------------------------
    public void testGetThrowableCount_Throwable() {
        assertEquals(0, ExceptionUtil.getThrowableCount(null));
        assertEquals(1, ExceptionUtil.getThrowableCount(withoutCause));
        assertEquals(2, ExceptionUtil.getThrowableCount(nested));
        assertEquals(3, ExceptionUtil.getThrowableCount(withCause));
        assertEquals(1, ExceptionUtil.getThrowableCount(jdkNoCause));
        assertEquals(1, ExceptionUtil.getThrowableCount(selfCause));
        assertEquals(3, ExceptionUtil.getThrowableCount(cyclicCause));
    }

    //-----------------------------------------------------------------------
    public void testGetThrowables_Throwable_null() {
        assertEquals(0, ExceptionUtil.getThrowables(null).length);
    }

    public void testGetThrowables_Throwable_withoutCause() {
        Throwable[] throwables = ExceptionUtil.getThrowables(withoutCause);
        assertEquals(1, throwables.length);
        assertSame(withoutCause, throwables[0]);
    }

    public void testGetThrowables_Throwable_nested() {
        Throwable[] throwables = ExceptionUtil.getThrowables(nested);
        assertEquals(2, throwables.length);
        assertSame(nested, throwables[0]);
        assertSame(withoutCause, throwables[1]);
    }

    public void testGetThrowables_Throwable_withCause() {
        Throwable[] throwables = ExceptionUtil.getThrowables(withCause);
        assertEquals(3, throwables.length);
        assertSame(withCause, throwables[0]);
        assertSame(nested, throwables[1]);
        assertSame(withoutCause, throwables[2]);
    }

    public void testGetThrowables_Throwable_jdkNoCause() {
        Throwable[] throwables = ExceptionUtil.getThrowables(jdkNoCause);
        assertEquals(1, throwables.length);
        assertSame(jdkNoCause, throwables[0]);
    }

    public void testGetThrowables_Throwable_selfCause() {
        Throwable[] throwables = ExceptionUtil.getThrowables(selfCause);
        assertEquals(1, throwables.length);
        assertSame(selfCause, throwables[0]);
    }

    public void testGetThrowables_Throwable_recursiveCause() {
        Throwable[] throwables = ExceptionUtil.getThrowables(cyclicCause);
        assertEquals(3, throwables.length);
        assertSame(cyclicCause, throwables[0]);
        assertSame(cyclicCause.getCause(), throwables[1]);
        assertSame(((ExceptionWithCause) cyclicCause.getCause()).getCause(), throwables[2]);
    }

    //-----------------------------------------------------------------------
    public void testGetThrowableList_Throwable_null() {
        List throwables = ExceptionUtil.getThrowableList(null);
        assertEquals(0, throwables.size());
    }

    public void testGetThrowableList_Throwable_withoutCause() {
        List throwables = ExceptionUtil.getThrowableList(withoutCause);
        assertEquals(1, throwables.size());
        assertSame(withoutCause, throwables.get(0));
    }

    public void testGetThrowableList_Throwable_nested() {
        List throwables = ExceptionUtil.getThrowableList(nested);
        assertEquals(2, throwables.size());
        assertSame(nested, throwables.get(0));
        assertSame(withoutCause, throwables.get(1));
    }

    public void testGetThrowableList_Throwable_withCause() {
        List throwables = ExceptionUtil.getThrowableList(withCause);
        assertEquals(3, throwables.size());
        assertSame(withCause, throwables.get(0));
        assertSame(nested, throwables.get(1));
        assertSame(withoutCause, throwables.get(2));
    }

    public void testGetThrowableList_Throwable_jdkNoCause() {
        List throwables = ExceptionUtil.getThrowableList(jdkNoCause);
        assertEquals(1, throwables.size());
        assertSame(jdkNoCause, throwables.get(0));
    }

    public void testGetThrowableList_Throwable_selfCause() {
        List throwables = ExceptionUtil.getThrowableList(selfCause);
        assertEquals(1, throwables.size());
        assertSame(selfCause, throwables.get(0));
    }

    public void testGetThrowableList_Throwable_recursiveCause() {
        List throwables = ExceptionUtil.getThrowableList(cyclicCause);
        assertEquals(3, throwables.size());
        assertSame(cyclicCause, throwables.get(0));
        assertSame(cyclicCause.getCause(), throwables.get(1));
        assertSame(((ExceptionWithCause) cyclicCause.getCause()).getCause(), throwables.get(2));
    }

    //-----------------------------------------------------------------------
    public void testIndexOf_ThrowableClass() {
        assertEquals(-1, ExceptionUtil.indexOfThrowable(null, null));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(null, NestableException.class));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withoutCause, null));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withoutCause, ExceptionWithCause.class));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withoutCause, NestableException.class));
        assertEquals(0, ExceptionUtil.indexOfThrowable(withoutCause, ExceptionWithoutCause.class));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(nested, null));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(nested, ExceptionWithCause.class));
        assertEquals(0, ExceptionUtil.indexOfThrowable(nested, NestableException.class));
        assertEquals(1, ExceptionUtil.indexOfThrowable(nested, ExceptionWithoutCause.class));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withCause, null));
        assertEquals(0, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithCause.class));
        assertEquals(1, ExceptionUtil.indexOfThrowable(withCause, NestableException.class));
        assertEquals(2, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithoutCause.class));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withCause, Exception.class));
    }

    public void testIndexOf_ThrowableClassInt() {
        assertEquals(-1, ExceptionUtil.indexOfThrowable(null, null, 0));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(null, NestableException.class, 0));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withoutCause, null));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withoutCause, ExceptionWithCause.class, 0));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withoutCause, NestableException.class, 0));
        assertEquals(0, ExceptionUtil.indexOfThrowable(withoutCause, ExceptionWithoutCause.class, 0));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(nested, null, 0));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(nested, ExceptionWithCause.class, 0));
        assertEquals(0, ExceptionUtil.indexOfThrowable(nested, NestableException.class, 0));
        assertEquals(1, ExceptionUtil.indexOfThrowable(nested, ExceptionWithoutCause.class, 0));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withCause, null));
        assertEquals(0, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithCause.class, 0));
        assertEquals(1, ExceptionUtil.indexOfThrowable(withCause, NestableException.class, 0));
        assertEquals(2, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithoutCause.class, 0));

        assertEquals(0, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithCause.class, -1));
        assertEquals(0, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithCause.class, 0));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithCause.class, 1));
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withCause, ExceptionWithCause.class, 9));
        
        assertEquals(-1, ExceptionUtil.indexOfThrowable(withCause, Exception.class, 0));
    }

    //-----------------------------------------------------------------------
    public void testIndexOfType_ThrowableClass() {
        assertEquals(-1, ExceptionUtil.indexOfType(null, null));
        assertEquals(-1, ExceptionUtil.indexOfType(null, NestableException.class));
        
        assertEquals(-1, ExceptionUtil.indexOfType(withoutCause, null));
        assertEquals(-1, ExceptionUtil.indexOfType(withoutCause, ExceptionWithCause.class));
        assertEquals(-1, ExceptionUtil.indexOfType(withoutCause, NestableException.class));
        assertEquals(0, ExceptionUtil.indexOfType(withoutCause, ExceptionWithoutCause.class));
        
        assertEquals(-1, ExceptionUtil.indexOfType(nested, null));
        assertEquals(-1, ExceptionUtil.indexOfType(nested, ExceptionWithCause.class));
        assertEquals(0, ExceptionUtil.indexOfType(nested, NestableException.class));
        assertEquals(1, ExceptionUtil.indexOfType(nested, ExceptionWithoutCause.class));
        
        assertEquals(-1, ExceptionUtil.indexOfType(withCause, null));
        assertEquals(0, ExceptionUtil.indexOfType(withCause, ExceptionWithCause.class));
        assertEquals(1, ExceptionUtil.indexOfType(withCause, NestableException.class));
        assertEquals(2, ExceptionUtil.indexOfType(withCause, ExceptionWithoutCause.class));
        
        assertEquals(0, ExceptionUtil.indexOfType(withCause, Exception.class));
    }

    public void testIndexOfType_ThrowableClassInt() {
        assertEquals(-1, ExceptionUtil.indexOfType(null, null, 0));
        assertEquals(-1, ExceptionUtil.indexOfType(null, NestableException.class, 0));
        
        assertEquals(-1, ExceptionUtil.indexOfType(withoutCause, null));
        assertEquals(-1, ExceptionUtil.indexOfType(withoutCause, ExceptionWithCause.class, 0));
        assertEquals(-1, ExceptionUtil.indexOfType(withoutCause, NestableException.class, 0));
        assertEquals(0, ExceptionUtil.indexOfType(withoutCause, ExceptionWithoutCause.class, 0));
        
        assertEquals(-1, ExceptionUtil.indexOfType(nested, null, 0));
        assertEquals(-1, ExceptionUtil.indexOfType(nested, ExceptionWithCause.class, 0));
        assertEquals(0, ExceptionUtil.indexOfType(nested, NestableException.class, 0));
        assertEquals(1, ExceptionUtil.indexOfType(nested, ExceptionWithoutCause.class, 0));
        
        assertEquals(-1, ExceptionUtil.indexOfType(withCause, null));
        assertEquals(0, ExceptionUtil.indexOfType(withCause, ExceptionWithCause.class, 0));
        assertEquals(1, ExceptionUtil.indexOfType(withCause, NestableException.class, 0));
        assertEquals(2, ExceptionUtil.indexOfType(withCause, ExceptionWithoutCause.class, 0));

        assertEquals(0, ExceptionUtil.indexOfType(withCause, ExceptionWithCause.class, -1));
        assertEquals(0, ExceptionUtil.indexOfType(withCause, ExceptionWithCause.class, 0));
        assertEquals(-1, ExceptionUtil.indexOfType(withCause, ExceptionWithCause.class, 1));
        assertEquals(-1, ExceptionUtil.indexOfType(withCause, ExceptionWithCause.class, 9));
        
        assertEquals(0, ExceptionUtil.indexOfType(withCause, Exception.class, 0));
    }

    //-----------------------------------------------------------------------
    public void testPrintRootCauseStackTrace_Throwable() throws Exception {
        ExceptionUtil.printRootCauseStackTrace(null);
        // could pipe system.err to a known stream, but not much point as
        // internally this method calls stram method anyway
    }
    
    public void testPrintRootCauseStackTrace_ThrowableStream() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ExceptionUtil.printRootCauseStackTrace(null, (PrintStream) null);
        ExceptionUtil.printRootCauseStackTrace(null, new PrintStream(out));
        assertEquals(0, out.toString().length());
        
        out = new ByteArrayOutputStream(1024);
        try {
            ExceptionUtil.printRootCauseStackTrace(withCause, (PrintStream) null);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        out = new ByteArrayOutputStream(1024);
        Throwable withCause = createExceptionWithCause();
        ExceptionUtil.printRootCauseStackTrace(withCause, new PrintStream(out));
        String stackTrace = out.toString();
        assertTrue(stackTrace.indexOf(ExceptionUtil.WRAPPED_MARKER) != -1);
        
        out = new ByteArrayOutputStream(1024);
        ExceptionUtil.printRootCauseStackTrace(withoutCause, new PrintStream(out));
        stackTrace = out.toString();
        assertTrue(stackTrace.indexOf(ExceptionUtil.WRAPPED_MARKER) == -1);
    }

    public void testPrintRootCauseStackTrace_ThrowableWriter() throws Exception {
        StringWriter writer = new StringWriter(1024);
        ExceptionUtil.printRootCauseStackTrace(null, (PrintWriter) null);
        ExceptionUtil.printRootCauseStackTrace(null, new PrintWriter(writer));
        assertEquals(0, writer.getBuffer().length());
        
        writer = new StringWriter(1024);
        try {
            ExceptionUtil.printRootCauseStackTrace(withCause, (PrintWriter) null);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        writer = new StringWriter(1024);
        Throwable withCause = createExceptionWithCause();
        ExceptionUtil.printRootCauseStackTrace(withCause, new PrintWriter(writer));
        String stackTrace = writer.toString();
        assertTrue(stackTrace.indexOf(ExceptionUtil.WRAPPED_MARKER) != -1);
        
        writer = new StringWriter(1024);
        ExceptionUtil.printRootCauseStackTrace(withoutCause, new PrintWriter(writer));
        stackTrace = writer.toString();
        assertTrue(stackTrace.indexOf(ExceptionUtil.WRAPPED_MARKER) == -1);
    }

    //-----------------------------------------------------------------------
    public void testGetRootCauseStackTrace_Throwable() throws Exception {
        assertEquals(0, ExceptionUtil.getRootCauseStackTrace(null).length);
        
        Throwable withCause = createExceptionWithCause();
        String[] stackTrace = ExceptionUtil.getRootCauseStackTrace(withCause);
        boolean match = false;
        for (int i = 0; i < stackTrace.length; i++) {
            if (stackTrace[i].startsWith(ExceptionUtil.WRAPPED_MARKER)) {
                match = true;
                break;
            }
        }
        assertEquals(false, match);
        
        stackTrace = ExceptionUtil.getRootCauseStackTrace(withoutCause);
        match = false;
        for (int i = 0; i < stackTrace.length; i++) {
            if (stackTrace[i].startsWith(ExceptionUtil.WRAPPED_MARKER)) {
                match = true;
                break;
            }
        }
        assertEquals(false, match);
    }

    public void testRemoveCommonFrames_ListList() throws Exception {
        try {
            ExceptionUtil.removeCommonFrames(null, null);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    public void test_getMessage_Throwable() {
        Throwable th = null;
        assertEquals("", ExceptionUtil.getMessage(th));
        
        th = new IllegalArgumentException("Base");
        assertEquals("IllegalArgumentException: Base", ExceptionUtil.getMessage(th));
        
        th = new ExceptionWithCause("Wrapper", th);
        assertEquals("ExceptionUtilTestCase.ExceptionWithCause: Wrapper", ExceptionUtil.getMessage(th));
    }

    public void test_getRootCauseMessage_Throwable() {
        Throwable th = null;
        assertEquals("", ExceptionUtil.getRootCauseMessage(th));
        
        th = new IllegalArgumentException("Base");
        assertEquals("IllegalArgumentException: Base", ExceptionUtil.getRootCauseMessage(th));
        
        th = new ExceptionWithCause("Wrapper", th);
        assertEquals("IllegalArgumentException: Base", ExceptionUtil.getRootCauseMessage(th));
    }

    //-----------------------------------------------------------------------
    /**
     * Provides a method with a well known chained/nested exception
     * name which matches the full signature (e.g. has a return value
     * of <code>Throwable</code>.
     */
    private static class ExceptionWithCause extends Exception {
        private Throwable cause;

        public ExceptionWithCause(String str, Throwable cause) {
            super(str);
            setCause(cause);
        }

        public ExceptionWithCause(Throwable cause) {
            super();
            setCause(cause);
        }

        public Throwable getCause() {
            return cause;
        }

        public void setCause(Throwable cause) {
            this.cause = cause;
        }
    }

    /**
     * Provides a method with a well known chained/nested exception
     * name which does not match the full signature (e.g. lacks a
     * return value of <code>Throwable</code>.
     */
    private static class ExceptionWithoutCause extends Exception {
        public void getTargetException() {
        }
    }

}
