package org.jxva.mvel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.jxva.tag.model.Category;
import org.mvel2.MVEL;

import com.jxva.util.ModelUtil;

public class Test {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Category category=new Category();
		category.setCategoryId(1);
		category.setDescription("编程设计语言");
		category.setName("Java");
		int count=10000;
		long s,e;
		
		
		
		s=System.nanoTime();
		for(int i=0;i<count;i++){
			category.getClass().getMethod(ModelUtil.getGetterName("name")).invoke(category).toString();
		}
		e=System.nanoTime();
		System.out.println("Reflect:"+(e-s));
		
		
		
		Serializable se=MVEL.compileExpression("name");
		s=System.nanoTime();
		for(int i=0;i<count;i++){
			MVEL.executeExpression(se,category);
		}
		e=System.nanoTime();
		System.out.println("MVEL   :"+(e-s));
	}

}
