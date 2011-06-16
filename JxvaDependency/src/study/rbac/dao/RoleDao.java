package study.rbac.dao;

import java.util.List;

import com.jxva.dao.BaseDao;
import study.rbac.model.Role;

public class RoleDao extends BaseDao {
	
	public List<Role> getRoles(String roleids){
		return (List<Role>)dao.find("from Role where roleid in (?)",roleids);
	}
}
