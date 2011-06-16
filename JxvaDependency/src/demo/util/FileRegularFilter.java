package demo.util;

import java.io.File;
import java.io.FilenameFilter;



/**
 * This is regular expression filename filter.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public class FileRegularFilter implements FilenameFilter {

	private RegularExpr	mRegularExpr	= null;

	/**
	 * Constructor.
	 * 
	 * @param pattern
	 *          regular expression
	 */
	public FileRegularFilter(String pattern) {
		if ((pattern == null) || pattern.equals("") || pattern.equals("*")) {
			mRegularExpr = null;
		}
		else {
			mRegularExpr = new RegularExpr(pattern);
		}
	}

	/**
	 * Tests if a specified file should be included in a file list.
	 * 
	 * @param dir -
	 *          the directory in which the file was found
	 * @param name -
	 *          the name of the file.
	 */
	public boolean accept(File dir, String name) {
		if (mRegularExpr == null) {
			return true;
		}
		return mRegularExpr.isMatch(name);
	}
}