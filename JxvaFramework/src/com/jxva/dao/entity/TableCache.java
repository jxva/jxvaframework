package com.jxva.dao.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jxva.dao.DAOFactory;
import com.jxva.dao.ParseException;
import com.jxva.dao.annotation.Column;
import com.jxva.dao.annotation.Table;
import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.TableEntry;
import com.jxva.dao.type.JavaType;
import com.jxva.entity.ClassFinder;
import com.jxva.util.ModelUtil;
import com.jxva.util.StringUtil;

public class TableCache {

	private static final Comparator<String> stringReverseComparator=new Comparator<String>(){
		public int compare(String k1, String k2) {
			return k2.compareTo(k1);
		}
	};
	
	private static final SortedMap<String,TableEntry> cache=new TreeMap<String,TableEntry>(stringReverseComparator);

	static{
		try{
			List<String> list=new ArrayList<String>();
			
			if(Environment.getEnvironment()==null){
				DAOFactory.getInstance();
			}
			String pkgName=Environment.getEnvironment().getPkgName();
			String[] pkgNames=pkgName.split("\\,");
			for(String pkg:pkgNames){
				Set<Class<?>> clzes = ClassFinder.getClasses(pkg);
				for(Class<?> c:clzes){
					list.add(c.getName());
				}
			}
			
			for(String clsName:list){
				TableEntry entry=new TableEntry();
				Class<?> cls=Class.forName(clsName);
				Annotation[] as=cls.getDeclaredAnnotations();
				if(as.length==0)continue;
				Table table=(Table)as[0];
				entry.setTable(table);
				entry.setModel(cls);
				entry.setTableName(table.name());
				
				SortedMap<String,ColumnEntry> specialFields=new TreeMap<String,ColumnEntry>(stringReverseComparator);
				
				Field[] fields=cls.getDeclaredFields();
				Map<String,ColumnEntry> columnEntrys=new LinkedHashMap<String,ColumnEntry>(fields.length);
				StringBuilder allColumns=new StringBuilder(fields.length);
				for(Field field:fields){
					String fieldName=field.getName();
					if(fieldName.equals("serialVersionUID"))continue;
					Annotation[] annotations=field.getDeclaredAnnotations();
					
					ColumnEntry columnEntry=new ColumnEntry();
					
					columnEntry.setGetterName(ModelUtil.getGetterName(fieldName));
					columnEntry.setSetterName(ModelUtil.getSetterName(fieldName));
					columnEntry.setType(JavaType.get(field.getType()));
					
					if(annotations.length==0){//不包含注解
						if(StringUtil.containsUpperChar(fieldName)){//包含大写字符,将大写字符替换为小写并在前加_下划线
							columnEntry.setColumnName(ModelUtil.getColumnName(fieldName));
							specialFields.put(fieldName,columnEntry);
						}else{//原始
							columnEntry.setColumnName(fieldName);
						}
					}else{
						Annotation annotation=annotations[0];
						if(annotation instanceof Column){
							Column column=(Column)annotation;
							columnEntry.setColumnName(column.name());
							specialFields.put(fieldName,columnEntry);
						}else{
							continue;
						}
					}
					
					columnEntrys.put(fieldName,columnEntry);
					allColumns.append(columnEntry.getColumnName()).append(',');
				}
				entry.setColumnEntrys(columnEntrys);
				entry.setSpecialFields(mapKeysToString(specialFields));
				entry.setColumnNames(allColumns.substring(0,allColumns.length()-1).toString());
				
				ColumnEntry incrementColumn=entry.getColumnEntrys().get(table.increment());
				entry.setIncrementColumn(incrementColumn==null?"":incrementColumn.getColumnName());
				entry.setFirstPrimaryKeyColumn((entry.getColumnEntrys().get(table.primaryKeys()[0]).getColumnName()));
				cache.put(clsName.substring(clsName.lastIndexOf('.')+1),entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String mapKeysToString(SortedMap<String,ColumnEntry> specialColumnEntryMap){
		if(specialColumnEntryMap.size()==0)return null;
		StringBuilder sb=new StringBuilder();
		for(String key:specialColumnEntryMap.keySet()){
			sb.append(key).append('|');
		}
		return sb.substring(0,sb.length()-1).toString();
	}
	
//	public static SortedMap<String,TableEntry> getCache(){
//		return cache;
//	}
	public static TableEntry getTableEntry(String model){
		TableEntry tableEntry=cache.get(model);
		if(tableEntry==null){
			throw new ParseException("'"+model+"' model does not exist or  is not a validate model, checking your JQL is correct.");
		}else{
			return tableEntry;
		}
	}
}
