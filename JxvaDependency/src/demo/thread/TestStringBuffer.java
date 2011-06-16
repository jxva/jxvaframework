package demo.thread;

public class TestStringBuffer extends Thread{
	public static void main(String args[]){
		for(int i=0;i<200;i++){
			new TestStringBuffer().start();
			new TestStringBuffer().start();
			new TestStringBuffer().start();
			new TestStringBuffer().start();
			new TestStringBuffer().start();
			new TestStringBuffer().start();
		}
	}
	public void run(){
		for(int i=0;i<10;i++){
			StringBuffer sb=new StringBuffer();
			for(int j=0;j<10;j++){
				sb.append(""+j+"");
			}
			System.out.println(sb.toString());
		}	
		
	}
}
