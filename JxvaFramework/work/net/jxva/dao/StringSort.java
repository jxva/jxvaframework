package net.jxva.dao;

import java.util.Collections;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class StringSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SortedSet<String> sortedSet=new TreeSet<String>(new StringComparator());
		Collections.addAll(sortedSet,"one threea two three four three_ five six seven eight eighta".split(" "));
		System.out.println(sortedSet);

		SortedMap<String,String> map=new TreeMap<String,String>(new StringComparator());
		map.put("one","one");
		map.put("threea","threea");
		map.put("two","one");
		map.put("three","one");
		map.put("four","one");
		map.put("three_","three_");
		map.put("five","one");
		map.put("six","one");
		map.put("seven","one");
		map.put("eight","one");
		map.put("eighta","one");
		for(String key:map.keySet()){
			System.out.println(key);
		}
	}

}
