package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/30/00 9:46:23 PM)
 * @author:  Jianguo Lu
 */
import sql4j.schema.*;
/**
 * Atomic where  condition, that has no logic connectors like AND, OR.
 * Creation date: (10/30/00 9:46:23 PM)
 * @author Jianguo Lu
 */
public class ExistenceTest extends AtomicWhereCondition{
/**
 * ExistenceTest constructor comment.
 */
public ExistenceTest() {
	super();
}
	public ExistenceTest(SelectStatement s){
		stmt=s;
	}
	public String toString(){
		return "EXISTS "+stmt.toString();
	}

}