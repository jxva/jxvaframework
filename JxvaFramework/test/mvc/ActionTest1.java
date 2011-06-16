package mvc;

import javax.servlet.ServletException;

import mvc.provide.ActionProvider;

import com.jxva.mvc.Form;

public class ActionTest1 extends BaseServletWarpper{

	public void tearDown() {
		
	}

	public void testAction() throws ServletException {
		// 录制request和response的动作
		request.setParameter("username","china");		
		request.setParameter("password","admin");
		request.setParameter("test","Ok");

		ActionProvider baseAction = new ActionProvider();
		baseAction.init(request, response);

		assertEquals(baseAction.execute(), "success");
		
		assertEquals(baseAction.test(), "Ok");

		assertEquals(session.getAttribute("hasLogin"),true);

		assertEquals(request.getParameter("username"), "china");

		String[] s = new Form(request).getParams("username", "password");
		assertEquals(s[0], "china");
		assertEquals(s[1], "admin");
	}
}
