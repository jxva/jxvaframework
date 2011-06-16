package study.rbac.handler;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.mvc.Action;
import study.rbac.model.Url;

public class UrlHandler extends Action {
	
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
		request.setAttribute("list",dao.find(Url.class));
		dao.close();
		return "list";
	}
	
	public String append(){
		Url ju=form.form2Bean(Url.class);
		dao=factory.createDAO();
		//ju.setUrlid(Long.valueOf(dao.getAutoId(Url.class,"urlid")).intValue());
		dao.save(ju);
		dao.close();
		return list();
	}
	

}
