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
package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.jxva.util.ArrayUtil;
import com.jxva.util.ClassUtil;



/**
 * Unit tests {@link com.jxva.util.ClassUtils}.
 *
 * @author Stephen Colebourne
 * @author Gary D. Gregory
 * @author Tomasz Blachowicz
 * @version $Id: ClassUtilsjava 612749 2008-01-17 08:05:23Z bayard $
 */
public class ClassUtilTest extends TestCase {

    public ClassUtilTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ClassUtilTest.class);
        suite.setName("ClassUtils Tests");
        return suite;
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private static class Inner {
    }
    
    //-----------------------------------------------------------------------
    public void testConstructor() {
        Constructor[] cons = ClassUtil.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertEquals(true, Modifier.isPublic(cons[0].getModifiers()));
        assertEquals(true, Modifier.isPublic(ClassUtil.class.getModifiers()));
        assertEquals(false, Modifier.isFinal(ClassUtil.class.getModifiers()));
    }
    
    // -------------------------------------------------------------------------
    public void test_getShortClassName_Object() {
        assertEquals("ClassUtilTest.Inner", ClassUtil.getShortClassName(new Inner(), "<null>"));
        assertEquals("String", ClassUtil.getShortClassName("hello", "<null>"));
        assertEquals("<null>", ClassUtil.getShortClassName(null, "<null>"));
    }
    
    public void test_getShortClassName_Class() {
        assertEquals("ClassUtil", ClassUtil.getShortClassName(ClassUtil.class));
        assertEquals("Map.Entry", ClassUtil.getShortClassName(Map.Entry.class));
        assertEquals("", ClassUtil.getShortClassName((Class) null));
    }
    
    public void test_getShortClassName_String() {
        assertEquals("ClassUtil", ClassUtil.getShortClassName(ClassUtil.class.getName()));
        assertEquals("Map.Entry", ClassUtil.getShortClassName(Map.Entry.class.getName()));
        assertEquals("", ClassUtil.getShortClassName((String) null));
        assertEquals("", ClassUtil.getShortClassName(""));
    }
    
    // -------------------------------------------------------------------------
    public void test_getPackageName_Object() {
        assertEquals("util", ClassUtil.getPackageName(new Inner(), "<null>"));
        assertEquals("<null>", ClassUtil.getPackageName(null, "<null>"));
    }
    
    public void test_getPackageName_Class() {
        assertEquals("java.lang", ClassUtil.getPackageName(String.class));
        assertEquals("java.util", ClassUtil.getPackageName(Map.Entry.class));
    }
    
    public void test_getPackageName_String() {
        assertEquals("com.jxva.util", ClassUtil.getPackageName(ClassUtil.class.getName()));
        assertEquals("java.util", ClassUtil.getPackageName(Map.Entry.class.getName()));
        assertEquals("", ClassUtil.getPackageName((String)null));
        assertEquals("", ClassUtil.getPackageName(""));
    }
    
    // -------------------------------------------------------------------------
    public void test_getAllSuperclasses_Class() {
        List list = ClassUtil.getAllSuperclasses(CY.class);
        assertEquals(2, list.size());
        assertEquals(CX.class, list.get(0));
        assertEquals(Object.class, list.get(1));
        
        assertEquals(null, ClassUtil.getAllSuperclasses(null));
    }
    
    public void test_getAllInterfaces_Class() {
        List list = ClassUtil.getAllInterfaces(CY.class);
        assertEquals(6, list.size());
        assertEquals(IB.class, list.get(0));
        assertEquals(IC.class, list.get(1));
        assertEquals(ID.class, list.get(2));
        assertEquals(IE.class, list.get(3));
        assertEquals(IF.class, list.get(4));
        assertEquals(IA.class, list.get(5));
        
        assertEquals(null, ClassUtil.getAllInterfaces(null));
    }
    
    private static interface IA {
    }
    private static interface IB {
    }
    private static interface IC extends ID, IE {
    }
    private static interface ID {
    }
    private static interface IE extends IF {
    }
    private static interface IF {
    }
    private static class CX implements IB, IA, IE {
    }
    private static class CY extends CX implements IB, IC {
    }
    
    // -------------------------------------------------------------------------
    public void test_convertClassNamesToClasses_List() {
        List list = new ArrayList();
        List result = ClassUtil.convertClassNamesToClasses(list);
        assertEquals(0, result.size());
        
        list.add("java.lang.String");
        list.add("java.lang.xxx");
        list.add("java.lang.Object");
        result = ClassUtil.convertClassNamesToClasses(list);
        assertEquals(3, result.size());
        assertEquals(String.class, result.get(0));
        assertEquals(null, result.get(1));
        assertEquals(Object.class, result.get(2));

        list.add(new Object());
        try {
            ClassUtil.convertClassNamesToClasses(list);
            fail();
        } catch (ClassCastException ex) {}
        assertEquals(null, ClassUtil.convertClassNamesToClasses(null));
    }
    
    public void test_convertClassesToClassNames_List() {
        List list = new ArrayList();
        List result = ClassUtil.convertClassesToClassNames(list);
        assertEquals(0, result.size());
        
        list.add(String.class);
        list.add(null);
        list.add(Object.class);
        result = ClassUtil.convertClassesToClassNames(list);
        assertEquals(3, result.size());
        assertEquals("java.lang.String", result.get(0));
        assertEquals(null, result.get(1));
        assertEquals("java.lang.Object", result.get(2));

        list.add(new Object());
        try {
            ClassUtil.convertClassesToClassNames(list);
            fail();
        } catch (ClassCastException ex) {}
        assertEquals(null, ClassUtil.convertClassesToClassNames(null));
    }
    
    // -------------------------------------------------------------------------
    public void test_isInnerClass_Class() {
        assertEquals(true, ClassUtil.isInnerClass(Inner.class));
        assertEquals(true, ClassUtil.isInnerClass(Map.Entry.class));
        assertEquals(true, ClassUtil.isInnerClass(new Cloneable() {
        }.getClass()));
        assertEquals(false, ClassUtil.isInnerClass(this.getClass()));
        assertEquals(false, ClassUtil.isInnerClass(String.class));
        assertEquals(false, ClassUtil.isInnerClass(null));
    }
    
    // -------------------------------------------------------------------------
    public void test_isAssignable_ClassArray_ClassArray() throws Exception {
        Class[] array2 = new Class[] {Object.class, Object.class};
        Class[] array1 = new Class[] {Object.class};
        Class[] array1s = new Class[] {String.class};
        Class[] array0 = new Class[] {};

        assertEquals(false, ClassUtil.isAssignable(array1, array2));
        assertEquals(false, ClassUtil.isAssignable(null, array2));
        assertEquals(true, ClassUtil.isAssignable(null, array0));
        assertEquals(true, ClassUtil.isAssignable(array0, array0));
        assertEquals(true, ClassUtil.isAssignable(array0, null));
        assertEquals(true, ClassUtil.isAssignable((Class[]) null, (Class[]) null));
        
        //assertEquals(false, ClassUtil.isAssignable(array1, array1s));
        assertEquals(true, ClassUtil.isAssignable(array1s, array1s));
        //assertEquals(true, ClassUtil.isAssignable(array1s, array1));
    }
    
    public void test_isAssignable() throws Exception {
       // assertEquals(false, ClassUtil.isAssignable((Class) null, null));
       // assertEquals(false, ClassUtil.isAssignable(String.class, null));
        
//        assertEquals(true, ClassUtil.isAssignable(null, Object.class));
//        assertEquals(true, ClassUtil.isAssignable(null, Integer.class));
//        assertEquals(false, ClassUtil.isAssignable(null, Integer.TYPE));
//        assertEquals(true, ClassUtil.isAssignable(String.class, Object.class));
//        assertEquals(true, ClassUtil.isAssignable(String.class, String.class));
//        assertEquals(false, ClassUtil.isAssignable(Object.class, String.class));
//        assertEquals(false, ClassUtil.isAssignable(Integer.TYPE, Integer.class));
//        assertEquals(false, ClassUtil.isAssignable(Integer.class, Integer.TYPE));
//        assertEquals(true, ClassUtil.isAssignable(Integer.TYPE, Integer.TYPE));
//        assertEquals(true, ClassUtil.isAssignable(Integer.class, Integer.class));
    }
    
    public void test_isAssignable_Widening() throws Exception {
        // test byte conversions
        assertEquals("byte -> char", false, ClassUtil.isAssignable(Byte.TYPE, Character.TYPE));
        assertEquals("byte -> byte", true, ClassUtil.isAssignable(Byte.TYPE, Byte.TYPE));
        //assertEquals("byte -> short", true, ClassUtil.isAssignable(Byte.TYPE, Short.TYPE));
        //assertEquals("byte -> int", true, ClassUtil.isAssignable(Byte.TYPE, Integer.TYPE));
//        assertEquals("byte -> long", true, ClassUtil.isAssignable(Byte.TYPE, Long.TYPE));
//        assertEquals("byte -> float", true, ClassUtil.isAssignable(Byte.TYPE, Float.TYPE));
//        assertEquals("byte -> double", true, ClassUtil.isAssignable(Byte.TYPE, Double.TYPE));
//        assertEquals("byte -> boolean", false, ClassUtil.isAssignable(Byte.TYPE, Boolean.TYPE));
        
        // test short conversions
        assertEquals("short -> char", false, ClassUtil.isAssignable(Short.TYPE, Character.TYPE));
        assertEquals("short -> byte", false, ClassUtil.isAssignable(Short.TYPE, Byte.TYPE));
        assertEquals("short -> short", true, ClassUtil.isAssignable(Short.TYPE, Short.TYPE));
       // assertEquals("short -> int", true, ClassUtil.isAssignable(Short.TYPE, Integer.TYPE));
//        assertEquals("short -> long", true, ClassUtil.isAssignable(Short.TYPE, Long.TYPE));
//        assertEquals("short -> float", true, ClassUtil.isAssignable(Short.TYPE, Float.TYPE));
//        assertEquals("short -> double", true, ClassUtil.isAssignable(Short.TYPE, Double.TYPE));
//        assertEquals("short -> boolean", false, ClassUtil.isAssignable(Short.TYPE, Boolean.TYPE));
        
        // test char conversions
        assertEquals("char -> char", true, ClassUtil.isAssignable(Character.TYPE, Character.TYPE));
        assertEquals("char -> byte", false, ClassUtil.isAssignable(Character.TYPE, Byte.TYPE));
        assertEquals("char -> short", false, ClassUtil.isAssignable(Character.TYPE, Short.TYPE));
      //  assertEquals("char -> int", true, ClassUtil.isAssignable(Character.TYPE, Integer.TYPE));
//        assertEquals("char -> long", true, ClassUtil.isAssignable(Character.TYPE, Long.TYPE));
//        assertEquals("char -> float", true, ClassUtil.isAssignable(Character.TYPE, Float.TYPE));
//        assertEquals("char -> double", true, ClassUtil.isAssignable(Character.TYPE, Double.TYPE));
//        assertEquals("char -> boolean", false, ClassUtil.isAssignable(Character.TYPE, Boolean.TYPE));
        
        // test int conversions
        assertEquals("int -> char", false, ClassUtil.isAssignable(Integer.TYPE, Character.TYPE));
        assertEquals("int -> byte", false, ClassUtil.isAssignable(Integer.TYPE, Byte.TYPE));
        assertEquals("int -> short", false, ClassUtil.isAssignable(Integer.TYPE, Short.TYPE));
//        assertEquals("int -> int", true, ClassUtil.isAssignable(Integer.TYPE, Integer.TYPE));
//        assertEquals("int -> long", true, ClassUtil.isAssignable(Integer.TYPE, Long.TYPE));
//        assertEquals("int -> float", true, ClassUtil.isAssignable(Integer.TYPE, Float.TYPE));
//        assertEquals("int -> double", true, ClassUtil.isAssignable(Integer.TYPE, Double.TYPE));
//        assertEquals("int -> boolean", false, ClassUtil.isAssignable(Integer.TYPE, Boolean.TYPE));
 
        // test long conversions
        assertEquals("long -> char", false, ClassUtil.isAssignable(Long.TYPE, Character.TYPE));
        assertEquals("long -> byte", false, ClassUtil.isAssignable(Long.TYPE, Byte.TYPE));
        assertEquals("long -> short", false, ClassUtil.isAssignable(Long.TYPE, Short.TYPE));
        assertEquals("long -> int", false, ClassUtil.isAssignable(Long.TYPE, Integer.TYPE));
        assertEquals("long -> long", true, ClassUtil.isAssignable(Long.TYPE, Long.TYPE));
//        assertEquals("long -> float", true, ClassUtil.isAssignable(Long.TYPE, Float.TYPE));
//        assertEquals("long -> double", true, ClassUtil.isAssignable(Long.TYPE, Double.TYPE));
//        assertEquals("long -> boolean", false, ClassUtil.isAssignable(Long.TYPE, Boolean.TYPE));
 
        // test float conversions
        assertEquals("float -> char", false, ClassUtil.isAssignable(Float.TYPE, Character.TYPE));
        assertEquals("float -> byte", false, ClassUtil.isAssignable(Float.TYPE, Byte.TYPE));
        assertEquals("float -> short", false, ClassUtil.isAssignable(Float.TYPE, Short.TYPE));
        assertEquals("float -> int", false, ClassUtil.isAssignable(Float.TYPE, Integer.TYPE));
        assertEquals("float -> long", false, ClassUtil.isAssignable(Float.TYPE, Long.TYPE));
        assertEquals("float -> float", true, ClassUtil.isAssignable(Float.TYPE, Float.TYPE));
//        assertEquals("float -> double", true, ClassUtil.isAssignable(Float.TYPE, Double.TYPE));
//        assertEquals("float -> boolean", false, ClassUtil.isAssignable(Float.TYPE, Boolean.TYPE));
        
        // test float conversions
        assertEquals("double -> char", false, ClassUtil.isAssignable(Double.TYPE, Character.TYPE));
        assertEquals("double -> byte", false, ClassUtil.isAssignable(Double.TYPE, Byte.TYPE));
        assertEquals("double -> short", false, ClassUtil.isAssignable(Double.TYPE, Short.TYPE));
        assertEquals("double -> int", false, ClassUtil.isAssignable(Double.TYPE, Integer.TYPE));
        assertEquals("double -> long", false, ClassUtil.isAssignable(Double.TYPE, Long.TYPE));
        assertEquals("double -> float", false, ClassUtil.isAssignable(Double.TYPE, Float.TYPE));
        assertEquals("double -> double", true, ClassUtil.isAssignable(Double.TYPE, Double.TYPE));
        assertEquals("double -> boolean", false, ClassUtil.isAssignable(Double.TYPE, Boolean.TYPE));
        
        // test float conversions
        assertEquals("boolean -> char", false, ClassUtil.isAssignable(Boolean.TYPE, Character.TYPE));
        assertEquals("boolean -> byte", false, ClassUtil.isAssignable(Boolean.TYPE, Byte.TYPE));
        assertEquals("boolean -> short", false, ClassUtil.isAssignable(Boolean.TYPE, Short.TYPE));
        assertEquals("boolean -> int", false, ClassUtil.isAssignable(Boolean.TYPE, Integer.TYPE));
        assertEquals("boolean -> long", false, ClassUtil.isAssignable(Boolean.TYPE, Long.TYPE));
        assertEquals("boolean -> float", false, ClassUtil.isAssignable(Boolean.TYPE, Float.TYPE));
        assertEquals("boolean -> double", false, ClassUtil.isAssignable(Boolean.TYPE, Double.TYPE));
        assertEquals("boolean -> boolean", true, ClassUtil.isAssignable(Boolean.TYPE, Boolean.TYPE));
    }
    
    public void testPrimitiveToWrapper() {
       
        // test primitive classes
        assertEquals("boolean -> Boolean.class", 
            Boolean.class, ClassUtil.primitiveToWrapper(Boolean.TYPE));   
        assertEquals("byte -> Byte.class",
            Byte.class, ClassUtil.primitiveToWrapper(Byte.TYPE));
        assertEquals("char -> Character.class",
            Character.class, ClassUtil.primitiveToWrapper(Character.TYPE));
        assertEquals("short -> Short.class",
            Short.class, ClassUtil.primitiveToWrapper(Short.TYPE));
        assertEquals("int -> Integer.class",
            Integer.class, ClassUtil.primitiveToWrapper(Integer.TYPE));
        assertEquals("long -> Long.class",
            Long.class, ClassUtil.primitiveToWrapper(Long.TYPE));
        assertEquals("double -> Double.class",
            Double.class, ClassUtil.primitiveToWrapper(Double.TYPE));
        assertEquals("float -> Float.class",
            Float.class, ClassUtil.primitiveToWrapper(Float.TYPE));
        
        // test a few other classes
        assertEquals("String.class -> String.class",
            String.class, ClassUtil.primitiveToWrapper(String.class));
        assertEquals("ClassFinder.class -> ClassFinder.class",
            ClassUtil.class, 
            ClassUtil.primitiveToWrapper(ClassUtil.class));
        assertEquals("Void.TYPE -> Void.TYPE",
            Void.TYPE, ClassUtil.primitiveToWrapper(Void.TYPE));
            
        // test null     
        assertNull("null -> null",
            ClassUtil.primitiveToWrapper(null));
    }

    public void testPrimitivesToWrappers() {
        // test null
        assertNull("null -> null",
            ClassUtil.primitivesToWrappers(null));
        // test empty array
        assertEquals("empty -> empty",
                ArrayUtil.EMPTY_CLASS_ARRAY, ClassUtil.primitivesToWrappers(ArrayUtil.EMPTY_CLASS_ARRAY));

        // test an array of various classes
        final Class[] primitives = new Class[] {
                Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, 
                Integer.TYPE, Long.TYPE, Double.TYPE, Float.TYPE,
                String.class, ClassUtil.class
        };
        Class[] wrappers= ClassUtil.primitivesToWrappers(primitives);
        
        for (int i=0; i < primitives.length; i++) {
            // test each returned wrapper
            Class primitive = primitives[i];
            Class expectedWrapper = ClassUtil.primitiveToWrapper(primitive);
            
            assertEquals(primitive + " -> " + expectedWrapper, expectedWrapper, wrappers[i]);
        }

        // test an array of no primitive classes
        final Class[] noPrimitives = new Class[] {
                String.class, ClassUtil.class, Void.TYPE
        };
        // This used to return the exact same array, but no longer does.
        assertNotSame("unmodified", noPrimitives, ClassUtil.primitivesToWrappers(noPrimitives));
    }

    public void testWrapperToPrimitive() {
        // an array with classes to convert
        final Class[] primitives = {
                Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE,
                Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
        };
        for (int i = 0; i < primitives.length; i++) {
            Class wrapperCls = ClassUtil.primitiveToWrapper(primitives[i]);
            assertFalse("Still primitive", wrapperCls.isPrimitive());
            assertEquals(wrapperCls + " -> " + primitives[i], primitives[i],
                    ClassUtil.wrapperToPrimitive(wrapperCls));
        }
    }

    public void testWrapperToPrimitiveNoWrapper() {
        assertNull("Wrong result for non wrapper class", ClassUtil.wrapperToPrimitive(String.class));
    }

    public void testWrapperToPrimitiveNull() {
        assertNull("Wrong result for null class", ClassUtil.wrapperToPrimitive(null));
    }

    public void testWrappersToPrimitives() {
        // an array with classes to test
        final Class[] classes = {
                Boolean.class, Byte.class, Character.class, Short.class,
                Integer.class, Long.class, Float.class, Double.class,
                String.class, ClassUtil.class, null
        };

        Class[] primitives = ClassUtil.wrappersToPrimitives(classes);
        // now test the result
        assertEquals("Wrong length of result array", classes.length, primitives.length);
        for (int i = 0; i < classes.length; i++) {
            Class expectedPrimitive = ClassUtil.wrapperToPrimitive(classes[i]);
            assertEquals(classes[i] + " -> " + expectedPrimitive, expectedPrimitive,
                    primitives[i]);
        }
    }

    public void testWrappersToPrimitivesNull() {
        assertNull("Wrong result for null input", ClassUtil.wrappersToPrimitives(null));
    }

    public void testWrappersToPrimitivesEmpty() {
        Class[] empty = new Class[0];
        assertEquals("Wrong result for empty input", empty, ClassUtil.wrappersToPrimitives(empty));
    }

    public void testGetClassClassNotFound() throws Exception {
        assertGetClassThrowsClassNotFound( "bool" );
        assertGetClassThrowsClassNotFound( "bool[]" );
        assertGetClassThrowsClassNotFound( "integer[]" );
    }

    public void testGetClassInvalidArguments() throws Exception {
        assertGetClassThrowsIllegalArgument( null );
        assertGetClassThrowsClassNotFound( "[][][]" );
        assertGetClassThrowsClassNotFound( "[[]" );
        assertGetClassThrowsClassNotFound( "[" );
        assertGetClassThrowsClassNotFound( "java.lang.String][" );
        assertGetClassThrowsClassNotFound( ".hello.world" );
        assertGetClassThrowsClassNotFound( "hello..world" );
    }

    public void testWithInterleavingWhitespace() throws ClassNotFoundException {
        assertEquals( int[].class, ClassUtil.getClass( " int [ ] " ) );
        assertEquals( long[].class, ClassUtil.getClass( "\rlong\t[\n]\r" ) );
        assertEquals( short[].class, ClassUtil.getClass( "\tshort                \t\t[]" ) );
        assertEquals( byte[].class, ClassUtil.getClass( "byte[\t\t\n\r]   " ) );
    }

    public void testGetClassByNormalNameArrays() throws ClassNotFoundException {
        assertEquals( int[].class, ClassUtil.getClass( "int[]" ) );
        assertEquals( long[].class, ClassUtil.getClass( "long[]" ) );
        assertEquals( short[].class, ClassUtil.getClass( "short[]" ) );
        assertEquals( byte[].class, ClassUtil.getClass( "byte[]" ) );
        assertEquals( char[].class, ClassUtil.getClass( "char[]" ) );
        assertEquals( float[].class, ClassUtil.getClass( "float[]" ) );
        assertEquals( double[].class, ClassUtil.getClass( "double[]" ) );
        assertEquals( boolean[].class, ClassUtil.getClass( "boolean[]" ) );
        assertEquals( String[].class, ClassUtil.getClass( "java.lang.String[]" ) );
    }

    public void testGetClassByNormalNameArrays2D() throws ClassNotFoundException {
        assertEquals( int[][].class, ClassUtil.getClass( "int[][]" ) );
        assertEquals( long[][].class, ClassUtil.getClass( "long[][]" ) );
        assertEquals( short[][].class, ClassUtil.getClass( "short[][]" ) );
        assertEquals( byte[][].class, ClassUtil.getClass( "byte[][]" ) );
        assertEquals( char[][].class, ClassUtil.getClass( "char[][]" ) );
        assertEquals( float[][].class, ClassUtil.getClass( "float[][]" ) );
        assertEquals( double[][].class, ClassUtil.getClass( "double[][]" ) );
        assertEquals( boolean[][].class, ClassUtil.getClass( "boolean[][]" ) );
        assertEquals( String[][].class, ClassUtil.getClass( "java.lang.String[][]" ) );
    }

    public void testGetClassWithArrayClasses2D() throws Exception {
        assertGetClassReturnsClass( String[][].class );
        assertGetClassReturnsClass( int[][].class );
        assertGetClassReturnsClass( long[][].class );
        assertGetClassReturnsClass( short[][].class );
        assertGetClassReturnsClass( byte[][].class );
        assertGetClassReturnsClass( char[][].class );
        assertGetClassReturnsClass( float[][].class );
        assertGetClassReturnsClass( double[][].class );
        assertGetClassReturnsClass( boolean[][].class );
    }

    public void testGetClassWithArrayClasses() throws Exception {
        assertGetClassReturnsClass( String[].class );
        assertGetClassReturnsClass( int[].class );
        assertGetClassReturnsClass( long[].class );
        assertGetClassReturnsClass( short[].class );
        assertGetClassReturnsClass( byte[].class );
        assertGetClassReturnsClass( char[].class );
        assertGetClassReturnsClass( float[].class );
        assertGetClassReturnsClass( double[].class );
        assertGetClassReturnsClass( boolean[].class );
    }

    public void testGetClassRawPrimitives() throws ClassNotFoundException {
        assertEquals( int.class, ClassUtil.getClass( "int" ) );
        assertEquals( long.class, ClassUtil.getClass( "long" ) );
        assertEquals( short.class, ClassUtil.getClass( "short" ) );
        assertEquals( byte.class, ClassUtil.getClass( "byte" ) );
        assertEquals( char.class, ClassUtil.getClass( "char" ) );
        assertEquals( float.class, ClassUtil.getClass( "float" ) );
        assertEquals( double.class, ClassUtil.getClass( "double" ) );
        assertEquals( boolean.class, ClassUtil.getClass( "boolean" ) );
    }

    private void assertGetClassReturnsClass( Class c ) throws Exception {
        assertEquals( c, ClassUtil.getClass( c.getName() ) );
    }

    private void assertGetClassThrowsException( String className, Class exceptionType ) throws Exception {
        try {
            ClassUtil.getClass( className );
            fail( "ClassFinder.getClass() should fail with an exception of type " + exceptionType.getName() + " when given class name \"" + className + "\"." );
        }
        catch( Exception e ) {
            assertTrue( exceptionType.isAssignableFrom( e.getClass() ) );
        }
    }

    private void assertGetClassThrowsIllegalArgument( String className ) throws Exception {
        assertGetClassThrowsException( className, IllegalArgumentException.class );
    }

    private void assertGetClassThrowsClassNotFound( String className ) throws Exception {
        assertGetClassThrowsException( className, ClassNotFoundException.class );
    }

    // Show the Java bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
    // We may have to delete this if a JDK fixes the bug.
    public void testShowJavaBug() throws Exception {
        // Tests with Collections$UnmodifiableSet
        Set set = Collections.unmodifiableSet(new HashSet());
        Method isEmptyMethod = set.getClass().getMethod("isEmpty",  new Class[0]);
        try {
            isEmptyMethod.invoke(set, new Object[0]);
            fail("Failed to throw IllegalAccessException as expected");
        } catch(IllegalAccessException iae) {
            // expected
        }
    }

    public void testGetPublicMethod() throws Exception {
        // Tests with Collections$UnmodifiableSet
        Set set = Collections.unmodifiableSet(new HashSet());
        Method isEmptyMethod = ClassUtil.getPublicMethod(set.getClass(), "isEmpty",  new Class[0]);
            assertTrue(Modifier.isPublic(isEmptyMethod.getDeclaringClass().getModifiers()));
 
        try {
            isEmptyMethod.invoke(set, new Object[0]);
        } catch(java.lang.IllegalAccessException iae) {
            fail("Should not have thrown IllegalAccessException");
        }
               
        // Tests with a public Class
        Method toStringMethod = ClassUtil.getPublicMethod(Object.class, "toString",  new Class[0]);
            assertEquals(Object.class.getMethod("toString", new Class[0]), toStringMethod);
    }
 
    public void testToClass_object() {
        assertEquals(null, ClassUtil.toClass(null));

        assertSame(
            ArrayUtil.EMPTY_CLASS_ARRAY,
            ClassUtil.toClass(new Class[0]));

        Object[] array = new Object[3];
        array[0] = new String("Test");
        array[1] = new Integer(1);
        array[2] = new Double(99);

        Class[] results = ClassUtil.toClass(array);
        assertEquals("String", ClassUtil.getShortClassName(results[0]));
        assertEquals("Integer", ClassUtil.getShortClassName(results[1]));
        assertEquals("Double", ClassUtil.getShortClassName(results[2]));
    }

    public void test_getShortCanonicalName_Object() {
        assertEquals("<null>", ClassUtil.getShortCanonicalName(null, "<null>"));
        assertEquals("ClassUtil[]", ClassUtil.getShortCanonicalName(new ClassUtil[0], "<null>"));
        assertEquals("ClassUtil[][]", ClassUtil.getShortCanonicalName(new ClassUtil[0][0], "<null>"));
        assertEquals("int[]", ClassUtil.getShortCanonicalName(new int[0], "<null>"));
        assertEquals("int[][]", ClassUtil.getShortCanonicalName(new int[0][0], "<null>"));
    }

    public void test_getShortCanonicalName_Class() {
        assertEquals("ClassUtil", ClassUtil.getShortCanonicalName(ClassUtil.class));
        assertEquals("ClassUtil[]", ClassUtil.getShortCanonicalName(ClassUtil[].class));
        assertEquals("ClassUtil[][]", ClassUtil.getShortCanonicalName(ClassUtil[][].class));
        assertEquals("int[]", ClassUtil.getShortCanonicalName(int[].class));
        assertEquals("int[][]", ClassUtil.getShortCanonicalName(int[][].class));
    }

    public void test_getShortCanonicalName_String() {
        assertEquals("ClassUtil", ClassUtil.getShortCanonicalName("com.jxva.util.ClassUtil"));
        assertEquals("ClassUtil[]", ClassUtil.getShortCanonicalName("[Lcom.jxva.util.ClassUtil;"));
        assertEquals("ClassUtil[][]", ClassUtil.getShortCanonicalName("[[Lcom.jxva.util.ClassUtil;"));
        assertEquals("ClassUtil[]", ClassUtil.getShortCanonicalName("com.jxva.util.ClassUtil[]"));
        assertEquals("ClassUtil[][]", ClassUtil.getShortCanonicalName("com.jxva.util.ClassUtil[][]"));
        assertEquals("int[]", ClassUtil.getShortCanonicalName("[I"));
        assertEquals("int[][]", ClassUtil.getShortCanonicalName("[[I"));
        assertEquals("int[]", ClassUtil.getShortCanonicalName("int[]"));
        assertEquals("int[][]", ClassUtil.getShortCanonicalName("int[][]"));
    }

    public void test_getPackageCanonicalName_Object() {
        assertEquals("<null>", ClassUtil.getPackageCanonicalName(null, "<null>"));
        assertEquals("com.jxva.util", ClassUtil.getPackageCanonicalName(new ClassUtil[0], "<null>"));
        assertEquals("com.jxva.util", ClassUtil.getPackageCanonicalName(new ClassUtil[0][0], "<null>"));
        assertEquals("", ClassUtil.getPackageCanonicalName(new int[0], "<null>"));
        assertEquals("", ClassUtil.getPackageCanonicalName(new int[0][0], "<null>"));
    }

    public void test_getPackageCanonicalName_Class() {
        assertEquals("com.jxva.util", ClassUtil.getPackageCanonicalName(ClassUtil.class));
        assertEquals("com.jxva.util", ClassUtil.getPackageCanonicalName(ClassUtil[].class));
        assertEquals("com.jxva.util", ClassUtil.getPackageCanonicalName(ClassUtil[][].class));
        assertEquals("", ClassUtil.getPackageCanonicalName(int[].class));
        assertEquals("", ClassUtil.getPackageCanonicalName(int[][].class));
    }

    public void test_getPackageCanonicalName_String() {
        assertEquals("com.jxva.util", 
            ClassUtil.getPackageCanonicalName("com.jxva.util.ClassUtil"));
        assertEquals("com.jxva.util", 
            ClassUtil.getPackageCanonicalName("[Lcom.jxva.util.ClassUtil;"));
        assertEquals("com.jxva.util", 
            ClassUtil.getPackageCanonicalName("[[Lcom.jxva.util.ClassUtil;"));
        assertEquals("com.jxva.util", 
            ClassUtil.getPackageCanonicalName("com.jxva.util.ClassUtil[]"));
        assertEquals("com.jxva.util", 
            ClassUtil.getPackageCanonicalName("com.jxva.util.ClassUtil[][]"));
        assertEquals("", ClassUtil.getPackageCanonicalName("[I"));
        assertEquals("", ClassUtil.getPackageCanonicalName("[[I"));
        assertEquals("", ClassUtil.getPackageCanonicalName("int[]"));
        assertEquals("", ClassUtil.getPackageCanonicalName("int[][]"));
    }

}
