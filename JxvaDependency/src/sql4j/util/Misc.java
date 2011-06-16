package sql4j.util;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author: Jianguo Lu
 */
 
import org.w3c.dom.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Misc{
				
			  					  /**
 * Misc constructor comment.
 */
public Misc() {
	super();
}
  /**
   ** Method : addIneqs (String)
   ** Purpose: Replaces '&lt;' with '<' , and '&gt;' with '>' in a String.
   **          Required for conditions in templates to parse properly.
   **/
  public static String addIneqs(String s){
	String result = replaceStr(s, "&lt;", "<");
	return replaceStr(result, "&gt;", ">");
  }            
  /**
   * Removes characters from xml document that are known to cause parsing errors
   */
  public static String cleanXML(String xml) {
	String result = xml;
	//result = result.replace('?, '*');
	return result;
  }            
  /**
   ** Method : compress (String[])
   ** Purpose: Removes all null Strings from the input String array, and returns the result.
   **/
  public static String[] compress(String[] a){
	int leng = a.length;
	int nullCount=0;
	for (int i =0; i<leng; i++){
	  if (a[i]==null) nullCount++;
	}
	String[] result = new String[leng-nullCount];
	int count = 0;
	for (int i = 0; i<leng; i++){
	  if (a[i]!=null){
	result[count]=a[i];
	count++;
	  }
	}
	return result;
  }            
  /**
   ** Method : compressEmpty (String[])
   ** Purpose: Removes all empty Strings from the input String array, and returns the result.
   **/
  public static String[] compressEmpty(String[] a){
	Vector resultV = new Vector();
	for (int i=0; i<a.length; i++)
	  if (!a[i].equals(""))
		resultV.addElement(a[i]);
	String[] result = new String[resultV.size()];
	resultV.copyInto(result);
	return result;
  }            
  /**
   ** Method : concat (String[], String[])
   ** Purpose: Concatenates two String arrays into a larger array, all elements preserved
   **/
  public static String[] concat(String[] str1, String[] str2) {
	int len1 = str1.length;
	int len2 = str2.length;
	String[] combined = new String[len1 + len2];
	for (int i=0; i<len1; i++)
	  combined[i]=str1[i];
	for (int i=0; i<len2; i++)
	  combined[len1+i]=str2[i];
	return combined;
  }            
/**
 * Insert the method's description here.
 * Creation date: (1/17/01 3:29:22 PM)
 * @return boolean
 * @param v java.util.Vector
 * @param s java.lang.String
 */
public static boolean contains(Vector v, String s) {
	for (Enumeration e = v.elements(); e.hasMoreElements();) {
		if (s.equals((String) e.nextElement()))
			return true;
	}
	return false;
}/**
 * get the last token of the dotted name.
 * Creation date: (1/11/01 10:49:44 AM)
 * @return java.lang.String
 * @param dottedName java.lang.String
 */      
  public static void debugMessage(Object s){

	// if(XIBData.DEBUG) JOptionPane.showMessageDialog(null,s);
	if (s instanceof String)
	  System.out.println((String)s);
  }            
  /** change <XMP> "&quot;" </XMP> to <CODE> "\""</CODE>, etc.
   **/
  public static String deNormalize(String s){
	String str = "";
	int len = (s!=null)?s.length():0;
	for(int i=0; i<len; i++){
	  char ch = s.charAt(i);
	  if (ch == '&'){
	    if (s.charAt(i+1)=='q' && 
	        s.charAt(i+2)=='u' && 
	        s.charAt(i+3)=='o' &&
	        s.charAt(i+4)=='t' && 
	        s.charAt(i+5)==';' ){
	      str+="\"";
	      i=i+5;
	    } else {
	} 
	  }
	  str+=ch;
	}
	return str;
  }            
  /** Write error messages to the errlog file.
   */
  public static void errlog(String s){
	try{
	  File outfile;
	  outfile = new File("errlog");
	  FileOutputStream fout;
	  fout = new FileOutputStream(outfile);
	  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));     
	  bw.write(s);
	  bw.write("/n/n");
	  bw.close();
	  fout.close();
	}catch (Exception e){e.printStackTrace();
	}
  }            
  public static String file2string(File file){	
	String result="";
	try{
	  FileInputStream fin = new FileInputStream(file);
	  BufferedReader br = new BufferedReader(new InputStreamReader(fin));
	  String thisLine;
	  while ((thisLine = br.readLine())!=null) {
	result=result+thisLine+"\n";
	  }
	  fin.close();
	}catch (Exception e){
	  e.printStackTrace();
	}
	return result;
  }            
  /** read a file into a string.
   **/
  public static String file2string(String fileName){ 
	String result="";
	try{
	  File myfile;
	  FileInputStream fin;
	  
	  myfile=new File(fileName);
	  fin = new FileInputStream(myfile);
	  BufferedReader br = new BufferedReader(new InputStreamReader(fin));
	  String thisLine;
	  while ((thisLine = br.readLine())!=null) {
	    result=result+thisLine+"\n";
	  }
	  fin.close();
	}catch (Exception e){
	  Misc.message("fail to read a file in file2string. File name is "+fileName);
	  e.printStackTrace();
	}
	return result;
  }            
  /** filter out the carriage returns and ampersand symbols in the string. 
   **/
  public static String filter(String s){
	String s2 = replaceStr(s, "&amp;", "and");
	String result = "";
	for (int i = 0; i < s2.length(); i++) {
	  char ch = s2.charAt(i);
	  switch (ch) {
	  case '&' : result += "and";
	  case '\r':
	  case '\n': {
	    break;
	  }
	  default: {
	    result += ch;
	  }
	  }
	}
	return result;
  }            
  /** filter out the spaces in the string.
   **/
  public static String filterSpaces(String s){
		String str = "";
		int len = (s != null) ? s.length() : 0;
		for (int i = 0; i < len; i++) {
		  char ch = s.charAt(i);
		  switch (ch) {
		  case ' ': break;
		  default : str+=ch;
		  }
		}
		return str;
  }            
  /** inverse the hashtable.
   **/
  public static Hashtable inverse(Hashtable ht){
	if (ht==null) return null;
	Hashtable result=new Hashtable();
	Enumeration keys = ht.keys();
	while(keys.hasMoreElements()){
	  Object key = keys.nextElement();
	  Object value = ht.get(key);
	  result.put(value, key);
	}
	return result;
  }            
  /**
   ** Method : isANumber (String)
   ** Purpose: Tests if a String can be parsed as a number.
   **/
  public static boolean isANumber(String input) {
	StringTokenizer st = new StringTokenizer(input, "1234567890., ");
	if (st.hasMoreTokens())
	  return false;
	return true;
  }            
