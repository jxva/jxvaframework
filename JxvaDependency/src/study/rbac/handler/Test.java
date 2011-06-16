package study.rbac.handler;

import java.util.HashSet;
import java.util.Set;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] s="3,5,6,1,2,4,2,3,4,6,2,5,7,2,4,7".split(",");
		Set<String> set=new HashSet<String>();
		for(int i=0;i<s.length;i++){
			set.add(s[i]);
		}
		String tmp=set.toString();
		System.out.println(tmp.substring(1,tmp.length()-1).replaceAll(", ",","));
	}

}
