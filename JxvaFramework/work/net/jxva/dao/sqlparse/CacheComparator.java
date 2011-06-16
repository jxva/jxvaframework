package net.jxva.dao.sqlparse;

import java.util.Comparator;

public class CacheComparator implements Comparator<String>{

		public int compare(String s1, String s2) {
			return s2.compareTo(s1);
		}

	}