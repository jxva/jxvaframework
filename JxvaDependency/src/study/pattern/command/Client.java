package study.pattern.command;

public class Client {
	public static void main(String[] args) {
		
		Invoker invoker = new Invoker();
		invoker.addCommand("JUSTIN",new UpperCaseHello("Justindd"));
		invoker.addCommand("momor",new LowerCaseHello("momor"));
		invoker.request("JUSTIN");
		invoker.request("momor");
	}
}