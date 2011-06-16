import jms.JmsTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import license.LicenseTestSuite;
import log.LogTestSuite;
import mvc.MvcTestSuite;
import oxm.OxmTestSuite;
import rbac.RbacTestSuite;
import sso.SsoTestSuite;
import tag.TagTestSuite;
import tool.ToolTestSuite;
import tree.TreeTestSuite;
import util.UtilTestSuite;
import xml.XmlTestSuite;
import cache.CacheTestSuite;
import dao.DaoTestSuite;
import entity.EntityTestSuite;
import exception.ExceptionTestSuite;
import filter.FilterTestSuite;
import graph.GraphTestSuite;
import http.HttpTestSuite;

//for junit4
//@RunWith(Suite.class)
//@SuiteClasses({          
//	 MvcTestSuite.class,
//	 UtilTestSuite.class
//})
public class TestAll extends TestCase{



//  for junit3.8	
	public static Test suite(){
		TestSuite suite= new TestSuite();
		//suite.addTest(CacheTestSuite.suite());
		//suite.addTest(DaoTestSuite.suite());
		//suite.addTest(EntityTestSuite.suite());
        suite.addTest(ExceptionTestSuite.suite());
        //suite.addTest(FilterTestSuite.suite());
        //suite.addTest(GraphTestSuite.suite());
       // suite.addTest(HttpTestSuite.suite());
        //suite.addTest(JmsTestSuite.suite());
		suite.addTest(LicenseTestSuite.suite());
		//suite.addTest(LogTestSuite.suite());
		suite.addTest(MvcTestSuite.suite());
		//suite.addTest(OxmTestSuite.suite());
		//suite.addTest(RbacTestSuite.suite());
		//suite.addTest(SsoTestSuite.suite());
		//suite.addTest(TagTestSuite.suite());
		//suite.addTest(ToolTestSuite.suite());
		//suite.addTest(TreeTestSuite.suite());
		suite.addTest(UtilTestSuite.suite());
		suite.addTest(XmlTestSuite.suite());
		return suite;
	}
	
}
