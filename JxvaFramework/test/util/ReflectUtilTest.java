package util;

import junit.framework.TestCase;

import com.jxva.util.ReflectUtil;

public class ReflectUtilTest extends TestCase{
	
	public void test(){
			
		Bean bean=new Bean();
		bean.setId(2);
		bean.setUsername("jxva");
		bean.setEmail("jxva@msn.com");
		
	
		assertEquals(ReflectUtil.getPublicProperty(bean,"id"),2);
		assertEquals(ReflectUtil.invokeStaticMethod(Bean.class,"sayHello"),"Hello");
		assertEquals(ReflectUtil.invokeStaticMethod(Bean.class,"sayHello","China"),"China");
		
		assertEquals(ReflectUtil.invokeMethod(bean,"say"),"Java");
		assertEquals(ReflectUtil.invokeMethod(bean,"say","Java"),"Java");
		
		Bean b=(Bean)ReflectUtil.newInstance(Bean.class);
		b.setUsername("World");
		assertEquals(b.getUsername(),"World");
		
		Bean b2=(Bean)ReflectUtil.newInstance(Bean.class,6,"hello","world");
		assertEquals((int)b2.getId(),6);
		assertEquals(b2.getUsername(),"hello");
		assertEquals(b2.getEmail(),"world");
		
		assertEquals(ReflectUtil.isInstance(b2,Bean.class),true);
		assertEquals(ReflectUtil.isInstance(b2,ReflectUtilTest.class),false);
	}
	
}