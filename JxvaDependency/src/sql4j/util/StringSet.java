package sql4j.util;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author: Jianguo Lu
 */
import java.util.Vector;
import java.util.Enumeration;

/** The <pre> set </pre> class represent a set of class. 
  * @see Signature, Vector
 * @author <a href="mailto:jglu@cs. toronto.edu"> Jianguo Lu </a> 
*/

public class StringSet extends Vector{
	  
/**
 * Insert the method's description here.
 * Creation date: (2/1/01 3:55:40 PM)
 */
public StringSet() {}
  /** Construct a set from an array. 
   **/
  public StringSet(String[] a){
	super();
	for (int i=0; i<a.length;i++){
	  this.add(a[i]);
	}
  }            
	public StringSet(Vector v){
		for (Enumeration e=v.elements(); e.hasMoreElements();){
			String s=e.nextElement().toString();
			add(s);
		}
	}  /** s1 = s2 if every element in s1 is contained in s2 and vice versa.
	*/
  /** add an element to a set.*/
  public boolean add(Object o){
	if(!this.contains(o)){
	  this.addElement(o);
	  return true;
	}

	return false;
  }            
  /** test whether there is a string s inside the set.
   **/
  public boolean contains(String s){
	for(Enumeration e=this.elements();e.hasMoreElements();){
	  Object o=e.nextElement();
	  if(o instanceof String){
	if( ((String)o).trim().equalsIgnoreCase(s.trim())) return true;
	  } else Misc.message("String set expected.");
	}
	return false;
  }            
  public boolean equals(StringSet s2){
	boolean result = true;
	for (Enumeration e = this.elements() ; e.hasMoreElements() ;) {
	  Object o=e.nextElement();
	  if (!s2.contains(o)) result = false;
	  //System.out.println(o);
	}
	
	for (Enumeration e = s2.elements() ; e.hasMoreElements() ;) {
	  Object o=e.nextElement();
	  if (!this.contains(o)) result = false;
	  //System.out.println(o);
	}
	
	return result;
  }        /** Get the intersection of two sets.
   **/                  
public boolean fuzzyContains(String s) {
	for (Enumeration e = this.elements(); e.hasMoreElements();) {
		Object o = e.nextElement();
		if (o instanceof String) {
			//if( Misc.fuzzyEquals((String)o, s)){
			//System.out.println("fuzzy equals:"+s+"\nfuzzy equals:"+ (String)o);
			// return true;
		}
		
	}
	return false;
}
  public StringSet intersection(StringSet s2){
	StringSet result = new StringSet();
	if (s2==null) return this;
	if (this==null) return s2;
	for (Enumeration e=this.elements();e.hasMoreElements();){
	  Object o=e.nextElement();
	  if (s2.contains((String)o))result.add(o);
	}
	return result;
  }              /**
   ** Method : isSubsetOf (Set)
   ** Purpose: Returns true if 'this' Set is not empty Strings,
   **          and if all Strings in 'this' Set can be found in
   **          Set 's2'.
   **/                  
  public boolean isSubsetOf(StringSet s2) {
	for (int i=0; i<this.size(); i++)
	  if (!s2.contains((String)this.elementAt(i)))
		return false;
	// check to make sure we don't have all empty Strings
	for (int i=0; i<this.size(); i++)
	  if (!((String)this.elementAt(i)).equals(""))
		return true;
	return false;
  }        /** minus operation of the two sets. 
   **/                  
  public StringSet minus(StringSet s){
	StringSet result=new StringSet();
	for (Enumeration e=this.elements(); e.hasMoreElements();){
	  Object o=e.nextElement();
	  if (o instanceof String){
	if (!s.contains((String)o)) result.add(o);
	  }else {
	if (!s.contains(o)) result.add(o);
	  }
	}
	return result;
  }          /** get the union of the two sets.
   **/                  
  /** remove an alement from a set. */
  public boolean remove(Object o){
	removeElement(o);
	if (this.contains(o)){
	  System.out.println("Unexpected multiple occurrence of objects in the class Set");
	}

	return true;
  }            
  /** tansform the set into an Array of String. 
   **/
  public Object[] toArray(){     
	String[] result=new String[this.size()];
	int count=0;
	for(Enumeration e=this.elements(); e.hasMoreElements();){
	  Object item=e.nextElement();
	  if (item instanceof String){
	result[count]=(String)item;
	  } else Misc.message("Set.java: String expected in toArray.");
	  count++;
	}
	return Misc.compress(result);
  }            
  public String toStr(){
	return Misc.toString((String[]) this.toArray());
  }            
  public StringSet union(StringSet s2){
	StringSet result=this;
	if (s2==null) return this;
	for(Enumeration e=s2.elements();e.hasMoreElements();){
	  Object o=e.nextElement();
	  if(!this.contains(o)) result.addElement(o);
	}
	return result;
  }            
}