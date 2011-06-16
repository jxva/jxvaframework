package exception;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.jxva.exception.ArgumentException;

public class NullArgumentExceptionTest extends TestCase {

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(NullArgumentExceptionTest.class);
    }

    public NullArgumentExceptionTest(String testName) {
        super(testName);
    }

    // testConstructor

    public void testConstructor_nullInput() {
        new ArgumentException(null);
    }

    // testGetMessage

    public void testGetMessage_nullConstructorInput() {
        final Throwable t = new ArgumentException(null);
        assertEquals("Argument must not be null.", t.getMessage());
    }

    public void testGetMessage_validConstructorInput() {
        final String argName = "name";
        final Throwable t = new ArgumentException(argName);
        assertEquals(argName + " must not be null.", t.getMessage());
    }

}

