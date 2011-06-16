package org.jxva;

public class Study {

	public static void main(String[] args) {
		//System.out.println(test(50000,60));
		for(int i=0;i<5;i++){
			for(int k=4;k>i;k--){
				System.out.print(' ');
			}
			for(int j=0;j<=i*2;j++){
				System.out.print(j==i?'*':'$');
			}
			System.out.println();
		}
	}
	
	public static double test(int amount,int day){
		return amount*day*1.35/36000;
	}
}
