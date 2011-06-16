package org.jxva.mvel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxva.tag.model.Category;
import org.mvel2.MVEL;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

public class MVELTest {
	
	public static void main(String[] args) {
		test5();
	}
	
	private static void test5(){
		List<Category> list=new ArrayList<Category>();
		
		Category category=new Category();
		category.setCategoryId(1);
		category.setDescription("编程设计语言");
		category.setName("Java");
		list.add(category);
		
		Category category2=new Category();
		category2.setCategoryId(2);
		category2.setDescription("数据库");
		category2.setName("MySQL");
		list.add(category2);
		Map vars = new HashMap();
		vars.put("list",list);
		
		String expression = "list[0]";
		//String output = (String) TemplateRuntime.eval(template,vars);
		Serializable compiled = MVEL.compileExpression(expression);
		System.out.println(compiled);
		
		//System.out.println(MVEL.executeExpression(compiled,vars));
		System.out.println(MVEL.eval("ssname",list.get(0)));
	}
	
	private static void test4(){
		String template = "Hello, @include{'src/main/java/org/mvel2/test/tpl.html'}my name is @{category.name.toUpperCase()}";
		Map vars = new HashMap();
		Category category=new Category();
		category.setCategoryId(1);
		category.setDescription("编程设计语言");
		category.setName("Java");
		vars.put("category",category);
		String output = (String) TemplateRuntime.eval(template,vars);
		System.out.println(output);
	}
	
	private static void test3(){
		String template = "Hello, my name is @{name.toUpperCase()}";
		Map vars = new HashMap();
		Category category=new Category();
		category.setCategoryId(1);
		category.setDescription("编程设计语言");
		category.setName("Java");
		vars.put("name", "Michael");

		String output = (String) TemplateRuntime.eval(template, vars);
		System.out.println(output);
		//===============
		String template2 = "1 + 1 = @{1+1}";

		// compile the template
		CompiledTemplate compiled = TemplateCompiler.compileTemplate(template2);
		// execute the template
		String output2 = (String) TemplateRuntime.execute(compiled);
		System.out.println(output2);
	}
	
	private static void test2(){
		String expression = "foobar > 99";

		Map vars = new HashMap();
		vars.put("foobar", new Integer(100));

		// We know this expression should return a boolean.
		Boolean result = (Boolean) MVEL.eval(expression, vars);

		if (result.booleanValue()) {
			System.out.println("It works!");
		}
	}
	
	private static void test1(){
		 String expression = "foobar > 99";

         // Compile the expression.
         Serializable compiled = MVEL.compileExpression(expression);

         Map vars = new HashMap();
         vars.put("foobar", new Integer(100));

         // Now we execute it.
         Boolean result = (Boolean) MVEL.executeExpression(compiled, vars);
         
         if (result.booleanValue()) {
             System.out.println("It works!");
        }      
	}
}