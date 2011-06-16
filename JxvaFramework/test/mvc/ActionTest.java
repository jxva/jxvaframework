package mvc;

import javax.servlet.ServletException;

import mvc.provide.ActionProvider;

public class ActionTest extends BaseServletWarpper {

	public void testAction() throws ServletException{
		request.setParameter("username","china");
		request.setParameter("password","admin");
		
		
				
		ActionProvider baseAction=new ActionProvider();
		baseAction.init(request, response);
		
		request.setParameter("test","Ok");
		
		assertEquals(baseAction.execute(),"success");
		assertEquals(baseAction.test(),"Ok");
		assertEquals(session.getAttribute("hasLogin"),true);
		
		assertEquals(request.getParameter("username"), "china");
	}
}
