package sql4j.util;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author: Jianguo Lu
 */
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** The parser error message processing.
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author Jianguo Lu
 */
public class Errors implements ErrorHandler {
  public  void error(SAXParseException ex) {
	System.err.println("XML parser error: "+ex
		       +"\n at line " + ex.getLineNumber()
		       +" cloumn "+ ex.getColumnNumber()
		       +" "+ ex.getPublicId()
		       );
  }            
  public  void fatalError(SAXParseException ex) throws SAXException {
	System.err.println("XML parser fatal error: "+ex
		       +"\n at line " + ex.getLineNumber()
		       +" cloumn "+ ex.getColumnNumber()
		       +" "+ ex.getPublicId()
		       );
  }            
  public  void warning(SAXParseException ex) {
	System.err.println("XML parser warning: "+ex
		       +"\n at line " + ex.getLineNumber()
		       +" cloumn "+ ex.getColumnNumber()
		       +" " + ex.getPublicId()
		       );
  }            
}