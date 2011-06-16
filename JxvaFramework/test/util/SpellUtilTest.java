package util;

import junit.framework.TestCase;

import com.jxva.util.SpellUtil;

public class SpellUtilTest  extends TestCase{
	
	public void test(){
		assertEquals(SpellUtil.toSpell("中国"),"zhong guo ");
		assertEquals(SpellUtil.toSpell("China 中国"),"China zhong guo ");
	}

}
