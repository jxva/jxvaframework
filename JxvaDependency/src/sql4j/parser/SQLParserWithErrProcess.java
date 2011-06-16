package sql4j.parser;


import java.io.*;
import java_cup.runtime.*;

import sql4j.schema.*;
 
public class SQLParserWithErrProcess extends SQLParser {
	public SQLParserWithErrProcess() {
		super();
	}
/**
 * SQLParserWithErroProcess constructor comment.
 * @param is java.io.InputStream
 */
public SQLParserWithErrProcess(java.io.InputStream is) {
	super(is);
}
/**
 * SQLParserWithErroProcess constructor comment.
 * @param s java_cup.runtime.Scanner
 */
public SQLParserWithErrProcess(java_cup.runtime.Scanner s) {
	super(s);
}
	protected int error_sync_size() {
		return 15;
	}
	public void report_error(String message, Object info) {
		String msg=message+ "   < " +((Symbol)info).value+" >";
		System.out.println(msg+ " trying to repare.");
	}
public void report_fatal_error(String message, Object info) throws java.lang.Exception {
	/* stop parsing (not really necessary since we throw an exception, but) */
	done_parsing();

	/* use the normal error message reporting to put out the message */
	report_error(message, info);

	/* throw an exception */
	//throw new Exception("Can't recover from previous error(s) " + ((Symbol) info).value);
}
public void syntax_error(Symbol cur_token) {
	String message = "SQL probably contains dynamic SQL or non-standard SQL. Relevant token is: ";
	report_error(message, cur_token);
}
}