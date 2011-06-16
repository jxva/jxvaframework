package demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;


/**
 * This class is used to load files into <code>OutputStream</code> after
 * parsing the template file. It supports variables, for block, if-then-else
 * block and iterator block. It has only one public method -
 * <code>loadFile(OutputStream, Map)</code>. It also supports vector
 * indexing. It stores the file data in a byte array. We may face some problems
 * later due to character encoding. But for the time being it works fine. <a
 * href="syntax.html">Template syntax.</a>
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public class Template {

	// whitespaces
	private static final String		mstWhitespaces	= " \t\r\n\f";

	// keywords
	private static final String[]	mstKeywords			=
																									{ "IF", "FOR", "ITR",
		"VARKEY", "NULL"															};

	// keyword index
	private static final int			IF							= 0;
	private static final int			FOR							= 1;
	private static final int			ITR							= 2;
	private static final int			VARKEY					= 3;
	private static final int			NULL						= 4;
	private static final int			VAR							= 5;

	private static final String		SIZE						= ".size";
	private static final String		LAST						= ".last";
	private static final String		THRU						= "THRU";
	private static final String		EQ							= "==";
	private static final String		NE							= "!=";
	private static final String		IN							= "IN";

	private File									mFile;
	private long									mModifiedTime		= 0;
	private byte[]								mbyContent;

	/**
	 * Constructor.
	 */
	public Template(File file) {
		mFile = file;
	}

	/**
	 * Read file (if modified) into a byte array. This function is
	 * <code>Synchronized</code> to make this class thread safe.
	 */
	private synchronized void readFile() throws IOException {

		// file check
		if (!mFile.exists())
			throw new IOException(toString() + " : does not exist.");
		if (!mFile.canRead())
			throw new IOException(toString() + " : no read permission.");
		if (!mFile.isFile())
			throw new IOException(toString() + " : not a file.");

		// file modified
		boolean bNeeded = false;
		long modTime = mFile.lastModified();
		if (modTime > mModifiedTime) {
			mModifiedTime = modTime;
			bNeeded = true;
		}

		if (bNeeded) {
			mbyContent = new byte[(int) mFile.length()];
			FileInputStream fis = new FileInputStream(mFile);
			fis.read(mbyContent);
			fis.close();
		}
	}

	/**
	 * Process block. First find the block type and then process.
	 */
	private int processBlock(OutputStream out, Map hash, Block block)
		throws IOException {

		// get keyword type or var
		int type = VAR;
		for (int i = 0; i < mstKeywords.length; i++) {
			if (block.equals(mstKeywords[i])) {
				type = i;
				break;
			}
		}

		return processBlock(out, hash, block, type);
	}

	/**
	 * Process block - call respective functions. Each of these functions returns
	 * the next index from where the next processing starts.
	 */
	private int processBlock(OutputStream out, Map hash, Block block, int type)
		throws IOException {

		int index;

		switch (type) {

		case VAR:
			index = processVarBlock(out, hash, block);
			break;

		case IF:
			index = processIfBlock(out, hash, block.nextIndex());
			break;

		case FOR:
			index = processForBlock(out, hash, block.nextIndex());
			break;

		case VARKEY:
			index = processVarkeyBlock(out, block);
			break;

		case NULL:
			index = processNullBlock(out, block);
			break;

		case ITR:
			index = processItrBlock(out, hash, block.nextIndex());
			break;

		default:
			throw new IOException("Unknown keyword index=" + type);
		}

		return index;
	}

	/**
	 * Process null block - returns nothing
	 */
	private int processNullBlock(OutputStream out, Block block) {
		return block.nextIndex();
	}

	/**
	 * Process varkey. It is used to write ${ in the <code>OutputStream</code>.
	 */
	private int processVarkeyBlock(OutputStream out, Block block)
		throws IOException {

		out.write('$');
		out.write('{');
		return block.nextIndex();
	}

	/**
	 * Process normal block. Normal block may contain other variables, keywords
	 * and other normal blocks. Search for "${". If found, get the block and
	 * process it.
	 */
	private int processNormalBlock(OutputStream out, Map hash, Block block)
		throws IOException {

		int ind = block.miStart;
		int finalIndex = ind + block.miLength;
		char c, c1;
		while (ind < finalIndex) {
			c = (char) mbyContent[ind++];

			if (c == '$') {
				c1 = (char) mbyContent[ind++];

				// not a variable
				if (c1 != '{') {
					out.write(c);
					out.write(c1);
					continue;
				}

				// variable and/or keyword
				Block nextBlock = getBlock(ind);
				ind = processBlock(out, hash, nextBlock);
			}
			else {
				out.write(c);
			}
		}

		return block.nextIndex();
	}

	/**
	 * Process variable block. Get variable object from the <code>Map</code> and
	 * write the string representation of the object.
	 */
	private int processVarBlock(OutputStream out, Map hash, Block block)
		throws IOException {

		// get variable value and send it
		String sb = block.toString();
		Object val = getVarObject(hash, sb);

		if (val != null)
			out.write(val.toString().getBytes());

		return block.nextIndex();
	}

	/**
	 * Get variable object. Returns null if not available in the Map
	 */
	private Object getVarObject(Map hash, String var) throws IOException {

		// get actual variable string
		String sb = getActualVarString(hash, var);

		// get vector size
		if (sb.endsWith(SIZE)) {
			return getVarSize(hash, var);
		}

		// get last element of a vector
		if (sb.endsWith(LAST)) {
			return getLastVar(hash, var);
		}

		// get vector element at a particular index
		int startIndex = sb.indexOf('[');
		int endIndex = sb.indexOf(']');
		if (startIndex != -1 && endIndex != -1) {
			if (endIndex < endIndex)
				throw new IOException("List indexing error");

			String indexBlock = sb.substring(startIndex + 1, endIndex);
			String vectVar = sb.substring(0, startIndex);
			Object obj = getVarObject(hash, vectVar);
			return getIndexedObject(obj, indexBlock);
		}

		// null variable
		if (sb.equals(mstKeywords[NULL])) {
			return null;
		}

		// return other values
		return hash.get(sb);
	}

	/**
	 * Get conditional variable object. If not a variable returns the string
	 * itself. Else returns the equivalent object from the hashtable. If it is not
	 * available in the hashtable returns null.
	 */
	private Object getCondVarObject(Map hash, String str) throws IOException {

		Object obj = null;

		// variable
		if (str.charAt(0) == '$' && str.charAt(1) == '{') {
			String var1 = str.substring(2, str.length() - 1);
			String var2 = getBlock(str, 2);

			if (var1.equals(var2))
				obj = getVarObject(hash, var1);
			else
				obj = getActualVarString(hash, str);
		}
		else
			obj = getActualVarString(hash, str);

		return obj;
	}

	/**
	 * Returns the size of a variable as an <code>Integer</code> object. In case
	 * of Scalar returns 1. If object is null, returns 0 and in case of
	 * <code>List</code> returns the list size.
	 */
	private Integer getVarSize(Map hash, String sb) throws IOException {

		// get object form hashtable
		String vectName = sb.substring(0, sb.length() - SIZE.length());
		Object obj = getVarObject(hash, vectName);

		if (obj == null)
			return new Integer(0);

		if (obj instanceof java.util.List)
			return new Integer(((List) obj).size());

		return new Integer(1);
	}

	/**
	 * Returns the last element of a vector. In case of scalar, returns the object
	 * itself. In case of null returns null and in case of vector returns null if
	 * size is zero or the last element.
	 */
	private Object getLastVar(Map hash, String sb) throws IOException {

		String vectName = sb.substring(0, sb.length() - LAST.length());
		Object obj = getVarObject(hash, vectName);

		if (obj == null)
			return null;

		if (obj instanceof java.util.List) {
			List v = (List) obj;
			if (v.size() == 0)
				return null;
			else
				return v.get(v.size() - 1);
		}

		return obj;
	}

	/**
	 * Get vector element at a particular index. Tokenize <code>indexBlock</code>
	 * string and returns the element.
	 */
	private Object getIndexedObject(Object obj, String indexBlock) {

		if (obj == null)
			return null;

		StringTokenizer st = new StringTokenizer(indexBlock, ",");
		while (st.hasMoreTokens()) {
			String initVar = st.nextToken().trim();
			int ind = Integer.parseInt(initVar);
			obj = elementAt(obj, ind);
		}

		st = null;
		return obj;
	}

	/**
	 * Returns an element at a particular element. If the object is null, returns
	 * null. If the object is a <code>List</code> returns null if the index is
	 * out of range or the element at that index.
	 */
	private Object elementAt(Object obj, int index) {

		if (obj == null)
			return null;

		if (obj instanceof java.util.List) {

			int size = ((List) obj).size();
			if (index >= size || index < 0)
				return null;

			return ((List) obj).get(index);
		}

		if (index == 0)
			return obj;

		return null;
	}

	/**
	 * Variable name can be formed dynamically. This function is used to get the
	 * actual variable name - by resolving inner variable name. Keywords will be
	 * treated as variables. The variable within '{' and '}' will be replaced by
	 * its string representation.
	 */
	private String getActualVarString(Map hash, String initVar)
		throws IOException {

		int ind = 0;
		char c, c1;
		int size = initVar.length();
		StringBuffer sb = new StringBuffer(64);

		while (ind < size) {
			c = initVar.charAt(ind);
			++ind;

			if (c == '$') {
				c1 = initVar.charAt(ind);
				ind++;

				// not a variable
				if (c1 != '{') {
					sb.append(c);
					sb.append(c1);
					continue;
				}

				// variable and/or keyword
				String var = getBlock(initVar, ind);
				ind += var.length() + 1;
				Object obj = getVarObject(hash, var);
				if (obj != null)
					sb.append(obj);
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * Process IF block. Returns the index of the next char.
	 */
	private int processIfBlock(OutputStream out, Map hash, int start)
		throws IOException {

		// condition block starts - evaluate condition
		int index = ignoreWhitespace(start);
		if (mbyContent[index] != '{')
			throw new IOException("IF condition block not found.");

		Block block = getBlock(index + 1);
		boolean bTrue = isTrueCondition(hash, block);

		// get IF action block
		index = block.nextIndex();
		index = ignoreWhitespace(index);
		if (mbyContent[index] != '{')
			throw new IOException("Invalid char after IF");
		block = getBlock(index + 1);
		if (bTrue)
			processNormalBlock(out, hash, block);

		// ignore ELSE block if available and condition is TRUE.
		index = block.nextIndex();
		char c = (char) mbyContent[index];
		if (bTrue) {
			if (c == '{') {
				block = getBlock(index + 1);
				return block.nextIndex();
			}
		}

		// else block starts
		if (!bTrue) {

			// check ELSE block
			if (c == '{') {
				block = getBlock(index + 1);
				processNormalBlock(out, hash, block);
				return block.nextIndex();
			}
		}

		return index;
	}

	/**
	 * Evaluate condition block in IF statement.
	 */
	private boolean isTrueCondition(Map hash, Block block) throws IOException {

		String sb = block.toString();
		StringTokenizer st = new StringTokenizer(sb, mstWhitespaces);

		// get left side
		if (!st.hasMoreTokens())
			throw new IOException("First token not found in IF");
		Object leftObj = getCondVarObject(hash, st.nextToken());

		// get condition operator
		if (!st.hasMoreTokens())
			throw new IOException("IF condition operator not found");
		String condition = st.nextToken();

		// get rightside
		if (!st.hasMoreTokens())
			throw new IOException("Last token not found in IF");
		Object rightObj = getCondVarObject(hash, st.nextToken());

		// now evaluate
		if (leftObj == null && rightObj == null)
			return condition.equals(EQ);
		if (leftObj == null || rightObj == null)
			return condition.equals(NE);
		if (condition.equals(EQ))
			return rightObj.toString().equals(leftObj.toString());
		if (condition.equals(NE))
			return !rightObj.toString().equals(leftObj.toString());
		if (condition.equals(IN))
			return ifObjExists(leftObj, rightObj);

		throw new IOException("Invalid IF condition operator: " + condition);
	}

	/**
	 * Check object existance. If any vector element is null, that element is
	 * ignored.
	 */
	private boolean ifObjExists(Object left, Object right) {

		// vector
		if (right instanceof java.util.List) {
			int sz = ((List) right).size();
			for (int i = 0; i < sz; i++) {
				Object obj = ((List) right).get(i);
				if (obj == null)
					continue;
				if (obj.toString().equals(left.toString()))
					return true;

			}
			return false;
		}

		// not a vector
		return right.toString().equals(left.toString());
	}

	/**
	 * Process iterator block. Here <code>index</code> is the starting index of
	 * the iterator initialization block.
	 */
	private int processItrBlock(OutputStream out, Map hash, int index)
		throws IOException {

		int ind = ignoreWhitespace(index);
		if (mbyContent[ind] != '{')
			throw new IOException("ITR initialization block not found.");

		// get ITR init block
		Block sb = getBlock(ind + 1);
		String str = sb.toString();
		StringTokenizer st = new StringTokenizer(str, mstWhitespaces);

		// get ITR main block
		ind = ignoreWhitespace(sb.nextIndex());
		if (mbyContent[ind] != '{')
			throw new IOException("ITR main block not found.");
		sb = getBlock(ind + 1);
		int retValue = sb.nextIndex();

		// get init variable name
		if (!st.hasMoreTokens())
			throw new IOException("Variable name not found in ITR block");
		String var = st.nextToken();

		// get start point
		int start = getIntegerValue(hash, st);
		if (start == -1)
			return retValue;

		// strip "THRU"
		if (!st.hasMoreTokens())
			throw new IOException("Invalid ITR block - string THRU not found");
		if (!st.nextToken().equals(THRU))
			throw new IOException("Expecting THRU in ITR block");

		// get length
		int len = getIntegerValue(hash, st);
		if (len == -1)
			return retValue;

		// process ITR main loop
		for (int i = 0; i < len; i++) {
			hash.put(var, new Integer(i + start));
			processNormalBlock(out, hash, sb);
		}

		return retValue;
	}

	/**
	 * Get the integer value from <code>StringTokenizer</code>. This function
	 * is used to get the start index and iteration count in ITR block. It returns
	 * -1 in case of error (<code>NumberFormatException</code>).
	 */
	private int getIntegerValue(Map hash, StringTokenizer st) throws IOException {

		// no tokens available
		if (!st.hasMoreTokens())
			throw new IOException("Start index not found in ITR block");

		String initVar = st.nextToken();

		// Get string representation of the number
		String initVal = getActualVarString(hash, initVar);
		try {
			return Integer.parseInt(initVal);
		}
		catch (NumberFormatException ex) {
			return -1;
		}
	}

	/**
	 * Process FOR block. Here <code>index</code> is the start index of the FOR
	 * initialization block.
	 */
	private int processForBlock(OutputStream out, Map hash, int index)
		throws IOException {

		// read initialization block
		int ind = ignoreWhitespace(index);
		char c = (char) mbyContent[ind];
		if (c != '{')
			throw new IOException("FOR initialization block not found.");

		Block sb = getBlock(ind + 1);
		StringBuffer varNameSb = new StringBuffer();
		List vec = getForList(hash, varNameSb, sb);
		String varName = varNameSb.toString();

		// read for main loop
		ind = ignoreWhitespace(sb.nextIndex());
		c = (char) mbyContent[ind];
		if (c != '{')
			throw new IOException("FOR main loop not found.");

		sb = getBlock(ind + 1);
		int sz = vec.size();
		for (int i = 0; i < sz; i++) {
			Object ob = vec.get(i);
			hash.put(varName, ob);
			processNormalBlock(out, hash, sb);
		}

		return sb.nextIndex();
	}

	/**
	 * Get for vector and init variable name. It passes the name of the FOR
	 * temporary variable name in <code>StringBuffer sb</code>
	 */
	private List getForList(Map hash, StringBuffer sb, Block block)
		throws IOException {

		String initBlock = block.toString();
		StringTokenizer st = new StringTokenizer(initBlock, mstWhitespaces);

		// get init variable
		if (!st.hasMoreTokens())
			throw new IOException("Invalid FOR block");
		String initVar = st.nextToken();
		sb.append(initVar);

		// ignore string "IN"
		if (!st.hasMoreTokens())
			throw new IOException("Invalid FOR block - string IN not found");
		initVar = st.nextToken();
		if (!initVar.equals(IN))
			throw new IOException("Expecting IN, found " + initVar);

		// get vector variable
		if (!st.hasMoreTokens())
			throw new IOException("Invalid FOR block - vector not found");
		initVar = st.nextToken();

		// get object form Map if variable
		Object obj = getCondVarObject(hash, initVar);

		if (obj instanceof java.util.List)
			return (List) obj;

		List vec = new Vector(1);
		if (obj != null)
			vec.add(obj);

		return vec;
	}

	/**
	 * Get block from index <code>start</code>. It starts just after the '{'
	 * character. It reads till it reaches the corresponding '}'.
	 */
	private Block getBlock(int start) {

		char c;
		int braceCount = 0;
		int index = start;

		while (braceCount != 1) {
			c = (char) mbyContent[index++];

			if (c == '{')
				--braceCount;
			else if (c == '}')
				++braceCount;
		}

		return new Block(mbyContent, start, index - start - 1);
	}

	/**
	 * Get block - this function is used to form the variable name dynamically.
	 */
	private String getBlock(String str, int start) {

		char c;
		int braceCount = 0;
		int index = start;

		while (braceCount != 1) {
			c = str.charAt(index++);

			if (c == '{')
				--braceCount;
			else if (c == '}')
				++braceCount;
		}

		return str.substring(start, index - 1);
	}

	/**
	 * Ignore whitespace. Returns the first non-whitespace char index.
	 */
	private int ignoreWhitespace(int start) {

		char c;
		while (true) {
			c = (char) mbyContent[start];

			if (mstWhitespaces.indexOf(c) != -1) {
				++start;
				continue;
			}
			else
				break;
		}
		return start;
	}

	/**
	 * Load file - it reads the file and process the block as normal block.
	 */
	public void loadFile(OutputStream out, Map hash) throws IOException {

		try {
			readFile();
			Block block = new Block(mbyContent);
			processNormalBlock(out, hash, block);
		}
		catch (ArrayIndexOutOfBoundsException ex) {
			throw new IOException("Unexpected end of file - bracket mismatch.");
		}
		catch (Throwable th) {
			throw new IOException(th.getLocalizedMessage());
		}
	}

	/**
	 * Get template file name
	 */
	public String toString() {
		return mFile.getAbsolutePath();
	}

}
