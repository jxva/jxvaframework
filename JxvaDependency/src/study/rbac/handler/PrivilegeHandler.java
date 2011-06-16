package study.rbac.handler;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.mvc.Action;
import study.rbac.model.Privilege;

public class PrivilegeHandler extends Action {
	
	private static DAOFactory factory=null;
	private DAO dao;
	static{
		factory=DAOFactory.getInstance();
	}
	
	public String execute() {
		System.out.println("default");
		return SUCCESS;
	}

	public String list(){
		dao=factory.createDAO();
		request.setAttribute("list",dao.find(Privilege.class));
		dao.close();
		return "list";
	}
	
	public String check(){
		dao=factory.createDAO();
		request.setAttribute("roleid",request.getParameter("roleid"));
		request.setAttribute("list",dao.find(Privilege.class));
		dao.close();
		return "check";
	}
	
	public String append(){
		dao = factory.createDAO();
		String restype=request.getParameter("restype");
		Integer resid=Integer.valueOf(request.getParameter("resid"));
		
		Object obj=dao.get(Privilege.class,"restype='"+restype+"' and resid="+resid);
		if(obj==null){
			Privilege jp=new Privilege();
			//jp.setPrivilegeid(Long.valueOf(dao.getAutoId(Privilege.class,"privilegeid")).intValue());
			jp.setRestype(restype);
			jp.setResid(resid);
			dao.save(jp);
		}
		dao.close();
		return list();
	}

}
