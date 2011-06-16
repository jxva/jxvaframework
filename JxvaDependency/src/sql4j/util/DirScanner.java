package sql4j.util;

/**
 * A generic directory scanner that search for all the files in a directory, given a pattern of a file name.
 * The file name pattern is the same to the regular expression. For example, ".*java" will search for all the files
 * that the file names end with "java". 
 * refer to gnu.regexp for detailed spesification for the syntax of the regular expression.
 * Creation date: (2/22/01 1:02:49 PM)
 * @author: Jianguo Lu
 */
import java.util.*;
import java.io.*;

import gnu.regexp.*;

/**
 * A generic directory scanner that search for all the files in a directory, given a pattern of a file name.
 * The file name pattern is the same to the regular expression. For example, ".*java" will search for all the files
 * that the file names end with "java". 
 * refer to gnu.regexp for detailed spesification for the syntax of the regular expression.
 * Creation date: (2/22/01 1:02:49 PM)
 * @author Jianguo Lu
 */
public class DirScanner {
	//File names found in this directory.
	protected Vector fileNames;
	//The directory to search for.
	protected File dir;
	//The collection of objects constructed from the files.
	protected Vector dataEntities;

/**
 * DirScanner constructor comment.
 */
public DirScanner() {
	super();
}
	public DirScanner(File d){
		dir=d;
	}
/**
	* Find all the files that have the pattern "fileNamePattern" in a directory
		* dir. Returns a vector of file(path) names. 
	* Creation date: (2/22/01 12:20:50 PM)
	* @return java.io.File[]
	* @param dir java.io.File
	*/
public Vector findFiles(String fileNamePattern) {
	Vector result = null;
	if (!dir.exists())
		return null;

	File[] files = dir.listFiles();

	for (int k = 0; k < files.length; k++) {
		if (files[k].isDirectory()) {
			DirScanner ds = new DirScanner(files[k]);
			Vector filesInSubDir = ds.findFiles(fileNamePattern);
			if (filesInSubDir != null) {
				if (result == null)
					result = new Vector();
				result.addAll(filesInSubDir);
			}
		} else {
			String name = files[k].getName();
			try {
				RE re = new RE(fileNamePattern);
				if (re.getMatch(name) != null) {
					if (result==null) result=new Vector();
					name=files[k].getPath();
					result.add(name);
					System.out.println(name);
				}
			} catch (REException e) {
				e.printStackTrace();
			}
		}

	}
	return result;
}
	public static void main(String[] args){
		DirScanner ds = new DirScanner(new File("D:/com"));
		ds.findFiles(".*FinderHelperBase.*");
		
	}

}