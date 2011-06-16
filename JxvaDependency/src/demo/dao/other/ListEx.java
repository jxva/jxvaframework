package demo.dao.other;

import java.util.ArrayList;
import java.util.Collection;

public class ListEx<E> extends ArrayList<E>{

	private static final long serialVersionUID = 4340780241111491475L;
	
	public ListEx(){
		super();
	}
	
	public ListEx(int initialCapacity){
		super(initialCapacity);
	}
	
	 public ListEx(Collection<? extends E> c){
		 super(c);
	 }
	 
	 public String setParam(Object obj){
		 return obj.toString();
	 }
	 
	 public static void main(String[] args){
		 ListEx<String> list=new ListEx<String>();
		 list.setParam("dddd");
		 
	 }
}
