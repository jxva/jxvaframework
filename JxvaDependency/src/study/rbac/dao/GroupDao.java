package study.rbac.dao;

import java.util.List;

import com.jxva.dao.BaseDao;
import study.rbac.model.Group;

public class GroupDao extends BaseDao {
	
	public List<Group> getGroups(String groupids) {
		return (List<Group>)dao.find("from Group where groupid in (?)",groupids);
	}
}
