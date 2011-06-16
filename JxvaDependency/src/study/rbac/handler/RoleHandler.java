package study.rbac.handler;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.mvc.Action;


public class RoleHandler extends Action {
	
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
		request.setAttribute("list",dao.find("from Role order by levelinfo"));
		dao.close();
		return "list";
	}
	
	public String tree(){
		//XmlTree jtree=new XmlTree(Path.getJxvaHome()+"data/role.xml");
		request.setAttribute("cls",request.getParameter("cls"));
		request.setAttribute("id",request.getParameter("id"));
		//request.setAttribute("jtreedata",jtree.getCheckTree("","",true,false));
		return "tree";
	}
}
