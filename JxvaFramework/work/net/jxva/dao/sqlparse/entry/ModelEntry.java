package net.jxva.dao.sqlparse.entry;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.SortedSet;

import com.jxva.dao.Model;

public class ModelEntry {
	
	private Class<?> className;
	private Model model;
	private SortedSet<String> columns;
	private SortedSet<String> upperCharColumns;
	private Set<Field> fields;

	public SortedSet<String> getColumns() {
		return columns;
	}
	public void setColumns(SortedSet<String> columns) {
		this.columns = columns;
	}
	public void setClassName(Class<?> className) {
		this.className = className;
	}
	public Class<?> getClassName() {
		return className;
	}
	public void setUpperCharColumns(SortedSet<String> upperCharColumns) {
		this.upperCharColumns = upperCharColumns;
	}
	public SortedSet<String> getUpperCharColumns() {
		return upperCharColumns;
	}
	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}
	public Set<Field> getFields() {
		return fields;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Model getModel() {
		return model;
	}
}
