package study.rbac.dao;

import java.util.List;

import com.jxva.dao.BaseDao;
import study.rbac.model.User;

public class UserDao extends BaseDao {
	
	public List<User> findAll(){
		return dao.find(User.class);
	}
	
	public void save(User user){
		//user.setUserid(Long.valueOf(dao.getAutoId(User.class,"userid")).intValue());
		dao.save(user);
	}
	
	public User getUser(String username,String password){
		return dao.get(User.class,"username='"+username+"' and password='"+password+"'");
	}
}
