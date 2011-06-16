package study.rbac.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import study.rbac.UserData;
import study.rbac.dao.GroupDao;
import study.rbac.dao.RoleDao;
import study.rbac.dao.UserDao;
import study.rbac.model.Group;
import study.rbac.model.Role;
import study.rbac.model.User;

import com.jxva.mvc.Action;
import com.jxva.util.StringUtil;

public class UserHandler extends Action {
	
	
	public String execute() {
		System.out.println("default");
		return SUCCESS;
	}

	public String list(){
		request.setAttribute("list",new UserDao().findAll());
		return "list";
	}
	
	public String append(){
		User user=form.form2Bean(User.class);
		new UserDao().save(user);
		return list();
	}
	
	public void login(){
		User ju=form.form2Bean(User.class);
		Object obj=new UserDao().getUser(ju.getUsername(),ju.getPassword());
		if(obj!=null){
			ju=(User)obj;
			UserData userdata=new UserData();
			userdata.setUserid(ju.getUserid());
			userdata.setUsername(ju.getUsername());
			userdata.setUsergroups(ju.getUsergroups());
						
			//获取用户所属的用户组
			Group jg=new Group();
			List<Group> list=new GroupDao().getGroups(StringUtil.isEmpty(ju.getUsergroups())?"0":ju.getUsergroups());
			String[] roleids=new String[list.size()+1];
			for(int i=0;i<list.size();i++){
				jg=(Group)list.get(i);
				roleids[i]=jg.getGrouproles();
			}
			roleids[list.size()]=ju.getUserroles();
			userdata.setUserroles( study.rbac.util.StringUtil.getString(roleids));
			
			//获取用户所属的角色
			Role jr=new Role();
			List<Role> list1=new RoleDao().getRoles(userdata.getUserroles());
			String[] privilegeids=new String[list1.size()];
			for(int i=0;i<list1.size();i++){
				jr=(Role)list1.get(i);
				privilegeids[i]=jr.getRoleprivileges();
			}
			userdata.setUserprivileges( study.rbac.util.StringUtil.getString(privilegeids));
			HttpSession session=request.getSession(true);
			userdata.setSessionid(session.getId());
			session.setAttribute("userinfo",userdata);
		}
	}
}