public static String lastOfDottedName(String dottedName) {
 	StringTokenizer st= new StringTokenizer(dottedName,".");
 	int number = st.countTokens();
 	for(int i=0;i<number-1;i++){st.nextToken();}
 	if (st.hasMoreTokens()) {return st.nextToken();
 	} else return "";
}/*
 * Insert the method's description here.
 * Creation date: (1/17/01 3:24:54 PM)
 */      
  /** show a long message.
   **/
  public static void longMessage(String s){
	JTextArea text = new JTextArea(s, 20, 40);
	JPanel p = new JPanel();
	p.add(text);
	JFrame frame = new JFrame();
	frame.getContentPane().add(p);
	frame.setVisible(true);
  }            
  /** show a message.
   **/
  public static void message(Object s){

	/**if(XIBData.isApp)
	  JOptionPane.showMessageDialog(null,s);
	**/
	if (s instanceof String)
	  System.out.println((String)s);
  }            
  /**
   ** Method : minIndex (String, String, String)
   ** Purpose: Compares where 'in1' and 'in2' are located in String 'input', and returns
   **          the smaller positive index of the two.
   **/
  public static int minIndex(String input, String in1, String in2) {
	int index1 = input.indexOf(in1);
	int index2 = input.indexOf(in2);
	if (index1 == -1) return index2;
	else if (index2 == -1) return index1;
	else return Math.min(index1, index2);
  }            
  /**
   ** Method : optionFilter (String)
   ** Purpose: Converts all '+'s in a String to ' 's, required for converting
   **          XML Description values to regular Strings.
   **/
  public static String optionFilter(String in){
	String result = "";
	int len = (in!=null)?in.length():0;
	for (int i=0; i<len; i++) {
	  char ch = in.charAt(i);
	  if (ch == '+')
		result+=" ";
	  else
		result+=ch;
	}
	return result;
  }            
  /** generates a number between 10000-99999
   **/
  public static int random(){
	return 10000+(int)(89999*Math.random());
  }            
  /**
   * Replace all the occurrences of "target" with "newStr". 
   * @return java.lang.String
   * @param str java.lang.String
   * @param target java.lang.String
   */
  public static String replace(String str, String target, String newStr) {
	int i = str.indexOf(target);
	if (i == -1)
	  return str;
	else
	  return str.substring(0, i) + newStr + Misc.replace(str.substring(i + target.length()),target,newStr);
  }            
  /**
   * This method was created in VisualAge.
   * @return java.lang.String
   * @param str java.lang.String
   * @param target java.lang.String
   */
  public static String replaceStr(String str, String target, String newStr) {
	int i = str.indexOf(target);
	if (i == -1)
	  return str;
	else
	  return str.substring(0, i) + newStr + Misc.replace(str.substring(i + target.length()),target,newStr);
  }            
  /** change '<' to '&lt;' and '>' to '&gt;'
   ** required to interpret templates
   **/
  public static String rmvIneqs(String s){
	String result = replaceStr(s, "<", "&lt;");
	return replaceStr(result, ">", "&gt;");
  }            
  public static String[] shrink(String[] myStr, int drop) {
	String[] result = new String[myStr.length-1];
	for (int i=0; i<drop; i++)
	  result[i]=myStr[i];
	for (int i=drop+1; i<myStr.length; i++)
	  result[i-1]=myStr[i];
	return result;
  }            
  /**
   ** Method : sortTable (String[][])
   ** Purpose: Sorts a 2D array of Strings by rows, using Misc.toString(String[]) as the
   **          comparison ordering. In general, rows will be sorted by the values in
   **          their first column. The sorting algorithm used is a bubble-sort, though
   **          a Q-sort would have been more efficient.
   **/
  public static String[][] sortTable(String[][] inputData){

	int tableLength = inputData.length;
	int tableWidth = (tableLength==0)?0:inputData[0].length;
	String[][] result = new String[tableLength][tableWidth];
	String[] mappingKeys = new String[tableLength];
	int[] mappingArray = new int[tableLength];
	for (int a=0; a<tableLength; a++) {
	  mappingKeys[a] = Misc.toString(inputData[a]);
	  mappingArray[a]=a;
	}
	
	// perform a bubble-sort using 'mappingArray' as 
	for (int a=0; a<tableLength; a++)
	  for (int b=0; b<tableLength-a-1; b++)
		if (mappingKeys[b].compareTo(mappingKeys[b+1])>0) {
		  String tempS=mappingKeys[b];
		  mappingKeys[b]=mappingKeys[b+1];
		  mappingKeys[b+1]=tempS;
		  int tempI = mappingArray[b]; 
		  mappingArray[b]=mappingArray[b+1];
		  mappingArray[b+1]=tempI;
		}
	for (int a=0; a<tableLength; a++)
	  result[a] = inputData[mappingArray[a]];
	return result;
  }            
  /** Write a string s into a file with the name fileName.
   **/
  public static void string2file(String s, String fileName){
	try{
	  File outfile;
	  outfile = new File(fileName);
	  FileOutputStream fout;
	  fout = new FileOutputStream(outfile);
	  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));     
	  bw.write(s);
	  bw.close();
	  fout.close();
	}catch (Exception e){e.printStackTrace();
	}
  }            
  /** Write a string s into a file with the name fileName, in the directory dirName.
   **/
  public static void string2file(String s, String dirName, String fileName){
	try{
	  File outfile;
	  outfile = new File(dirName);
	  outfile.mkdirs();
	  outfile = new File(dirName + fileName);
	  FileOutputStream fout;
	  fout = new FileOutputStream(outfile);
	  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));     
	  bw.write(s);
	  bw.flush();
	  bw.close();
	  fout.close();
	}catch (Exception e){e.printStackTrace();
	}
  }            
  /** transform the Vector to an array.
   **/
  public static Object[] toArray(Vector v){
	int size = v.size();
	Object[] result=new Object[size];
	int count = 0;
	for (Enumeration enum1=v.elements(); enum1.hasMoreElements();){
	  result[count]=enum1.nextElement();
	  count++;
	}
	return result;
  }            
  public static Element[] toElementArray(Vector v){
	int size = v.size();
	Element[] result=new Element[size];
	int count = 0;
	for (Enumeration enum1=v.elements(); enum1.hasMoreElements();){
	  Object o = enum1.nextElement();
	  if(o instanceof Element){
	result[count]=(Element)o;
	  }else message("Misc.java: Element type expected for the vector.");
	  count++;
	}
	return result;
  }            
  /** transform an array of Strings to a string.
   **/
  public static String toString(String[] array){
	  if (array==null) return "NULL";
	  String result = "";
	  for (int i=0; i<array.length; i++){
	  String item=(array[i]==null)?"NULL":array[i];
	  item=(item.equals(""))?"EMPTY":item;
	  if (i==0) {
	      result = result +item;
	  }else result = result + ", "  +item;
	  }
	  return result;
  }            
