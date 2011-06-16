package study.rbac.handler;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.mvc.Action;
import study.rbac.model.Group;
import study.rbac.model.Role;
import study.rbac.model.User;

public class RoleAjaxHandler extends Action {
	
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
		String cls=request.getParameter("cls");
		int		id=Integer.valueOf(request.getParameter("id"));
		String ids=request.getParameter("ids");
		dao =factory.createDAO();
		if(cls.equals("user")){
			User ju=new User();
			ju.setUserid(id);
			ju.setUserroles(ids);
			dao.update(ju);
		}else{
			Group jg=new Group();
			jg.setGrouproles(ids);
			jg.setGroupid(id);
			dao.update(jg);
		}
		dao.close();
	}
	
	public void allcoate(){
		dao=factory.createDAO();
		Role jr=new Role();
		jr.setRoleid(form.getIntParam("roleid"));
		jr.setRoleprivileges(request.getParameter("ids"));
		dao.update(jr);
		dao.close();
	}
}
