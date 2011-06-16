package study.rbac.handler;

import java.util.List;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import study.rbac.model.Group;
import study.rbac.model.Role;
import study.rbac.model.User;
import study.rbac.util.StringUtil;

public class Sql {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		User ju=new User();
		ju.setUserid(1);
		ju.setUsergroups("7");
		ju.setUserroles("11,8");
		ju.setUsername("lucy");
		ju.setPassword("lucy");
		
		DAOFactory factory=DAOFactory.getInstance();
		DAO dao=factory.createDAO();
		Object obj=dao.get(User.class,"username='"+ju.getUsername()+"' and password='"+ju.getPassword()+"'");
		if(obj!=null){
			ju=(User)obj;
			
			//获取用户所属的用户组
			List<Group> list=(List<Group>)dao.find("from Group where groupid in ("+ju.getUsergroups()+")");
			String[] roleids=new String[list.size()+1];
			for(int i=0;i<list.size();i++){
				Group jg=(Group)list.get(i);
				roleids[i]=jg.getGrouproles();
			}
			roleids[list.size()]=ju.getUserroles();
			
			//获取用户所属的角色
			List<Role> list1=(List<Role>)dao.find("from Role where roleid in ("+StringUtil.getString(roleids)+")");
			String[] privilegeids=new String[list1.size()];
			for(int i=0;i<list.size();i++){
				Role jr=list1.get(i);
				privilegeids[i]=jr.getRoleprivileges();
			}
			//session.setAttribute("userinfo",ju);
			//session.setAttribute("userprivileges",StringUtil.getString(privilegeids));
			System.out.println(StringUtil.getString(privilegeids));
		}
		dao.close();
	}

}
