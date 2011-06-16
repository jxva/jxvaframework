package study.pattern.command;

import java.util.HashMap;
import java.util.Map;

public class Invoker  {    
	private Map<Object,Object> commands;
	public Invoker() {
		commands = new HashMap<Object,Object>();
	}        
	
	public void addCommand(String commName,ICommand command) {
		commands.put(commName, command);   
	}        
	
	public void request(String commName) {
		ICommand command = (ICommand) commands.get(commName);
		command.execute();   
	}
} 
