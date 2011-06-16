package study.pattern.nullobjects;

public class Client {
	
	//外部调用
	//private Data data=new NullData();
	//使用内置类
	private Data data=nulldata;
	
	public Data getData(){
		return data;
	}
	
	public void setData(){
		this.data=getData();
	}
	
	private static final Data nulldata=new Data(){
		public String getUsername() {
			return null;
		}
		public Integer getUserid() {
			return null;
		}
	};
}
