package xml;

import junit.framework.TestCase;

import com.jxva.xml.Document;
import com.jxva.xml.Element;
import com.jxva.xml.XmlParser;
import com.jxva.xml.XmlParserFactory;

import exception.TestException;


public class DomTest extends TestCase{
	
	/**
	 * 
	 * @param fileName
	 * @return Element root
	 * @throws Exception
	 */
	public Element getRoot(String fileName)throws TestException{
		try{
			Document doc = new Document();
			XmlParserFactory factory = XmlParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlParser xpp = factory.newPullParser();
			//xpp.setInput(new FileInputStream("D:/eclipse/workspace/JxvaFramework/dev/test/xml/"+fileName),"UTF-8");
			//xpp.setInput(TestAll.class.getResourceAsStream("/test/xml/"+fileName),"UTF-8");
			xpp.setInput(DomTest.class.getResourceAsStream(fileName),"UTF-8");
			doc.parse(xpp);
			return doc.getRootElement();
		}catch(Exception e){
			throw new TestException(e);
		}
	}
	
	
	public void testSys(){
		Element root=getRoot("sys.xml");
		assertEquals(root.getElement("sysconfig").getName(),"sysconfig");
		assertEquals(root.getElement("sysconfig").getAttributeValue("encoding"),"UTF-8");
		assertEquals(root.getElement("sysconfig").getElement("error-page").getText(),"/pub/error.htm");
		
		Element mvc=root.getElement("mvc");
		assertEquals(mvc.getChildCount(),9);
	}
	
	public void testMvc(){
		Element root=getRoot("mvc.xml");
		assertEquals(root.getChildCount(),5);
	}
}
