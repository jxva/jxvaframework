package org.jxva;

public class FetchMethodName {
	public static void main(String[] args){
		new FetchMethodName().test2();
	}
	
	public void test1(){
		
		// 获得当前的方法名
		System.out.println(new Exception().getStackTrace()[0].getMethodName());
		
		
		//获得调用者的方法名   
		System.out.println(new Exception().getStackTrace()[1].getMethodName());
	}
	
	public void test2(){
		test1();
	}
}
