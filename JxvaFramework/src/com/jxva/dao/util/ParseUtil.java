package com.jxva.dao.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.TableEntry;

public abstract class ParseUtil {
	
	public static String replaceColumns(final TableEntry tableEntry,final String jql){
		final String specialFields=tableEntry.getSpecialFields();
		if(specialFields==null)return jql;
		final Map<String,ColumnEntry> columnEntrys=tableEntry.getColumnEntrys();
		final Matcher matcher=Pattern.compile(specialFields).matcher(jql);
		final StringBuffer sb = new StringBuffer();   
		while(matcher.find()){
			matcher.appendReplacement(sb,columnEntrys.get(matcher.group()).getColumnName());
		}
		matcher.appendTail(sb); 
		return sb.toString();
	}
}
