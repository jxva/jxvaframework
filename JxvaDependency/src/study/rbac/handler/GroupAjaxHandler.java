package study.rbac.handler;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.mvc.Action;
import study.rbac.model.User;

public class GroupAjaxHandler extends Action {
	
	private static DAOFactory factory=null;
	private DAO dao;
	static{
		factory=DAOFactory.getInstance();
	}
	
	public String execute() {
		System.out.println("default");
		return SUCCESS;
	}

	public void save(){
		int		id=Integer.valueOf(request.getParameter("id"));
		String ids=request.getParameter("ids");
		dao =factory.createDAO();
		User ju=new User();
		ju.setUserid(id);
		ju.setUsergroups(ids);
		dao.update(ju);
		dao.close();
	}
}
