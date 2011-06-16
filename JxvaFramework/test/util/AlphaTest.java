package util;

import junit.framework.TestCase;

import com.jxva.util.AlphaUtil;


public class AlphaTest extends TestCase{
	public void test(){
		assertEquals(AlphaUtil.toAlpha('a'),'A');
		assertEquals(AlphaUtil.toAlpha('6'),'6');
		assertEquals(AlphaUtil.toAlpha('z'),'Z');
	    assertEquals(AlphaUtil.toAlpha("蒋博士"),"JBS");
	    assertEquals(AlphaUtil.toAlpha("中国and美国"),"ZGANDMG");
	    assertEquals(AlphaUtil.toAlpha("你好,Hello"),"NH,HELLO");
	}
}
