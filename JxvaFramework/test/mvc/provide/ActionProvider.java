package mvc.provide;

import com.jxva.mvc.Action;

public class ActionProvider extends Action{

	public String execute() {
		
		return SUCCESS;
	}
	
	public String test(){
		request.setAttribute("dd","ee");
		session.setAttribute("hasLogin",true);
		return request.getParameter("test");
	}
	
}
