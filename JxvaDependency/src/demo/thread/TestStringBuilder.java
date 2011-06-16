package demo.thread;

public class TestStringBuilder extends Thread{
	public static void main(String args[]){
		for(int i=0;i<200;i++){
			new TestStringBuilder().start();
			new TestStringBuilder().start();
			new TestStringBuilder().start();
			new TestStringBuilder().start();
			new TestStringBuilder().start();
			new TestStringBuilder().start();
		}
	}
	public void run(){
		for(int i=0;i<10;i++){
			StringBuilder sb=new StringBuilder();
			for(int j=0;j<10;j++){
				sb.append(""+j+"");
			}
			System.out.println(sb.toString());
		}	
		
	}
}
