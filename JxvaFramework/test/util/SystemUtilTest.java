package util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.jxva.util.SystemUtil;

public class SystemUtilTest extends TestCase {
    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SystemUtilTest.class);
        suite.setName("SystemUtils Tests");
        return suite;
    }

    //-----------------------------------------------------------------------
    // COPIED FROM SystemUtils
    //-----------------------------------------------------------------------
    private String JAVA_VERSION;

    private String JAVA_VERSION_TRIMMED;

    private String OS_NAME;

    private String OS_VERSION;

    public SystemUtilTest(String name) {
        super(name);
    }

    /**
     * <p>Gets the Java version number as a <code>float</code>.</p>
     *
     * <p>Example return values:</p>
     * <ul>
     *  <li><code>1.2f</code> for JDK 1.2
     *  <li><code>1.31f</code> for JDK 1.3.1
     * </ul>
     * 
     * <p>Patch releases are not reported.
     * Zero is returned if {@link #JAVA_VERSION_TRIMMED} is <code>null</code>.</p>
     * 
     * @return the version, for example 1.31f for JDK 1.3.1
     */
    private float getJavaVersionAsFloat() {
        if (JAVA_VERSION_TRIMMED == null) {
            return 0f;
        }
        String str = JAVA_VERSION_TRIMMED.substring(0, 3);
        if (JAVA_VERSION_TRIMMED.length() >= 5) {
            str = str + JAVA_VERSION_TRIMMED.substring(4, 5);
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * <p>Gets the Java version number as an <code>int</code>.</p>
     *
     * <p>Example return values:</p>
     * <ul>
     *  <li><code>120</code> for JDK 1.2
     *  <li><code>131</code> for JDK 1.3.1
     * </ul>
     * 
     * <p>Patch releases are not reported.
     * Zero is returned if {@link #JAVA_VERSION_TRIMMED} is <code>null</code>.</p>
     * 
     * @return the version, for example 131 for JDK 1.3.1
     */
    private int getJavaVersionAsInt() {
        if (JAVA_VERSION == null) {
            return 0;
        }
        String str = JAVA_VERSION_TRIMMED.substring(0, 1);
        str = str + JAVA_VERSION_TRIMMED.substring(2, 3);
        if (JAVA_VERSION_TRIMMED.length() >= 5) {
            str = str + JAVA_VERSION_TRIMMED.substring(4, 5);
        } else {
            str = str + "0";
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Trims the text of the java version to start with numbers.
     * 
     * @return the trimmed java version
     */
    private String getJavaVersionTrimmed() {
        if (JAVA_VERSION != null) {
            for (int i = 0; i < JAVA_VERSION.length(); i++) {
                char ch = JAVA_VERSION.charAt(i);
                if (ch >= '0' && ch <= '9') {
                    return JAVA_VERSION.substring(i);
                }
            }
        }
        return null;
    }

    /**
     * Decides if the java version matches.
     * 
     * @param versionPrefix
     *                  the prefix for the java version
     * @return true if matches, or false if not or can't determine
     */
    private boolean getJavaVersionMatches(String versionPrefix) {
        if (JAVA_VERSION_TRIMMED == null) {
            return false;
        }
        return JAVA_VERSION_TRIMMED.startsWith(versionPrefix);
    }

    /**
     * Decides if the operating system matches.
     * 
     * @param osNamePrefix
     *                  the prefix for the os name
     * @return true if matches, or false if not or can't determine
     */
    private boolean getOSMatches(String osNamePrefix) {
        if (OS_NAME == null) {
            return false;
        }
        return OS_NAME.startsWith(osNamePrefix);
    }

    /**
     * Decides if the operating system matches.
     * 
     * @param osNamePrefix
     *                  the prefix for the os name
     * @param osVersionPrefix
     *                  the prefix for the version
     * @return true if matches, or false if not or can't determine
     */
    private boolean getOSMatches(String osNamePrefix, String osVersionPrefix) {
        if (OS_NAME == null || OS_VERSION == null) {
            return false;
        }
        return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //-----------------------------------------------------------------------
    public void testConstructor() {
        Constructor[] cons = SystemUtil.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertEquals(true, Modifier.isPublic(cons[0].getModifiers()));
        assertEquals(true, Modifier.isPublic(SystemUtil.class.getModifiers()));
        assertEquals(false, Modifier.isFinal(SystemUtil.class.getModifiers()));
    }

    /**
     * Assums no security manager exists.
     */
    public void testGetJavaHome() {
        File dir = SystemUtil.getJavaHome();
        Assert.assertNotNull(dir);
        Assert.assertTrue(dir.exists());
    }

    /**
     * Assums no security manager exists.
     */
    public void testGetJavaIoTmpDir() {
        File dir = SystemUtil.getJavaIoTmpDir();
        Assert.assertNotNull(dir);
        Assert.assertTrue(dir.exists());
    }

    /**
     * Assums no security manager exists.
     */
    public void testGetUserDir() {
        File dir = SystemUtil.getUserDir();
        Assert.assertNotNull(dir);
        Assert.assertTrue(dir.exists());
    }

    /**
     * Assums no security manager exists.
     */
    public void testGetUserHome() {
        File dir = SystemUtil.getUserHome();
        Assert.assertNotNull(dir);
        Assert.assertTrue(dir.exists());
    }

    public void testIS_JAVA() {
        String javaVersion = System.getProperty("java.version");
        if (javaVersion == null) {
            assertEquals(false, SystemUtil.IS_JAVA_1_1);
            assertEquals(false, SystemUtil.IS_JAVA_1_2);
            assertEquals(false, SystemUtil.IS_JAVA_1_3);
            assertEquals(false, SystemUtil.IS_JAVA_1_4);
            assertEquals(false, SystemUtil.IS_JAVA_1_5);
            assertEquals(false, SystemUtil.IS_JAVA_1_6);
        } else if (javaVersion.startsWith("1.1")) {
            assertEquals(true, SystemUtil.IS_JAVA_1_1);
            assertEquals(false, SystemUtil.IS_JAVA_1_2);
            assertEquals(false, SystemUtil.IS_JAVA_1_3);
            assertEquals(false, SystemUtil.IS_JAVA_1_4);
            assertEquals(false, SystemUtil.IS_JAVA_1_5);
            assertEquals(false, SystemUtil.IS_JAVA_1_6);
        } else if (javaVersion.startsWith("1.2")) {
            assertEquals(false, SystemUtil.IS_JAVA_1_1);
            assertEquals(true, SystemUtil.IS_JAVA_1_2);
            assertEquals(false, SystemUtil.IS_JAVA_1_3);
            assertEquals(false, SystemUtil.IS_JAVA_1_4);
            assertEquals(false, SystemUtil.IS_JAVA_1_5);
            assertEquals(false, SystemUtil.IS_JAVA_1_6);
        } else if (javaVersion.startsWith("1.3")) {
            assertEquals(false, SystemUtil.IS_JAVA_1_1);
            assertEquals(false, SystemUtil.IS_JAVA_1_2);
            assertEquals(true, SystemUtil.IS_JAVA_1_3);
            assertEquals(false, SystemUtil.IS_JAVA_1_4);
            assertEquals(false, SystemUtil.IS_JAVA_1_5);
            assertEquals(false, SystemUtil.IS_JAVA_1_6);
        } else if (javaVersion.startsWith("1.4")) {
            assertEquals(false, SystemUtil.IS_JAVA_1_1);
            assertEquals(false, SystemUtil.IS_JAVA_1_2);
            assertEquals(false, SystemUtil.IS_JAVA_1_3);
            assertEquals(true, SystemUtil.IS_JAVA_1_4);
            assertEquals(false, SystemUtil.IS_JAVA_1_5);
            assertEquals(false, SystemUtil.IS_JAVA_1_6);
        } else if (javaVersion.startsWith("1.5")) {
            assertEquals(false, SystemUtil.IS_JAVA_1_1);
            assertEquals(false, SystemUtil.IS_JAVA_1_2);
            assertEquals(false, SystemUtil.IS_JAVA_1_3);
            assertEquals(false, SystemUtil.IS_JAVA_1_4);
            assertEquals(true, SystemUtil.IS_JAVA_1_5);
            assertEquals(false, SystemUtil.IS_JAVA_1_6);
        } else if (javaVersion.startsWith("1.6")) {
            assertEquals(false, SystemUtil.IS_JAVA_1_1);
            assertEquals(false, SystemUtil.IS_JAVA_1_2);
            assertEquals(false, SystemUtil.IS_JAVA_1_3);
            assertEquals(false, SystemUtil.IS_JAVA_1_4);
            assertEquals(false, SystemUtil.IS_JAVA_1_5);
            assertEquals(true, SystemUtil.IS_JAVA_1_6);
        } else {
            System.out.println("Can't test IS_JAVA value");
        }
    }

    public void testIS_OS() {
        String osName = System.getProperty("os.name");
        if (osName == null) {
            assertEquals(false, SystemUtil.IS_OS_WINDOWS);
            assertEquals(false, SystemUtil.IS_OS_UNIX);
            assertEquals(false, SystemUtil.IS_OS_SOLARIS);
            assertEquals(false, SystemUtil.IS_OS_LINUX);
            assertEquals(false, SystemUtil.IS_OS_MAC_OSX);
        } else if (osName.startsWith("Windows")) {
            assertEquals(false, SystemUtil.IS_OS_UNIX);
            assertEquals(true, SystemUtil.IS_OS_WINDOWS);
        } else if (osName.startsWith("Solaris")) {
            assertEquals(true, SystemUtil.IS_OS_SOLARIS);
            assertEquals(true, SystemUtil.IS_OS_UNIX);
            assertEquals(false, SystemUtil.IS_OS_WINDOWS);
        } else if (osName.toLowerCase().startsWith("linux")) {
            assertEquals(true, SystemUtil.IS_OS_LINUX);
            assertEquals(true, SystemUtil.IS_OS_UNIX);
            assertEquals(false, SystemUtil.IS_OS_WINDOWS);
        } else if (osName.startsWith("Mac OS X")) {
            assertEquals(true, SystemUtil.IS_OS_MAC_OSX);
            assertEquals(true, SystemUtil.IS_OS_UNIX);
            assertEquals(false, SystemUtil.IS_OS_WINDOWS);
        } else if (osName.startsWith("OS/2")) {
            assertEquals(true, SystemUtil.IS_OS_OS2);
            assertEquals(false, SystemUtil.IS_OS_UNIX);
            assertEquals(false, SystemUtil.IS_OS_WINDOWS);
        } else if (osName.startsWith("SunOS")) {
            assertEquals(true, SystemUtil.IS_OS_SUN_OS);
            assertEquals(true, SystemUtil.IS_OS_UNIX);
            assertEquals(false, SystemUtil.IS_OS_WINDOWS);
        } else {
            System.out.println("Can't test IS_OS value");
        }
    }

    public void testJavaVersionAsFloat() {
        JAVA_VERSION = null;
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(0f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.1f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.2";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.2f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.3.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.3f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.3.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.31f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.4.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.4f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.4.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.41f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.5.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.5f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "1.6.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.6f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "JavaVM-1.3.1";  //HP-UX
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(1.31f, getJavaVersionAsFloat(), 0.000001f);
        JAVA_VERSION = "XXX-1.3.x";  //error
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(0.0f, getJavaVersionAsFloat(), 0.000001f);
    }

    public void testJavaVersionAsInt() {
        JAVA_VERSION = null;
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(0, getJavaVersionAsInt());
        JAVA_VERSION = "1.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(110, getJavaVersionAsInt());
        JAVA_VERSION = "1.2";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(120, getJavaVersionAsInt());
        JAVA_VERSION = "1.3.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(130, getJavaVersionAsInt());
        JAVA_VERSION = "1.3.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(131, getJavaVersionAsInt());
        JAVA_VERSION = "1.4.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(140, getJavaVersionAsInt());
        JAVA_VERSION = "1.4.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(141, getJavaVersionAsInt());
        JAVA_VERSION = "1.5.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(150, getJavaVersionAsInt());
        JAVA_VERSION = "1.6.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(160, getJavaVersionAsInt());
        JAVA_VERSION = "JavaVM-1.3.1";  //HP-UX
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(131, getJavaVersionAsInt());
        JAVA_VERSION = "XXX-1.3.x";  //error
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(0, getJavaVersionAsInt());
    }

    public void testJavaVersionAtLeastFloat() {
        float version = SystemUtil.JAVA_VERSION_FLOAT;
        assertEquals(true, SystemUtil.isJavaVersionAtLeast(version));
        version -= 0.1f;
        assertEquals(true, SystemUtil.isJavaVersionAtLeast(version));
        version += 0.2f;
        assertEquals(false, SystemUtil.isJavaVersionAtLeast(version));
    }

    public void testJavaVersionAtLeastInt() {
        int version = SystemUtil.JAVA_VERSION_INT;
        assertEquals(true, SystemUtil.isJavaVersionAtLeast(version));
        version -= 10;
        assertEquals(true, SystemUtil.isJavaVersionAtLeast(version));
        version += 20;
        assertEquals(false, SystemUtil.isJavaVersionAtLeast(version));
    }

    //-----------------------------------------------------------------------
    public void testJavaVersionMatches() {
        JAVA_VERSION = null;
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(true, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.2";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(true, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.3.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(true, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.3.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(true, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.4.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(true, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.4.1";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(true, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.5.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(true, getJavaVersionMatches("1.5"));
        JAVA_VERSION = "1.6.0";
        JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
        assertEquals(false, getJavaVersionMatches("1.1"));
        assertEquals(false, getJavaVersionMatches("1.2"));
        assertEquals(false, getJavaVersionMatches("1.3"));
        assertEquals(false, getJavaVersionMatches("1.4"));
        assertEquals(false, getJavaVersionMatches("1.5"));
    }

    public void testOSMatches() {
        OS_NAME = null;
        assertEquals(false, getOSMatches("Windows"));
        OS_NAME = "Windows 95";
        assertEquals(true, getOSMatches("Windows"));
        OS_NAME = "Windows NT";
        assertEquals(true, getOSMatches("Windows"));
        OS_NAME = "OS/2";
        assertEquals(false, getOSMatches("Windows"));
    }

    public void testOSMatches2() {
        OS_NAME = null;
        OS_VERSION = null;
        assertEquals(false, getOSMatches("Windows 9", "4.1"));
        OS_NAME = "Windows 95";
        OS_VERSION = "4.0";
        assertEquals(false, getOSMatches("Windows 9", "4.1"));
        OS_NAME = "Windows 95";
        OS_VERSION = "4.1";
        assertEquals(true, getOSMatches("Windows 9", "4.1"));
        OS_NAME = "Windows 98";
        OS_VERSION = "4.1";
        assertEquals(true, getOSMatches("Windows 9", "4.1"));
        OS_NAME = "Windows NT";
        OS_VERSION = "4.0";
        assertEquals(false, getOSMatches("Windows 9", "4.1"));
        OS_NAME = "OS/2";
        OS_VERSION = "4.0";
        assertEquals(false, getOSMatches("Windows 9", "4.1"));
    }

    public void testJavaAwtHeadless() {
        boolean atLeastJava14 = SystemUtil.isJavaVersionAtLeast(140);
        String expectedStringValue = System.getProperty("java.awt.headless");
        String expectedStringValueWithDefault = System.getProperty("java.awt.headless", "false");
        assertNotNull(expectedStringValueWithDefault);
        if (atLeastJava14) {
            boolean expectedValue = Boolean.valueOf(expectedStringValue).booleanValue();
            if (expectedStringValue != null) {
                assertEquals(expectedStringValue, SystemUtil.JAVA_AWT_HEADLESS);
            }
            assertEquals(expectedValue, SystemUtil.isJavaAwtHeadless());
        } else {
            assertNull(expectedStringValue);
            assertNull(SystemUtil.JAVA_AWT_HEADLESS);
            assertEquals(expectedStringValueWithDefault, "" + SystemUtil.isJavaAwtHeadless());
        }
        assertEquals(expectedStringValueWithDefault, "" + SystemUtil.isJavaAwtHeadless());
    }
}
