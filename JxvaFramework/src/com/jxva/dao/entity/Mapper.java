package com.jxva.dao.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.ModelEntry;
import com.jxva.dao.type.Blob;
import com.jxva.dao.type.ByteList;
import com.jxva.dao.type.Clob;
import com.jxva.util.DateUtil;
import com.jxva.util.ModelUtil;

public abstract class Mapper {
	/**
	 * 
	 * example:
	 * 		dao.find(Test.class,"aa<? and aa>?",1,40);
	 * 		dao.findUnique(Test.class,"cc like ?","%c%");
	 * @param ps
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement setValues(PreparedStatement ps,Object...values)throws SQLException{
		if(values.length==0)return ps;
		try{
			int valuesLength=values.length;
			if(valuesLength==0)return ps;
			for (int i = 1; i<=valuesLength; i++) {
				Object value=values[i-1];
				if(value instanceof Character){
					ps.setString(i,Character.toString((value.toString().charAt(0))));
				}else if(value instanceof Clob){
					ps.setClob(i,(Clob)value);
				}else if(value instanceof Blob){
					ps.setBlob(i,(Blob)value);
					//ps.setBinaryStream(j,((Blob) value).getBinaryStream(),((Blob) value).getBinaryStream().available());
				}else if(value instanceof java.io.InputStream){
					InputStream is=(InputStream)value;
					ps.setBinaryStream(i,is,is.available());
				}else{
					ps.setObject(i,value);
				}
				/**
				if(value instanceof String){
					ps.setString(j,vs);
				}else if(value instanceof Integer){
					ps.setInt(j,Integer.valueOf(vs));
				}else if(value instanceof Timestamp){
					ps.setTimestamp(j,Timestamp.valueOf(vs));
				}else if(value instanceof Short){
					ps.setShort(j,Short.valueOf(vs));
				}else if(value instanceof Long){
					ps.setLong(j,Long.valueOf(vs));
				}else if(value instanceof Date){
					ps.setDate(j,Date.valueOf(vs));
				}else if(value instanceof Double){
					ps.setDouble(j,Double.valueOf(vs));
				}else if(value instanceof Time){
					ps.setTime(j,Time.valueOf(vs));
				}else if(value instanceof Character){
					ps.setString(j,Character.toString((vs.charAt(0))));
				}else if(value instanceof BigDecimal){
					ps.setBigDecimal(j,new BigDecimal(vs));
				}else if(value instanceof Blob){
					ps.setBlob(j,(Blob)value);
				}else if(value instanceof Clob){
					ps.setClob(j,(Clob)value);
				}
				*/
			}
			return ps;
		}catch(Exception e){
			throw new SQLException(e);
		}
	}
	
