package util;

public class Bean {
	public Integer id;
	private String username;
	private String email;
	
	public Bean(){
		
	}
	
	public Bean(Integer id,String username,String email){
		this.id=id;
		this.username=username;
		this.email=email;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String say(){
		return "Java";
	}
	
	public String say(String str){
		return str;
	}
	
	public static String sayHello(){
		return "Hello";
	}
	
	public static String sayHello(String str){
		return str;
	}
}