public static String toString(Vector v) {
	if (v == null)
		return null;
	String result = "";
	int count = 0;
	for (Enumeration e = v.elements(); e.hasMoreElements();) {
		Object elem = e.nextElement();
		if (count>0) result+=", ";
		if (elem instanceof String) {
			result += (String) elem;
		} else {
			result += elem.toString();
		}
		count++;
	}
	return result;
}
/**
   ** Method : toStrings (Vector)
   ** Purpose: Converts a Vector of String objects to an array of Strings.
   **/
public static String[] toStrings(Vector inputV) {
	if (inputV == null)
		return null;
	String[] result = new String[inputV.size()];
	int count=0;
	for(Enumeration e=inputV.elements();e.hasMoreElements();){
		Object elem=e.nextElement();
		if(elem instanceof String){
			result[count]=(String)elem;
		}else {
			//System.out.println("Not string vector;");
			result[count]=elem.toString();
		}
		count++;
	}
	return result;
}
  /** converts an array of strings to a vector object
   **/
  public static Vector toVector(String[] strings) {
	Vector result = new Vector();
	for (int i=0; i<strings.length; i++)
	   result.addElement(strings[i]);
	return result;
  }            
  /** transpose the two dimensional array.
   **/
  public static Object[][] transpose(Object[][]array){
	if(array==null) {return null;
	}else if (array.length==0) {return null;
	}else if (array[0].length==0) return null;

	Object result[][] = new Object[array[0].length][array.length];
	for (int i=0; i<array.length; i++){
	  for (int j=0; j<array[0].length;j++){
	result[j][i]=array[i][j];
	  }
	}
	return result;
  }            
  /** transpose a 2D string array
   **/
  public static String[][] transpose(String[][] array){
	if(array==null) {
	  return null;
	} else if (array.length==0) {
	  return null;
	} else if (array[0].length==0)
	  return null;

	String result[][] = new String[array[0].length][array.length];
	for (int i=0; i<array.length; i++){
	  for (int j=0; j<array[0].length;j++){
		result[j][i]=array[i][j];
	  }
	}
	return result;
  }            
  /** Filter used to converter url special character to normal strings. 
	 <XMP>
	  '+' needs to be converted to a single whitespace
	  '=' separate variable and value pairs, eg. "var1=val1&var2=val2&..."
	  '%2F' needs to be converted to '/'
	  '%3A' needs to be converted to ':'
	  '%40' needs to be converted to '@'
	  '%0D%0A' are created when the XSL version is used, these are CR/LFs, and must be removed
	  </XMP>
  */
  public static String urlFilter(String s){
	String result="";
	for (int j=0; j<s.length(); j++) {
	  char ch = s.charAt(j);
	  switch (ch){
	  case '+': result+=' ';
	  case '=': 
	  case '%': 
	if (s.charAt(j+1)=='2' && s.charAt(j+2)=='F') {
	  result+="/";
	} else if (s.charAt(j+1)=='3' && s.charAt(j+2)=='A') {
	  result+=":";
	} else if (s.charAt(j+1)=='4' && s.charAt(j+2)=='0') {
	  result+="@";
	} else if (s.charAt(j+1)=='0' && s.charAt(j+2)=='D' &&
		   s.charAt(j+3)=='%' && s.charAt(j+4)=='0' && s.charAt(j+5)=='A') {
	}
	  default: result+=ch;
	  }
	}
	return result;
  }            
}