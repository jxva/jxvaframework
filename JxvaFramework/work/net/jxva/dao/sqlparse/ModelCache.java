package net.jxva.dao.sqlparse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.jxva.dao.sqlparse.entry.ModelEntry;

import com.jxva.dao.Model;



public class ModelCache {

	private static final SortedMap<String,ModelEntry> cache=new TreeMap<String,ModelEntry>(new CacheComparator());
	private static final List<String> list=new ArrayList<String>();
	
	static{
		
		try{
			list.add("net.jxva.dao.model.Book");
			list.add("net.jxva.dao.model.User");
			list.add("net.jxva.dao.model.Publish");
			
			for(String clsName:list){
				ModelEntry entry=new ModelEntry();
				entry.setClassName(Class.forName(clsName));
				Model model=(Model)entry.getClassName().getDeclaredAnnotations()[0];
				entry.setModel(model);
				SortedSet<String> columns=new TreeSet<String>(new CacheComparator());
				SortedSet<String> upperCharColumns=new TreeSet<String>(new CacheComparator());
				Field[] fields=entry.getClassName().getDeclaredFields();
				for(Field field:fields){
					if(field.getName().equals("serialVersionUID")||field.getDeclaredAnnotations().length>0)continue;
					if(!field.getName().equals(field.getName().toLowerCase()))upperCharColumns.add(field.getName());
					columns.add(field.getName());
				}
				entry.setUpperCharColumns(upperCharColumns);
				entry.setColumns(columns);
				cache.put(entry.getClassName().getSimpleName(),entry);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static SortedMap<String,ModelEntry> getCache(){
		return cache;
	}
}
