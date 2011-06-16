package com.jxva.dao.entry;

public class ColumnEntry {
	
	private String columnName;
	private String getterName;
	private String setterName;
	private int type;

	
	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}
	public String getGetterName() {
		return getterName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getSetterName() {
		return setterName;
	}
	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
