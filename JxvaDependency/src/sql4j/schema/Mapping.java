package sql4j.schema;

/**
 * Insert the type's description here.
 * Creation date: (11/2/00 2:51:18 PM)
 * @author: Jianguo Lu
 */

import java.util.*;


import sql4j.parser.*;import sql4j.util.*;
 
public class Mapping {
	protected Hashtable mapping;
/**
 * Mapping constructor comment.
 */
public Mapping() {
	super();
}
	public Mapping(Hashtable ht){ mapping = ht;
	}
/** Given a V4 name, returns a set of v5 names.
**/
public Set applyMapping(String source) {
	if (source==null) return null;
	Object o = mapping.get(source.toUpperCase());
	Set result;
	if (o==null){
		result=null;
	}else if (o instanceof Set){
		result=(Set) o;
	}else {
	    result=new HashSet();
	    result.add(o);
	}
	return result;
}
// from a vector of string, get a vector of string.
public Vector applyMapping(Vector v) {
	HashSet hs=new HashSet();
	for (Enumeration e=v.elements();e.hasMoreElements();){
		String s=e.nextElement().toString();
		Set v5s = this.applyMapping(s);
		hs.addAll(v5s);
	}
	Vector result = new Vector();
	Iterator it = hs.iterator();
	while (it.hasNext()) {
		result.add((String)(it.next()));
	}
	return result;
}
public Hashtable getMapping() {
	return mapping;
}
/** Given a set of V4 table names, returns portion of the table name mapping
that is relevant.
**/
public Mapping getMapping(Set v4s) {
	Hashtable ht = new Hashtable();
	for (Iterator i = v4s.iterator(); i.hasNext();) {
		Object o = i.next();
		String v4 = "";
		if (o instanceof Table) {
			v4 = ((Table) o).toString();
		} else
			if (o instanceof Column) {
				v4 = ((Column) o).toString();
				//v4 = Misc.lastOfDottedName(v4);

			} else
				if (o instanceof String) {
					v4 = (String) o;
				}

		Set v5s = this.applyMapping(v4);
		ht.put(v4, v5s);
	}
	return new Mapping(ht);
}
	public Mapping getMapping(Vector v) {
		return getMapping(new HashSet(v));
	}
public Object put(Object k, Object d) {
	if (mapping == null)
		mapping = new Hashtable();
	return mapping.put(k, d);

}/**
 * Insert the method's description here.
 * Creation date: (1/18/01 8:27:49 PM)
 * @return java.util.Vector
 * @param v java.util.Vector
 */      
public void putAll(Mapping m) {
	if (m != null) {
		if (mapping == null) {
			mapping = m.getMapping();
		} else
			if (m.getMapping() != null)
				mapping.putAll(m.getMapping());
	}
}
/**
 * Insert the method's description here.
 * Creation date: (11/8/00 5:47:32 PM)
 * @return java.lang.String
 */
public String toXML() {
	return null;
}
}