//	public static <T> T getObject(Class<T> cls,ResultSet rs) throws Exception{
//		T object=cls.newInstance();
//		List<Field> fs=FieldUtil.getAllFields(cls);
//		for (int i = 0; i < fs.size(); i++) {
//			if(!assignValue(cls, object,fs.get(i),rs,-1)){
//				continue;
//			}			
//		}
//		return object;
//	}
	
	public static Object getObject(ModelEntry[] modelEntrys,ResultSet rs) throws Exception{
		Object object=modelEntrys[0].getTableEntry().getModel().newInstance();
		Map<String,ColumnEntry> columns=modelEntrys[0].getTableEntry().getColumnEntrys();
		int i=0;
		for (String key:columns.keySet()) {
			Field fs=modelEntrys[0].getTableEntry().getModel().getDeclaredField(key);
			i++;
			if(!assignValue(modelEntrys[0].getTableEntry().getModel(), object,fs,rs,i)){
				continue;
			}			
		}
		int n=columns.size();
		for(int j=0;j<modelEntrys.length-1;j++){
			ModelEntry modelEntry=modelEntrys[j+1];
			
			if(!modelEntrys[j].isFetch()){
				n+=modelEntry.getTableEntry().getColumnEntrys().size();
				continue;
			}else{//针对OneToOne的
				Object value=getObject(modelEntry,n,rs);
				modelEntrys[0].getTableEntry().getModel().getMethod(ModelUtil.getFieldName(modelEntrys[0].getModelName()),modelEntrys[0].getTableEntry().getModel()).invoke(object,value);
				
			}
			n+=modelEntry.getTableEntry().getColumnEntrys().size();
		}
		return object;
	}
	
	public static Object getObject(ModelEntry modelEntry,int startPos,ResultSet rs) throws Exception{
		Object object=modelEntry.getTableEntry().getModel().newInstance();
		Map<String,ColumnEntry> columns=modelEntry.getTableEntry().getColumnEntrys();
		int i=startPos;
		for (String key:columns.keySet()) {
			Field fs=modelEntry.getTableEntry().getModel().getDeclaredField(key);
			i++;
			if(!assignValue(modelEntry.getTableEntry().getModel(), object,fs,rs,i)){
				continue;
			}			
		}
		return object;
	}
	
	public static <T> T getObject(Class<T> cls,ResultSet rs) throws Exception{
		T object=cls.newInstance();
		Map<String,ColumnEntry> columns=TableCache.getTableEntry(cls.getSimpleName()).getColumnEntrys();
		int i=0;
		for (String column:columns.keySet()) {
			Field fs=cls.getDeclaredField(column);
			i++;
			if(!assignValue(cls, object,fs,rs,i)){
				continue;
			}			
		}
		return object;
	}

	public static boolean assignValue(Class<?> cls, Object object,Field field,ResultSet rs,int pos) throws Exception {
		String name = ModelUtil.getSetterName(field.getName());
		
		Class<?> type = field.getType();
		//String value = (pos==-1?rs.getString(name):rs.getString(pos));
		String value=rs.getString(pos);
		if (value == null||(value.trim().equals("")&&!type.equals(java.lang.String.class))) {
			return false;
		}
		if (type.equals(java.lang.String.class)) {//need using switch optimize
			cls.getDeclaredMethod(name,java.lang.String.class).invoke(object,value);
		}else if (type.equals(java.lang.Integer.class)) {
			cls.getDeclaredMethod(name,java.lang.Integer.class).invoke(object,Integer.valueOf(value));
		}else if (type.equals(java.sql.Timestamp.class)) {
			cls.getDeclaredMethod(name,java.sql.Timestamp.class).invoke(object,Timestamp.valueOf(value));
		}else if (type.equals(java.lang.Short.class)){
			cls.getDeclaredMethod(name,java.lang.Short.class).invoke(object,Short.valueOf(value));
		}else if (type.equals(java.lang.Long.class)) {
			cls.getDeclaredMethod(name,java.lang.Long.class).invoke(object,Long.valueOf(value));
		}else if (type.equals(java.sql.Date.class)) {
			cls.getDeclaredMethod(name,java.sql.Date.class).invoke(object,Date.valueOf(value));
		}else if (type.equals(java.lang.Double.class)) {
			cls.getDeclaredMethod(name,java.lang.Double.class).invoke(object,Double.valueOf(value));
		}else if (type.equals(java.sql.Time.class)){
			cls.getDeclaredMethod(name,java.sql.Time.class).invoke(object,Time.valueOf(value));
		}else if (type.equals(java.lang.Character.class)){
			cls.getDeclaredMethod(name,java.lang.Character.class).invoke(object,Character.valueOf(value.charAt(0)));
		}else if(type.equals(java.math.BigDecimal.class)){
			cls.getDeclaredMethod(name,java.math.BigDecimal.class).invoke(object,new java.math.BigDecimal(value));
		}else if(type.equals(java.lang.Float.class)){
			cls.getDeclaredMethod(name,java.lang.Float.class).invoke(object,Float.valueOf(value));
		}else if(type.equals(java.sql.Blob.class)){
			java.sql.Blob blob=(pos==-1?rs.getBlob(name):rs.getBlob(pos));
			if(blob!=null){
				cls.getDeclaredMethod(name,java.sql.Blob.class).invoke(object,new Blob(blob.getBinaryStream()));
			}
		}else if(type.equals(java.sql.Clob.class)){
			java.sql.Clob clob=(pos==-1?rs.getClob(name):rs.getClob(pos));
			if(clob!=null){
				cls.getDeclaredMethod(name,java.sql.Clob.class).invoke(object,new Clob(clob.getCharacterStream()));
			}
		}else if(type.equals(java.io.InputStream.class)){
			InputStream in=(pos==-1?rs.getBinaryStream(name):rs.getBinaryStream(pos));
			if(in!=null){
				ByteList bl=new ByteList();
				bl.read(in);
				cls.getDeclaredMethod(name,java.io.InputStream.class).invoke(object,new ByteArrayInputStream(bl.detach()));
			}
		}else if(type.equals(java.util.Date.class)){
			cls.getDeclaredMethod(name,java.util.Date.class).invoke(object,DateUtil.parse(value));
		}else{
			throw new Exception("don't support type:"+type);
		}
		return true;
	}
}
